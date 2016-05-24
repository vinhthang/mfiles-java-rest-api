package mfiles.data;

public class ObjectFile {

    //! Based on M-Files API.
    private String changeTimeUtc;

    //! Based on M-Files API.
    private String extension;

    //! Based on M-Files API.
    private int ID;

    //! Based on M-Files API.
    private String name;

    //! Based on M-Files API.
    private int version;

    public String getChangeTimeUtc() {
        return changeTimeUtc;
    }

    public void setChangeTimeUtc(String changeTimeUtc) {
        this.changeTimeUtc = changeTimeUtc;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
