package database.objects;

import java.util.ArrayList;

public class Faculty {
    String facultuName;
    ArrayList<Group> groupList;

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
}
