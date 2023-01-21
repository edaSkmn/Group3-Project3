package CampusTest.Classes;

import java.util.Arrays;

public class US_8_Department {
    private String id;
    private String name;
    private String code;

    private String[] sections={};

    private String[] constants={};

    private boolean active=true;

    private String school;

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String[] getSections() {
        return sections;
    }

    public void setSections(String[] sections) {
        this.sections = sections;
    }

    public String[] getConstants() {
        return constants;
    }

    public void setConstants(String[] constants) {
        this.constants = constants;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getSchool() {
        return school;
    }

    @Override
    public String toString() {
        return "US_8_Department{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", sections=" + Arrays.toString(sections) +
                ", constants=" + Arrays.toString(constants) +
                ", active=" + active +
                ", school='" + school + '\'' +
                '}';
    }

    public void setSchool(String school) {
        this.school = school;

    }

}