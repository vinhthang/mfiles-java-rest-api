package mfiles.data;

public class ClassGroup {
    private String name;
    private ObjectClass[] classes;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ObjectClass[] getClasses() {
        return classes;
    }

    public void setClasses(ObjectClass[] classes) {
        this.classes = classes;
    }
}
