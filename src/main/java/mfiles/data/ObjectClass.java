package mfiles.data;

public class ObjectClass {
    private int ID;
    private String name;
    private String namePropertyDef;
    private int objType;
    private int workflow;

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

    public String getNamePropertyDef() {
        return namePropertyDef;
    }

    public void setNamePropertyDef(String namePropertyDef) {
        this.namePropertyDef = namePropertyDef;
    }

    public int getObjType() {
        return objType;
    }

    public void setObjType(int objType) {
        this.objType = objType;
    }

    public int getWorkflow() {
        return workflow;
    }

    public void setWorkflow(int workflow) {
        this.workflow = workflow;
    }
}
