package mfiles.data;

public class Vault {
    //! Vault name.
    private String name;

    //! Vault GUID.
    private String GUID;

    //! Vault-specific authentication token.
    private String authentication;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGUID() {
        return GUID;
    }

    public void setGUID(String GUID) {
        this.GUID = GUID;
    }

    public String getAuthentication() {
        return authentication;
    }

    public void setAuthentication(String authentication) {
        this.authentication = authentication;
    }
}
