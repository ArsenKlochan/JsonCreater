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
//            chooseForGroup(studentChooseSystem);
            chooseForFaculty(studentChooseSystem);
        //       chooseForStupidGroup(studentChooseSystem);


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
//                if(group.getGroupName().contains("мн-2-1-21")) {
                    LinkedList<StudentData> studentDataLinkedList = new LinkedList<>();
                    file = new File(directory, group.getGroupName() + ".json");
                    file.createNewFile();
                    System.out.println(group.getGroupName());
                    for (String studentPass : group.getStudentList()) {
                        studentDataLinkedList.add(new StudentData(studentPass));
                    }
                    System.out.println();
                    JSONCreater.writeJsonObject(file.getAbsolutePath(), studentDataLinkedList);
//                }
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

    //    метод для тупого вказування айдішок студентів
    private static void chooseForStupidGroup(StudentChooseSystem studentChooseSystem){
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        try{
            System.out.println("Режим роботи програми, який забезпечує створення json-файлів для конкретної групи");
            System.out.println("Розпочався процес формування json-файлів. Зачекайте кілька митей");
            ArrayList<String> studentList = new ArrayList<>();

            studentList.add("68075");
            studentList.add("68076");
            studentList.add("68077");
            studentList.add("68078");
            studentList.add("68079");
            studentList.add("68080");
            studentList.add("68081");
            studentList.add("68082");
            studentList.add("68083");
            studentList.add("68084");
            studentList.add("68085");
            studentList.add("68086");
            studentList.add("68087");
            studentList.add("68088");
            studentList.add("68089");
            studentList.add("68090");
            studentList.add("68091");
            studentList.add("68092");
            studentList.add("68093");
            studentList.add("68094");
            studentList.add("68095");
            studentList.add("68096");
            studentList.add("68097");
            studentList.add("68098");
            studentList.add("68345");





            LinkedList<StudentData> studentDataLinkedList = new LinkedList<>();
            for (String studentPass : studentList) {
                studentDataLinkedList.add(new StudentData(studentPass));
            }
            file = new File("stupidGroup" + ".json");
            file.createNewFile();
            JSONCreater.writeJsonObject(file.getAbsolutePath(), studentDataLinkedList);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}