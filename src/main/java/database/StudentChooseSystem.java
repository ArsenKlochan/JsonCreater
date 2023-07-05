package database;

import database.objects.Faculty;
import database.objects.Group;

import java.beans.IntrospectionException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class StudentChooseSystem {
    private static String[] atestationID = {"9166", "9821", "5590", "6065", "6066", "6067", "6167", "6175", "6195", "6367", "6368", "6369", "6424", "6429", "6430", "6434", "6444", "6447", "6507", "6516", "6520", "6521", "6528", "6558", "6710", "6711", "6813", "6814", "6821", "6828", "6829", "6830", "6831", "6832", "6870", "6871", "6877", "6882", "6883", "6884", "6885", "6886", "6887", "6888", "6889", "6890", "6891", "6892", "6893", "6894", "6895", "6896", "6897", "6898", "6899", "6900", "6902", "6903", "6904", "6958", "6959", "6963", "6964", "6965", "6967", "6968", "6970", "6971", "6972", "6973", "6974", "6975", "6976", "6977", "6978", "6979", "6980", "6981", "6982", "6983", "6984", "6985", "6986", "6987", "6988", "6989", "6990", "6991", "6992", "6993", "7003", "7004", "7005", "7006", "7007", "7010", "7011", "7065", "7066", "7067", "7087", "7088", "7089", "7099", "7101", "7102", "7103", "7107", "7108", "7109", "7110", "7111", "7112", "7113", "7114", "7115", "7116", "7117", "7118", "7137", "7138", "7139", "7446", "7447", "7453", "7455", "7456", "7590", "7599", "7618", "7668", "7671", "7672", "7673", "7674", "7675", "7676", "7677", "7678", "7679", "7680", "8081", "8254", "8360", "8519", "8653", "8755", "8756", "9107", "9153", "9251", "9261", "9326", "9338", "9357", "9359", "9360", "9435", "9551", "9560", "9720"};
    private static List<String> listAtestations = Arrays.asList(atestationID);
    private static String[] faculty = {"ФТБ", "АМФ", "ФМЛТ", "ФЗДН(ЦПК ППСз)", "ФЗДН(ЦЗДН)", "ФТІТ", "ФЗДН(ЦМОд)", "ФЗДН(ЦПК ППСд)", "ФЗДН(ЦМОз)", "ФЕП"};
    private static String[] formOfStudy = {"д", "з"};
    private static BaseConnector connector = new BaseConnector();
    private static Connection connection = connector.getConnection();
    private static HashSet<String> studentsId = new HashSet<>();

    private static ArrayList<Group> groups = new ArrayList<>();
    private static ArrayList<String> groupsName = new ArrayList<>();

    private static ArrayList<Faculty> faculties = new ArrayList<>();
    private static ArrayList<String> facultiesId = new ArrayList<>();

    public ArrayList<Faculty> getFaculties() {
        return faculties;
    }
//    метод вибору студентів у чких є атестація, тобто які випускаються
    public void chooseStudent(){
        System.out.println("Йде процес відбору студентів в яких є атестація, тобто які будуть випускатись в цьому році. Зачекайте кілька хвилин.");
        try {
            Statement statement = connection.createStatement();
//            вибір студентів в яких є атестація (які випускаються)
            for(String D_ID: listAtestations){
                ResultSet resultSet = statement.executeQuery("SELECT pass from vuknavchplan WHERE D_ID = '" + D_ID + "'");
                while (resultSet.next()){
                    studentsId.add(resultSet.getString(1));
                }
            }
            for (String studentId: studentsId){
                distributionOfStudent(studentId);
            }
            System.out.println("Student choosing done");
        } catch (SQLException throwables) {
            System.out.println("Something wrong with connection at students choose");
            throwables.printStackTrace();
        }
    }
//  метод розподілу студентів по групам
    private void distributionOfStudent(String studentID){
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT S_ID, NomGroup, NomKurs, FAC_ID, F_ID, RikKurs FROM anketu WHERE pass= '" + studentID + "'");
            while (resultSet.next()) {
                String S_ID = resultSet.getString(1);
                int facultyId = Integer.parseInt(resultSet.getString(4));
                String facultyName = facultyId + " " + faculty[facultyId-1];
                String nameOfSpeciality = getSpecialityName(S_ID);
                String nameOfGroup = nameOfSpeciality + "-" + resultSet.getString(3) + "-" + resultSet.getString(2) + "-" + resultSet.getString(6) + formOfStudy[Integer.parseInt(resultSet.getString(5))-1];
                groups.get(checkGroup(nameOfGroup, facultyName)).getStudentList().add(studentID);
            }
        } catch (SQLException throwables) {
            System.out.println("Something wrong with connection at students distribution");
            throwables.printStackTrace();
        }

    }
//    метод визначення шифру спеціальності
    private String getSpecialityName(String S_ID) {
        String nameOfSpeciality = "";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT FAC_ID, SAbrCyr FROM spec WHERE S_ID = '" + S_ID + "'");
            while (resultSet.next()) {
                nameOfSpeciality = resultSet.getString(1) + " " + resultSet.getString(2);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nameOfSpeciality;
    }
//   метод знаходження індексу групи в динамічному масиві, якщо групи не знаходить, то дадає його в масив
    private int checkGroup(String nameGroup, String nameFaculty){
        if(!groupsName.contains(nameGroup)){
            Group temp = new Group(nameGroup);
            groups.add(temp);
            groupsName.add(nameGroup);
            faculties.get(checkFaculty(nameFaculty)).getGroupList().add(temp);
        }
        return groupsName.indexOf(nameGroup);
    }
//   метод знаходження індексу факультету в динамічному масиві, якщо факультет не знаходить, то дадає його в масив
    private int checkFaculty(String facultyName){
        if(!facultiesId.contains(facultyName)){
            faculties.add(new Faculty(facultyName));
            facultiesId.add(facultyName);
        }
        return facultiesId.indexOf(facultyName);
    }

}
