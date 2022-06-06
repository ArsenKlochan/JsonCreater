package database.objects;

import java.util.ArrayList;

public class Faculty {
    String facultuName;
    ArrayList<Group> groupList;
    int facultyId;

    public Faculty(String facultuName) {
        this.facultuName = facultuName;
        groupList = new ArrayList<>();
    }
    public String getFacultuName() {
        return facultuName;
    }
    public ArrayList<Group> getGroupList() {
        return groupList;
    }
    public int getFacultyId() {
        return facultyId;
    }
}
