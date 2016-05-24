package mfiles.data;

public class Authentication {
    private String username;
    private String password;
    private String domain;
    private boolean windowsUser;
    private String computerName;
    private String vaultGuid;
    private String expiration;
    private boolean readOnly;
    private String URL;
    private String method;

    public Authentication(String username, String password) {
        this.username = username;
        this.password = password;

        this.windowsUser = true;
        this.domain = "IPA";
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public boolean isWindowsUser() {
        return windowsUser;
    }

    public void setWindowsUser(boolean windowsUser) {
        this.windowsUser = windowsUser;
    }

    public String getComputerName() {
        return computerName;
    }

    public void setComputerName(String computerName) {
        this.computerName = computerName;
    }

    public String getVaultGuid() {
        return vaultGuid;
    }

    public void setVaultGuid(String vaultGuid) {
        this.vaultGuid = vaultGuid;
    }

    public String getExpiration() {
        return expiration;
    }

    public void setExpiration(String expiration) {
        this.expiration = expiration;
    }

    public boolean isReadOnly() {
        return readOnly;
    }

    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
}
