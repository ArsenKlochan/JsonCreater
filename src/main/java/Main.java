import database.JSONCreater;
import database.StudentChooseSystem;
import database.objects.Faculty;
import database.objects.Group;
import database.objects.StudentData;
import javafx.application.Application;
import javafx.stage.Stage;
import java.util.LinkedList;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        StudentChooseSystem studentChooseSystem = new StudentChooseSystem();
        studentChooseSystem.chooseStudent();
        for(Faculty faculty: studentChooseSystem.getFaculties()){
            System.out.println(faculty.getFacultuName());
            for (Group group: faculty.getGroupList()){
                LinkedList<StudentData> studentDataLinkedList = new LinkedList<>();
                for (String studentPass: group.getStudentList()){
                    studentDataLinkedList.add(new StudentData(studentPass));
                }
                JSONCreater.writeJsonObject(faculty.getFacultuName() + "-" + group.getGroupName() + ".json",studentDataLinkedList);
            }
        }
//        LinkedList<StudentData> studentDataLinkedList = new LinkedList<>();
//            studentDataLinkedList.add(new StudentData("55369"));
//            studentDataLinkedList.add(new StudentData("55377"));
//        JSONCreater.writeJsonObject("test3.json",studentDataLinkedList);

        System.out.println("Done");
    }


    public static void main(String[] args) {
        Application.launch(args);
    }
}
