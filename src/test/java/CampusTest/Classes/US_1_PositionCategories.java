package CampusTest.Classes;

public class US_1_PositionCategories {
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
        return "US_1_PositionCategories{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
