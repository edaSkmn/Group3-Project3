package CampusTest.Classes;

public class US_12_Nationality {

    private String id;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "US_12_Nationality{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
