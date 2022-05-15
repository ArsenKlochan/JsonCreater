package training;

import database.BaseConnector;
import database.objects.Mark;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class getMarksFromModule {
    private String disciplineId;
    private String disciplineUkr;
    private String disciplineEng;
    private int hours;
    private int mark;
    //  динамічні масиви для дисциплін, курсових робіт, курсових проектів, розрахунково-графічних робіт, практик
    private LinkedList<Mark> marks = new LinkedList<>();
    private LinkedList<Mark> courseWorks = new LinkedList<>();
    private LinkedList<Mark> courseProjects = new LinkedList<>();
    private LinkedList<Mark> calculationAndGraphicWorks = new LinkedList<>();
    private LinkedList<Mark> practisies = new LinkedList<>();
    //    оцінка за підсуикову атестацію
    private Mark atestatiion;
    //   Списки ідентифікаторів дисциплін, які являються практиками
    private static String[] practiseDId = {"1112", "1156", "1157", "1158", "1159", "1160", "1210", "5522", "5523", "5547", "5591", "5600", "5602", "5616", "5617", "5643", "5657", "5960", "5995", "6023", "6050", "6068", "6069", "6070", "6071", "6105", "6111", "6121", "6130", "6131", "6174", "6202", "6203", "6205", "6206", "6207", "6208", "6306", "6404", "6405", "6416", "6428", "6433", "6563", "6565", "6566", "6567", "6568", "6839", "6864", "6865", "6866", "6867", "6868", "6869", "7058", "7059", "7061", "7106", "7432", "7434", "7439", "7441", "7457", "7537", "8045", "8046", "8392", "8476", "8477", "8559", "9099", "9160", "9368", "9369", "9547", "9549"};
    private static List<String> listPracties = Arrays.asList(practiseDId);
    //   Списки ідентифікаторів дисциплін, які являються підсумковими атестаціями
    private static String[] atestationID = {"5590", "6065", "6066", "6067", "6167", "6175", "6195", "6367", "6368", "6369", "6424", "6429", "6430", "6434", "6444", "6447", "6507", "6516", "6520", "6521", "6528", "6558", "6710", "6711", "6813", "6814", "6821", "6828", "6829", "6830", "6831", "6832", "6870", "6871", "6877", "6882", "6883", "6884", "6885", "6886", "6887", "6888", "6889", "6890", "6891", "6892", "6893", "6894", "6895", "6896", "6897", "6898", "6899", "6900", "6902", "6903", "6904", "6958", "6959", "6963", "6964", "6965", "6967", "6968", "6970", "6971", "6972", "6973", "6974", "6975", "6976", "6977", "6978", "6979", "6980", "6981", "6982", "6983", "6984", "6985", "6986", "6987", "6988", "6989", "6990", "6991", "6992", "6993", "7003", "7004", "7005", "7006", "7007", "7010", "7011", "7065", "7066", "7067", "7087", "7088", "7089", "7099", "7101", "7102", "7103", "7107", "7108", "7109", "7110", "7111", "7112", "7113", "7114", "7115", "7116", "7117", "7118", "7137", "7138", "7139", "7446", "7447", "7453", "7455", "7456", "7599", "7618", "7668", "7671", "7672", "7673", "7674", "7675", "7676", "7677", "7678", "7679", "7680", "8081", "8254", "8360", "8519", "8653", "8755", "8756", "9107", "9153", "9251", "9261", "9326", "9338", "9357", "9359", "9360", "9435", "9551"};
    private static List<String> listAtestations = Arrays.asList(atestationID);
    //    об'єкт з'єднання з базою даних
    private static BaseConnector connector = new BaseConnector();
    //    отримання даних з табллиці module
    private void getDataFromModule(Statement statement, String pass){
        ResultSet resultSet3 = null;
        int typeOfControl;
        try {
            resultSet3 = statement.executeQuery("SELECT D_ID, HH, type_control_id, mark FROM module WHERE pass = '" + pass + "'");
            while (resultSet3.next()) {
                disciplineId = resultSet3.getString(1);
                hours = Integer.parseInt(resultSet3.getString(2));
                typeOfControl = Integer.parseInt(resultSet3.getString(3));
                mark = Integer.parseInt(resultSet3.getString(4));
                disciplineUkr = getDisciplineName(disciplineId)[0];
                disciplineEng = getDisciplineName(disciplineId)[1];
                if(listPracties.contains(disciplineId)){
                    practisies.add(new Mark(disciplineUkr, disciplineEng, hours, mark));
                }
                else if (listAtestations.contains(disciplineId)){
                    atestatiion = new Mark(disciplineUkr, disciplineEng, hours, mark);
                }
                else if(typeOfControl == 7){
                    calculationAndGraphicWorks.add(new Mark("Розрахунково-графічна робота з дисципліни «" + disciplineUkr + "»", "Calculation and Graphic Work in "+ disciplineEng, hours, mark));
                }
                else if(typeOfControl == 6){
                    courseProjects.add(new Mark("Курсовий проект з дисципліни «" + disciplineUkr + "»", "Course Project in "+ disciplineEng, hours, mark));
                }
                else if(typeOfControl == 5){
                    courseWorks.add(new Mark("Курсова робота з дисципліни «" + disciplineUkr + "»", "Course Paper in "+ disciplineEng, hours, mark));
                }
                else if(typeOfControl > 1){
                    marks.add(new Mark(disciplineUkr, disciplineEng, hours, mark));
                }
            }
            this.marks = marks;
            this.practisies = practisies;
            this.atestatiion = atestatiion;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    //  метод отримання назви дисципліни українською та англійською за ідентифікатором з таблиці дисциплін
    private static String[] getDisciplineName(String disciplineId){
        String[] nameAray = new String[2];
        try (Connection connection = connector.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet4 = statement.executeQuery("SELECT DFullName, DLatName FROM duscuplinu WHERE D_ID = '" +disciplineId + "'");
            while (resultSet4.next()) {
                nameAray[0] = resultSet4.getString(1);
                nameAray[1] = resultSet4.getString(2);
            }
        }
        catch (Exception ex){
            System.out.println("Bad connection");
            ex.printStackTrace();
        }
        return nameAray;
    }
}
