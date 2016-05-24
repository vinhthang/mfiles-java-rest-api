package mfiles.data;

public class PropertyValue {
    private int propertyDef;
    private TypedValue typedValue;

    public PropertyValue() {

    }

    public PropertyValue(int propertyDef, TypedValue typedValue) {
        this.propertyDef = propertyDef;
        this.typedValue = typedValue;
    }

    public int getPropertyDef() {
        return propertyDef;
    }

    public void setPropertyDef(int propertyDef) {
        this.propertyDef = propertyDef;
    }

    public TypedValue getTypedValue() {
        return typedValue;
    }

    public void setTypedValue(TypedValue typedValue) {
        this.typedValue = typedValue;
    }

    public static PropertyValue create(int propertyDef, int typeDef, String displayValue, Object... value) {
        PropertyValue propertyValue = new PropertyValue();
        propertyValue.setPropertyDef(propertyDef);
        propertyValue.typedValue = new TypedValue();
        propertyValue.typedValue.setDataType(typeDef);
        propertyValue.typedValue.setDisplayValue(displayValue);
        switch (typeDef) {
            case MFDataType.Lookup :
                Lookup lookup = new Lookup();
                lookup.setDisplayValue(displayValue);
                lookup.setItem((Integer) value[0]);
                propertyValue.typedValue.setLookup(lookup);
                break;
            case MFDataType.MultiSelectLookup:
                Lookup[] lookups = new Lookup[value.length];
                for (int i = 0; i < value.length; i++) {
                    lookups[i] = new Lookup();
                    lookups[i].setItem((Integer) value[i]);
                }
                propertyValue.typedValue.setLookups(lookups);
                break;
            default:
                propertyValue.typedValue.setValue(value[0]);
                break;
        }

        return  propertyValue;
    }
}
