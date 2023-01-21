package CampusTest.Classes;

import java.util.Arrays;

public class US_9_BankAccounts {

    private String Iban;
    private String IntCode;
    private String Name;
    private String[] curr;
    private String schoolId;

    public US_9_BankAccounts(String schoolId, String[] curr){
        this.schoolId=schoolId;
        this.curr=curr;
    }


    public String getIban() {
        return Iban;
    }

    public void setIban(String iban) {
        Iban = iban;
    }

    public String getIntCode() {
        return IntCode;
    }

    public void setIntCode(String intCode) {
        IntCode = intCode;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String[] getCurr() {
        return curr;
    }

    public void setCurr(String[] curr) {
        this.curr = curr;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    @Override
    public String toString() {
        return "US_9_BankAccounts{" +
                "Iban='" + Iban + '\'' +
                ", IntCode='" + IntCode + '\'' +
                ", Name='" + Name + '\'' +
                ", curr=" + Arrays.toString(curr) +
                ", schoolId='" + schoolId + '\'' +
                '}';
    }

}
