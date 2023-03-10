package CampusTest.Classes;

public class US_11_Discounts {

    private String id;
    private String description;
    private String code;
    private String priority;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    @Override
    public String toString() {
        return "US_11_Discounts{" +
                "id='" + id + '\'' +
                ", description='" + description + '\'' +
                ", code='" + code + '\'' +
                ", priority='" + priority + '\'' +
                '}';
    }
}
