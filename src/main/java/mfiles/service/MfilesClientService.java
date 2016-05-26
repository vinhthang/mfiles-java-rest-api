package mfiles.service;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import mfiles.data.*;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Logger;

public class MfilesClientService {
    private static final Logger logger = Logger.getLogger(MfilesClientService.class.getName());
    private String mFilesServerRESTURI = "http://10.28.1.199/m/REST/";
    private CloseableHttpClient httpClient;
    private final JsonParser parser;
    private final Gson gson;

    public MfilesClientService() {
        httpClient = HttpClientBuilder.create().build();
        parser = new JsonParser();
        gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();
    }

    public MfilesClientService(String serverURI) {
        this();
        this.mFilesServerRESTURI = serverURI;
    }

    public List<Vault> authentication(String username, String password, boolean windowsUser, String domain) {
        String result = null;
        HttpPost request = new HttpPost(mFilesServerRESTURI + "session.aspx?_method=PUT");
        request.setHeader("Accept", "application/json");
        Authentication auth = new Authentication(username, password);
        auth.setWindowsUser(windowsUser);
        auth.setDomain(domain);
        final HttpEntity entity = new StringEntity(gson.toJson(auth), "UTF-8");
        request.setEntity(entity);
        final String execute = execute(request);
        final List<Vault> vaultList = gson.fromJson(execute, new TypeToken<List<Vault>>() {}.getType());
        final Vault defaultVault = vaultList.get(0);
        //got 401 exception Current session is not allowed to modify its state.
        //getSessionVault(defaultVault.getAuthentication(), defaultVault.getGUID());
        return vaultList;
    }

    public List<Vault> getSessionVault(String xAuthentication, String guid) {
        HttpPost request = new HttpPost(mFilesServerRESTURI + "session/vault.aspx?_method=PUT");
        request.setHeader("Accept", "application/json");
        request.setHeader("X-Authentication", xAuthentication);
        request.setHeader("Content-Type", "application/x-www-form-urlencoded");
        request.setEntity(new StringEntity("{\"GUID\": \"" + guid + "\"}", "UTF-8"));
        final String execute = execute(request);
        return gson.fromJson(execute, new TypeToken<List<Vault>>(){}.getType());
    }

    public ObjectVersion getObject(String xAuthentication, int type, int id) {
        final String uri = String.format(mFilesServerRESTURI + "objects/%d/%d/latest.aspx", type, id);
        HttpGet request = new HttpGet(uri);
        request.setHeader("X-Authentication", xAuthentication);
        final String execute = execute(request);
        return gson.fromJson(execute, ObjectVersion.class);
    }

    public ObjectVersion createObject(String xAuthentication, int type, PropertyValue[] propertyValues, Path file, int workflow) {
        UploadInfo uploadInfo = uploadFile(xAuthentication, file);
        final String fileName = file.getFileName().toString();
//        uploadInfo.setTitle("test");
        String extension = "txt";
        if (fileName.lastIndexOf(".") > 0) {
            extension = fileName.substring(fileName.lastIndexOf(".") + 1);
        }
        uploadInfo.setExtension(extension);
        HttpPost request = new HttpPost(mFilesServerRESTURI + "objects/" + type + ".aspx?checkIn=false&_method=POST");
        request.setHeader("X-Authentication", xAuthentication);

        ObjectCreationInfo objectCreationInfo = new ObjectCreationInfo();
        objectCreationInfo.setPropertyValues(propertyValues);
        objectCreationInfo.setFiles(new UploadInfo[] {uploadInfo});
//        objectCreationInfo.setWorkflow(107);
        objectCreationInfo.setWorkflow(workflow);
        objectCreationInfo.setNamedACL(-1);
        request.setEntity(new StringEntity(gson.toJson(objectCreationInfo), "UTF-8"));

        String result = execute(request);
        return gson.fromJson(result, ObjectVersion.class);
    }

    public UploadInfo uploadFile(String xAuthentication, Path file) {
        HttpPost request = new HttpPost(mFilesServerRESTURI + "files");
        request.setHeader("Content-Type", "application/octet-stream");
        request.setHeader("X-Authentication", xAuthentication);
        FileEntity entity = new FileEntity(file.toFile());
        request.setEntity(entity);

        final String execute = execute(request);
        return gson.fromJson(execute, UploadInfo.class);
    }

    public List<ClassGroup> getClassGroups(String xAuthentication) {
        HttpGet request = new HttpGet(mFilesServerRESTURI + "objects/0/classes.aspx?byGroup=true");
        request.setHeader("Accept", "application/json");
        request.setHeader("X-Authentication", xAuthentication);
        final String execute = execute(request);

        return gson.fromJson(execute, new TypeToken<List<ClassGroup>>(){}.getType());
    }

    public List<PropertyDef> getPropertyDefs(String xAuthentication) {
        HttpGet request = new HttpGet(mFilesServerRESTURI + "structure/properties");
        request.setHeader("Accept", "application/json");
        request.setHeader("X-Authentication", xAuthentication);
        final String execute = execute(request);

        return gson.fromJson(execute, new TypeToken<List<PropertyDef>>(){}.getType());
    }

    private JsonObject getJsonObject(HttpUriRequest request) {
        String result = execute(request);

        return parser.parse(result).getAsJsonObject();
    }

    private String execute(HttpUriRequest request) {
        try (CloseableHttpResponse response = httpClient.execute(request)) {
            final String responseString = EntityUtils.toString(response.getEntity());
            final int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 500 || statusCode == 401) {
                throw new MFilesException(statusCode, responseString);
            }
            return responseString;
        } catch (IOException e) {
            e.printStackTrace();
            throw new MFilesException(500, e.getMessage());
        }
    }

    private JsonArray getJsonArray(HttpUriRequest request) {
        String result = execute(request);

        return parser.parse(result).getAsJsonArray();
    }

    public ExportResult export(Path exportPath, String customerNo, String issueType, String[] selectedClassAndWorkFlow) {
        final ExportResult exportResult = new ExportResult();
        List<Vault> authJson = this.authentication("jirauser", "vnds1234", false, null);
        String xAuthentication = authJson.get(0).getAuthentication();
        //Class value
//        PropertyValue testJiraClass = PropertyValue.create(100, MFDataType.Lookup, null, 451);
        PropertyValue testJiraClass = PropertyValue.create(100, MFDataType.Lookup, null, Integer.valueOf(selectedClassAndWorkFlow[0]));

//        PropertyValue soTaiKhoan = PropertyValue.create(1205, MFDataType.Lookup, customerNo, 32901);
        PropertyValue soTaiKhoan = PropertyValue.create(1205, MFDataType.Lookup, customerNo, 32901);
        PropertyValue noiLuuTru = PropertyValue.create(1256, MFDataType.MultiSelectLookup, "Hội Sở", 1);

        ObjectVersion objectVersion = this.createObject(xAuthentication, MFDataType.Uninitialized,
                new PropertyValue[] {testJiraClass, soTaiKhoan, noiLuuTru}, exportPath, Integer.valueOf(selectedClassAndWorkFlow[1]));
        exportResult.setMessage(gson.toJson(objectVersion));
        return exportResult;
    }
}
