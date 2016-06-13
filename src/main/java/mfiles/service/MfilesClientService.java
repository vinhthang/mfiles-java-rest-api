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
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public class MfilesClientService {
    private static final Logger logger = Logger.getLogger(MfilesClientService.class.getName());
    private String mFilesServerRESTURI = "http://10.28.1.199/m/REST/";
    private CloseableHttpClient httpClient;
    private final JsonParser parser;
    private final Gson gson;
    private String jiraUserAuthentication;

    public MfilesClientService() {
        httpClient = HttpClientBuilder.create().build();
        parser = new JsonParser();
        gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();
    }

    public MfilesClientService(String serverURI) {
        this();
        this.mFilesServerRESTURI = serverURI;
        this.jiraUserAuthentication = getJiraUserAuthentication();
    }

    public String getJiraUserAuthentication() {
        return authentication("jirauser", "vnds1234", false, null).get(0).getAuthentication();
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
        return createObject(xAuthentication, type, propertyValues, Arrays.asList(file), workflow);
    }

    public ObjectVersion createObject(String xAuthentication, int type, PropertyValue[] propertyValues, List<Path> files, int workflow) {
        UploadInfo[] uploadInfos = new UploadInfo[files.size()];
        for (int i = 0; i < files.size(); i++) {
            Path path = files.get(i);
            UploadInfo uploadInfo = uploadFile(xAuthentication, path);
            uploadInfos[i] = uploadInfo;
        }

        HttpPost request = new HttpPost(mFilesServerRESTURI + "objects/" + type + ".aspx?checkIn=false&_method=POST");
        request.setHeader("X-Authentication", xAuthentication);

        ObjectCreationInfo objectCreationInfo = new ObjectCreationInfo();
        objectCreationInfo.setPropertyValues(propertyValues);
        objectCreationInfo.setFiles(uploadInfos);
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

        final UploadInfo uploadInfo = gson.fromJson(execute, UploadInfo.class);
        String fileName = file.getFileName().toString();
        String extension = "txt";
        final int indexOf = fileName.lastIndexOf(".");
        if (indexOf > 0) {
            extension = fileName.substring(indexOf + 1);
            fileName = fileName.substring(0, indexOf);
        }
        uploadInfo.setExtension(extension);
        uploadInfo.setTitle(fileName);
        return uploadInfo;
    }

    public ExtendedObjectClass getExtendedObjectClass(Integer objectClassId) {
        HttpGet request = new HttpGet(mFilesServerRESTURI + String.format("/structure/classes/%d/full.aspx", objectClassId));
        request.setHeader("Accept", "application/json");
        request.setHeader("X-Authentication", getJiraUserAuthentication());
        final String execute = execute(request);

        return gson.fromJson(execute, ExtendedObjectClass.class);
    }

    public List<Workflow> getStructureWorkflows() {
        HttpGet request = new HttpGet(mFilesServerRESTURI + "/structure/workflows");
        request.setHeader("Accept", "application/json");
        request.setHeader("X-Authentication", getJiraUserAuthentication());
        final String execute = execute(request);

        return gson.fromJson(execute, new TypeToken<List<Workflow>>(){}.getType());
    }

    public List<ClassGroup> getClassGroups(String xAuthentication) {
        HttpGet request = new HttpGet(mFilesServerRESTURI + "objects/0/classes.aspx?byGroup=true");
        request.setHeader("Accept", "application/json");
        request.setHeader("X-Authentication", xAuthentication);
        final String execute = execute(request);

        return gson.fromJson(execute, new TypeToken<List<ClassGroup>>(){}.getType());
    }

    public List<ClassGroup> getClassGroups() {
        return getClassGroups(getJiraUserAuthentication());
    }

    public List<ValueListItem> getValueListItemByPropertyDefID(int propertyDefID, String filter) {
        HttpGet request = new HttpGet(mFilesServerRESTURI +
                String.format("valuelistsbypropdefid/%d/items.aspx?limit=100&filter=%s", propertyDefID, filter)
                + "&extensions=mfwa&q=0&p=1&s=0&contentType=application%2Fjson%3B+charset%3Dutf-8");
        request.setHeader("Accept", "application/json");
        request.setHeader("X-Authentication", getJiraUserAuthentication());
        final String execute = execute(request);

        return gson.fromJson(execute, new TypeToken<List<ValueListItem>>(){}.getType());
    }

    public List<ValueListItem> getValueListItemByPropertyDefID(int propertyDefID) {
        return getValueListItemByPropertyDefID(propertyDefID, "");
    }

    public List<PropertyDef> getPropertyDefs() {
        HttpGet request = new HttpGet(mFilesServerRESTURI + "structure/properties");
        request.setHeader("Accept", "application/json");
        request.setHeader("X-Authentication", getJiraUserAuthentication());
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

    public ExportResult export(List<Path> fileList, String customerNo, String objectClass, Integer workFlow, String noiLuuHoSoGoc) {
        List<Vault> authJson = this.authentication("jirauser", "vnds1234", false, null);
        String xAuthentication = authJson.get(0).getAuthentication();
        if (workFlow == null) {
            final ExtendedObjectClass extendedObjectClass = getExtendedObjectClass(Integer.valueOf(objectClass));
            workFlow = extendedObjectClass.getWorkflow();
        }
        //Class value
//        PropertyValue testJiraClass = PropertyValue.create(100, MFDataType.Lookup, null, 451);
        PropertyValue loaiTaiLieu = PropertyValue.create(100, MFDataType.Lookup, null, Integer.valueOf(objectClass));
//        PropertyValue checkIn = PropertyValue.create(22, MFDataType.Boolean, "Đóng", Boolean.TRUE);
        PropertyValue soTaiKhoan = createCustomerNoPropertyValue(customerNo);
//        PropertyValue noiLuuTru = PropertyValue.create(1256, MFDataType.MultiSelectLookup, "Hội Sở", Integer.valueOf(noiLuuHoSoGoc));
        PropertyValue noiLuuTru = createNoiLuuHoSoGocPropertyValue(noiLuuHoSoGoc);

        ObjectVersion objectVersion = this.createObject(xAuthentication, MFDataType.Uninitialized,
                new PropertyValue[]{loaiTaiLieu, soTaiKhoan, noiLuuTru}, fileList, workFlow);
        final ExportResult exportResult = new ExportResult(gson.toJson(objectVersion), objectVersion);

        return exportResult;
    }

    public PropertyValue createNoiLuuHoSoGocPropertyValue(String noiLuuHoSoGoc) {
        List<ValueListItem> customerNoList = getValueListItemByPropertyDefID(1256);
        for (ValueListItem item : customerNoList) {
            if (item.getID() == Integer.valueOf(noiLuuHoSoGoc)) {
                return PropertyValue.create(1256, MFDataType.MultiSelectLookup, item.getName(), item.getID());
            }
        }

        throw new MFilesException(404, "Noi Luu Ho So Goc not found, id " + noiLuuHoSoGoc);
    }

    public PropertyValue createCustomerNoPropertyValue(String customerNo) {
        List<ValueListItem> customerNoList = getValueListItemByPropertyDefID(1205, customerNo);
        if (customerNoList == null || customerNoList.size() == 0 || !customerNo.equals(customerNoList.get(0).getName())) {
            throw new MFilesException(404, "Customer No not found, " + customerNo);
        }

        int customerID = customerNoList.get(0).getID();
        return PropertyValue.create(1205, MFDataType.Lookup, customerNo, customerID);
    }

    public List<PropertyValue> displayProperties(int objectId) {
        HttpGet request = new HttpGet(mFilesServerRESTURI + String.format("objects/0/%d/1/displayproperties.aspx", objectId));
        request.setHeader("Accept", "application/json");
        request.setHeader("X-Authentication", getJiraUserAuthentication());
        final String execute = execute(request);
        return gson.fromJson(execute, new TypeToken<List<PropertyValue>>(){}.getType());
    }

    public void checkout(int objectId) {
        HttpPost request = new HttpPost(mFilesServerRESTURI + String.format("objects/0/%d/checkedout.aspx?_method=PUT", objectId));
        request.setHeader("Accept", "application/json");
        request.setHeader("X-Authentication", getJiraUserAuthentication());
        request.setEntity(new StringEntity("{\"Value\": 0 }", "utf-8"));
        final String execute = execute(request);
    }
}
