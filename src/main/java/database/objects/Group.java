package database.objects;

import java.util.ArrayList;

public class Group {
    private String groupName;
    private ArrayList<String> studentPassList;

    public Group(String groupName) {
        this.groupName = groupName;
        studentPassList = new ArrayList<>(15);
    }
    public String getGroupName() {
        return groupName;
    }
    public ArrayList<String> getStudentList() {
        return studentPassList;
    }
}
