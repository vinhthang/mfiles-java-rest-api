package mfiles.data;

public class ObjectCreationInfo {
    //! Properties for the new object.
    private PropertyValue[] propertyValues;

    //! References previously uploaded files.
    private UploadInfo[] files;

    private int workflow;
    private int namedACL;

    public PropertyValue[] getPropertyValues() {
        return propertyValues;
    }

    public void setPropertyValues(PropertyValue[] propertyValues) {
        this.propertyValues = propertyValues;
    }

    public UploadInfo[] getFiles() {
        return files;
    }

    public void setFiles(UploadInfo[] files) {
        this.files = files;
    }

    public int getWorkflow() {
        return workflow;
    }

    public void setWorkflow(int workflow) {
        this.workflow = workflow;
    }

    public int getNamedACL() {
        return namedACL;
    }

    public void setNamedACL(int namedACL) {
        this.namedACL = namedACL;
    }
}
