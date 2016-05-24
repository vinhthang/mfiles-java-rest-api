package mfiles.data;

public class ObjectVersion {
    private String accessedByMeUtc;
    private String checkedOutAtUtc;
    private int checkedOutTo;
    private String checkedOutToUserName;
    private int Class;
    private String createdUtc;
    private boolean deleted;
    private String displayID;
    private ObjectFile[] files;
    private boolean hasAssignments;
    private boolean hasRelationshipsFromThis;
    private boolean hasRelationshipsToThis;
    private boolean isStub;
    private String lastModifiedUtc;
    private boolean objectCheckedOut;
    private boolean objectCheckedOutToThisUser;
    private int objectVersionFlags;
    private ObjVer objVer;
    private boolean singleFile;
    private boolean thisVersionLatestToThisUser;
    private String title;
    private boolean visibleAfterOperation;

    public String getAccessedByMeUtc() {
        return accessedByMeUtc;
    }

    public void setAccessedByMeUtc(String accessedByMeUtc) {
        this.accessedByMeUtc = accessedByMeUtc;
    }

    public String getCheckedOutAtUtc() {
        return checkedOutAtUtc;
    }

    public void setCheckedOutAtUtc(String checkedOutAtUtc) {
        this.checkedOutAtUtc = checkedOutAtUtc;
    }

    public int getCheckedOutTo() {
        return checkedOutTo;
    }

    public void setCheckedOutTo(int checkedOutTo) {
        this.checkedOutTo = checkedOutTo;
    }

    public String getCheckedOutToUserName() {
        return checkedOutToUserName;
    }

    public void setCheckedOutToUserName(String checkedOutToUserName) {
        this.checkedOutToUserName = checkedOutToUserName;
    }

    public int getClazz() {
        return Class;
    }

    public void setClass(int aClass) {
        Class = aClass;
    }

    public String getCreatedUtc() {
        return createdUtc;
    }

    public void setCreatedUtc(String createdUtc) {
        this.createdUtc = createdUtc;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public String getDisplayID() {
        return displayID;
    }

    public void setDisplayID(String displayID) {
        this.displayID = displayID;
    }

    public ObjectFile[] getFiles() {
        return files;
    }

    public void setFiles(ObjectFile[] files) {
        this.files = files;
    }

    public boolean isHasAssignments() {
        return hasAssignments;
    }

    public void setHasAssignments(boolean hasAssignments) {
        this.hasAssignments = hasAssignments;
    }

    public boolean isHasRelationshipsFromThis() {
        return hasRelationshipsFromThis;
    }

    public void setHasRelationshipsFromThis(boolean hasRelationshipsFromThis) {
        this.hasRelationshipsFromThis = hasRelationshipsFromThis;
    }

    public boolean isHasRelationshipsToThis() {
        return hasRelationshipsToThis;
    }

    public void setHasRelationshipsToThis(boolean hasRelationshipsToThis) {
        this.hasRelationshipsToThis = hasRelationshipsToThis;
    }

    public boolean isStub() {
        return isStub;
    }

    public void setStub(boolean stub) {
        isStub = stub;
    }

    public String getLastModifiedUtc() {
        return lastModifiedUtc;
    }

    public void setLastModifiedUtc(String lastModifiedUtc) {
        this.lastModifiedUtc = lastModifiedUtc;
    }

    public boolean isObjectCheckedOut() {
        return objectCheckedOut;
    }

    public void setObjectCheckedOut(boolean objectCheckedOut) {
        this.objectCheckedOut = objectCheckedOut;
    }

    public boolean isObjectCheckedOutToThisUser() {
        return objectCheckedOutToThisUser;
    }

    public void setObjectCheckedOutToThisUser(boolean objectCheckedOutToThisUser) {
        this.objectCheckedOutToThisUser = objectCheckedOutToThisUser;
    }

    public int getObjectVersionFlags() {
        return objectVersionFlags;
    }

    public void setObjectVersionFlags(int objectVersionFlags) {
        this.objectVersionFlags = objectVersionFlags;
    }

    public ObjVer getObjVer() {
        return objVer;
    }

    public void setObjVer(ObjVer objVer) {
        this.objVer = objVer;
    }

    public boolean isSingleFile() {
        return singleFile;
    }

    public void setSingleFile(boolean singleFile) {
        this.singleFile = singleFile;
    }

    public boolean isThisVersionLatestToThisUser() {
        return thisVersionLatestToThisUser;
    }

    public void setThisVersionLatestToThisUser(boolean thisVersionLatestToThisUser) {
        this.thisVersionLatestToThisUser = thisVersionLatestToThisUser;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isVisibleAfterOperation() {
        return visibleAfterOperation;
    }

    public void setVisibleAfterOperation(boolean visibleAfterOperation) {
        this.visibleAfterOperation = visibleAfterOperation;
    }
}
