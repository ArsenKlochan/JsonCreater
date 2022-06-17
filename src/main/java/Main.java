import database.JSONCreater;
import database.StudentChooseSystem;
import database.objects.Faculty;
import database.objects.Group;
import database.objects.StudentData;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;

public class Main{
    private static File directory;
    private static File file;
//        extends Application

//    @Override
//    public void start(Stage primaryStage) throws Exception{
//    }


    public static void main(String[] args) {
            StudentChooseSystem studentChooseSystem = new StudentChooseSystem();
            studentChooseSystem.chooseStudent();
//            chooseAll(studentChooseSystem);
//        chooseForGroup(studentChooseSystem);
        chooseForFaculty(studentChooseSystem);

//        Application.launch(args);

    }
//    метод формування json-файлів для всіх груп, що випускаються
    private static void chooseAll(StudentChooseSystem studentChooseSystem){
        try {
            System.out.println("Режим роботи програми, який забезпечує створення json-файлів для всіх випускових груп університету");
            System.out.println("Розпочалався процес формування json-файлів. Назва факультету та назва групи для якої формується json-файл буде відображатись в консолі.");
            for (Faculty faculty : studentChooseSystem.getFaculties()) {
                directory = new File(faculty.getFacultuName());
                directory.mkdirs();
                System.out.println(faculty.getFacultuName());
                for (Group group : faculty.getGroupList()) {
                LinkedList<StudentData> studentDataLinkedList = new LinkedList<>();
                file = new File(directory, group.getGroupName() + ".json");
                file.createNewFile();
                System.out.println(group.getGroupName());
                    for (String studentPass : group.getStudentList()) {
                        studentDataLinkedList.add(new StudentData(studentPass));
                    }
                JSONCreater.writeJsonObject(file.getAbsolutePath(), studentDataLinkedList);
                }
            }
        System.out.println("Done");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
//    метод формування джейсон файлу для конкретного факультету
    private static void chooseForFaculty(StudentChooseSystem studentChooseSystem){
        try {
            System.out.println("Режим роботи програми, який забезпечує створення json-файлів для конкретного факультету");
            int counter = 0;
            for (Faculty faculty: studentChooseSystem.getFaculties()){
                counter++;
                System.out.println(counter + " " + faculty.getFacultuName());
            }
            System.out.println("Оберіть факультет. Введіть порядковий номер факультету з приведеного списку");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            int facultyNumber = Integer.parseInt(bufferedReader.readLine());
            Faculty faculty = studentChooseSystem.getFaculties().get(facultyNumber-1);
            directory = new File(facultyNumber + faculty.getFacultuName());
            directory.mkdirs();
            System.out.println(faculty.getFacultuName());
            System.out.println("Розпочалався процес формування json-файлів. Назва групи для якої формується json-файл буде відображатись в консолі.");
            for (Group group : faculty.getGroupList()) {
                LinkedList<StudentData> studentDataLinkedList = new LinkedList<>();
                file = new File(directory, group.getGroupName() + ".json");
                file.createNewFile();
                System.out.println(group.getGroupName());
                for (String studentPass : group.getStudentList()) {
                    studentDataLinkedList.add(new StudentData(studentPass));
                }
                JSONCreater.writeJsonObject(file.getAbsolutePath(), studentDataLinkedList);
            }
            System.out.println("Done");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static void chooseForGroup(StudentChooseSystem studentChooseSystem){
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        try{
            System.out.println("Режим роботи програми, який забезпечує створення json-файлів для конкретної групи");
            int counter = 0;
            for (Faculty faculty: studentChooseSystem.getFaculties()){
                counter++;
                System.out.println(counter + " " + faculty.getFacultuName());
            }
            System.out.println("Оберіть факультет. Введіть порядковий номер факультету з приведеного списку");
            int facultyNumber = Integer.parseInt(bufferedReader.readLine());
            Faculty faculty = studentChooseSystem.getFaculties().get(facultyNumber-1);
            counter=0;
            for (Group group: faculty.getGroupList()){
                counter++;
                System.out.println(counter + " " + group.getGroupName());
            }
            System.out.println("Оберіть групу. Введіть порядковий номер групи з приведеного списку");
            int groupNumber = Integer.parseInt(bufferedReader.readLine());
            Group group = faculty.getGroupList().get(groupNumber-1);

            System.out.println(group.getGroupName());
            System.out.println("Розпочалався процес формування json-файлів. Зачекайте кілька митей");
            LinkedList<StudentData> studentDataLinkedList = new LinkedList<>();
            for (String studentPass : group.getStudentList()) {
                studentDataLinkedList.add(new StudentData(studentPass));
            }
            file = new File(groupNumber + group.getGroupName() + ".json");
            file.createNewFile();
            JSONCreater.writeJsonObject(file.getAbsolutePath(), studentDataLinkedList);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}