package mfiles.data;

public class ExtendedObjectClass extends ObjectClass {
    private AssociatedPropertyDef[]	associatedPropertyDefs;
    private ObjectVersion[] templates;

    public AssociatedPropertyDef[] getAssociatedPropertyDefs() {
        return associatedPropertyDefs;
    }

    public void setAssociatedPropertyDefs(AssociatedPropertyDef[] associatedPropertyDefs) {
        this.associatedPropertyDefs = associatedPropertyDefs;
    }

    public ObjectVersion[] getTemplates() {
        return templates;
    }

    public void setTemplates(ObjectVersion[] templates) {
        this.templates = templates;
    }
}
