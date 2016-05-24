package mfiles.data;

public class TypedValue {
    //! Specifies the type of the value.
    private int dataType;

    //! Specifies whether the typed value contains a real value.
    private boolean hasValue;

    //! Specifies the string, number or boolean value when the DataType is not a lookup type.
    private Object value;

    //! Specifies the lookup value when the DataType is Lookup.
    private Lookup lookup;

    //! Specifies the collection of \type{Lookup}s when the DataType is MultiSelectLookup.
    private Lookup[] lookups;

    //! Provides the value formatted for display.
    private String displayValue;

    //! Provides a key that can be used to sort \type{TypedValue}s
    private String sortingKey;

    //! Provides the typed value in a serialized format suitable to be used in URIs.
    private String serializedValue;

    public int getDataType() {
        return dataType;
    }

    public void setDataType(int dataType) {
        this.dataType = dataType;
    }

    public boolean isHasValue() {
        return hasValue;
    }

    public void setHasValue(boolean hasValue) {
        this.hasValue = hasValue;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Lookup getLookup() {
        return lookup;
    }

    public void setLookup(Lookup lookup) {
        this.lookup = lookup;
    }

    public Lookup[] getLookups() {
        return lookups;
    }

    public void setLookups(Lookup[] lookups) {
        this.lookups = lookups;
    }

    public String getDisplayValue() {
        return displayValue;
    }

    public void setDisplayValue(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getSortingKey() {
        return sortingKey;
    }

    public void setSortingKey(String sortingKey) {
        this.sortingKey = sortingKey;
    }

    public String getSerializedValue() {
        return serializedValue;
    }

    public void setSerializedValue(String serializedValue) {
        this.serializedValue = serializedValue;
    }
}
