package mfiles.data;

public class PropertyDef {
    private boolean allObjectTypes;
    private String automaticValue;
    private int automaticValueType;
    private boolean basedOnValueList;
    private int dataType;
    private int ID;
    private String name;
    private int objectType;
    private int valueList;

    public boolean isAllObjectTypes() {
        return allObjectTypes;
    }

    public void setAllObjectTypes(boolean allObjectTypes) {
        this.allObjectTypes = allObjectTypes;
    }

    public String getAutomaticValue() {
        return automaticValue;
    }

    public void setAutomaticValue(String automaticValue) {
        this.automaticValue = automaticValue;
    }

    public int getAutomaticValueType() {
        return automaticValueType;
    }

    public void setAutomaticValueType(int automaticValueType) {
        this.automaticValueType = automaticValueType;
    }

    public boolean isBasedOnValueList() {
        return basedOnValueList;
    }

    public void setBasedOnValueList(boolean basedOnValueList) {
        this.basedOnValueList = basedOnValueList;
    }

    public int getDataType() {
        return dataType;
    }

    public void setDataType(int dataType) {
        this.dataType = dataType;
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

    public int getObjectType() {
        return objectType;
    }

    public void setObjectType(int objectType) {
        this.objectType = objectType;
    }

    public int getValueList() {
        return valueList;
    }

    public void setValueList(int valueList) {
        this.valueList = valueList;
    }
}
