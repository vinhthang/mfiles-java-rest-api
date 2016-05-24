package mfiles.data;

public class UploadInfo {
    //! Temporary upload ID given by the server.
    private int uploadID;

    //! File name without extension.
    private String title;

    //! File extension.
    private String extension;

    //! File size.
    private long size;

    public int getUploadID() {
        return uploadID;
    }

    public void setUploadID(int uploadID) {
        this.uploadID = uploadID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }
}
