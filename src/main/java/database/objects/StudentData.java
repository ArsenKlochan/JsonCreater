package database.objects;

import database.BaseConnector;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.Collator;
import java.util.*;

public class StudentData {
    private String date_birth = "";
    private String series_diploma = "";
    private String number_diploma = "";
    private String diploma_type = "Диплом / Diploma";
    private String date_diploma_receiving = "";
    private String number_supplement_diploma = "";
    private String date_receiving_supplement = "";
    private String prev_diploma_number = "";
    private String prev_diploma_date = "";
//    private String prev_diploma_theme = "";
    private String name_dok_osv = "";
    private String prev_navch_zakl = "";
    private String prev_navch_zakl_en = "";
    private String name_ukr = "";
    private String name_eng = "";
    private String surname_ukr = "";
    private String surname_eng = "";
    private String middlename_ukr = "";
    private String middlename_eng = "";
    private String diploma_theme = "";
    private String diploma_theme_E = "";
    private String total_credits = "";
    private String average_mark = "";
    private String average_grade = "";
    private String average_ECTS = "";
    private LinkedList<Mark> marks = new LinkedList<>();
    private LinkedList<String> marksID = new LinkedList<>(); // ІД-шки дисциплін, використовується для перевірки двохсеместрових дисциплін
    private LinkedList<Mark> courseWorks = new LinkedList<>();
    private LinkedList<Mark> courseProjects = new LinkedList<>();
    private LinkedList<Mark> calculationAndGraphicWorks = new LinkedList<>();
    private LinkedList<Mark> practisies = new LinkedList<>();
    private Mark atestatiion = new Mark("1","1",60,70);

    private String disciplineId;
    private String disciplineUkr;
    private String disciplineEng;
    private int hours;
    private int typeOfControl;
    private int mark;


    private static String[] practiseDId = {"1112", "1156", "1157", "1158", "1159", "1160", "1210", "5522", "5523", "5547", "5591", "5600", "5602", "5616", "5617", "5643", "5657", "5960", "5995", "6023", "6050", "6068", "6069", "6070", "6071", "6105", "6111", "6121", "6130", "6131", "6174", "6202", "6203", "6205", "6206", "6207", "6208", "6306", "6404", "6405", "6416", "6428", "6433", "6563", "6565", "6566", "6567", "6568", "6839", "6864", "6865", "6866", "6867", "6868", "6869", "7058", "7059", "7061", "7106", "7432", "7434", "7439", "7441", "7457", "7537", "8045", "8046", "8392", "8476", "8477", "8559", "9099", "9160", "9368", "9369", "9547", "9549"};
    private static List<String> listPracties = Arrays.asList(practiseDId);
    private static String[] atestationID = {"5590", "6065", "6066", "6067", "6167", "6175", "6195", "6367", "6368", "6369", "6424", "6429", "6430", "6434", "6444", "6447", "6507", "6516", "6520", "6521", "6528", "6558", "6710", "6711", "6813", "6814", "6821", "6828", "6829", "6830", "6831", "6832", "6870", "6871", "6877", "6882", "6883", "6884", "6885", "6886", "6887", "6888", "6889", "6890", "6891", "6892", "6893", "6894", "6895", "6896", "6897", "6898", "6899", "6900", "6902", "6903", "6904", "6958", "6959", "6963", "6964", "6965", "6967", "6968", "6970", "6971", "6972", "6973", "6974", "6975", "6976", "6977", "6978", "6979", "6980", "6981", "6982", "6983", "6984", "6985", "6986", "6987", "6988", "6989", "6990", "6991", "6992", "6993", "7003", "7004", "7005", "7006", "7007", "7010", "7011", "7065", "7066", "7067", "7087", "7088", "7089", "7099", "7101", "7102", "7103", "7107", "7108", "7109", "7110", "7111", "7112", "7113", "7114", "7115", "7116", "7117", "7118", "7137", "7138", "7139", "7446", "7447", "7453", "7455", "7456", "7599", "7618", "7668", "7671", "7672", "7673", "7674", "7675", "7676", "7677", "7678", "7679", "7680", "8081", "8254", "8360", "8519", "8653", "8755", "8756", "9107", "9153", "9251", "9261", "9326", "9338", "9357", "9359", "9360", "9435", "9551"};
    private static List<String> listAtestations = Arrays.asList(atestationID);
    private static BaseConnector connector = new BaseConnector();

//    Отримання даних про студента з БД
    public StudentData(String pass) {
        System.out.println("new StudentData");
        try (Connection connection = connector.getConnection()) {
            Statement statement = connection.createStatement();
//отримання даних з таблиці анкета
            ResultSet resultSet1 = statement.executeQuery("SELECT Birth, SNomDokOsv, DateDokOsv, NameDokOsv, NavchZakl, NavchZaklEn, P, I, B, p_en, i_en, b_en FROM anketu WHERE pass = '" + pass + "'");
            while (resultSet1.next()) {
                this.date_birth = resultSet1.getString(1);
                this.prev_diploma_number = resultSet1.getString(2);
                this.prev_diploma_date = resultSet1.getString(3);
                this.name_dok_osv = resultSet1.getString(4);
                this.prev_navch_zakl = resultSet1.getString(5);
                this.prev_navch_zakl_en = resultSet1.getString(6);
                this.surname_ukr = resultSet1.getString(7);
                this.name_ukr = resultSet1.getString(8);
                this.middlename_ukr = resultSet1.getString(9);
                this.surname_eng = resultSet1.getString(10);
                this.name_eng = resultSet1.getString(11);
                this.middlename_eng = resultSet1.getString(12);
            }
//отримання даних з таблиці додаток
            ResultSet resultSet2 = statement.executeQuery("SELECT ser_dyplom, nom_dyplom, NomDod, topicU, topicE, release_date FROM dodatok WHERE pass = '" + pass + "'");
            while (resultSet2.next()) {
                this.series_diploma = resultSet2.getString(1);
                this.number_diploma = resultSet2.getString(2);
                this.number_supplement_diploma = resultSet2.getString(3);
                this.diploma_theme = resultSet2.getString(4);
                this.diploma_theme_E = resultSet2.getString(5);
                this.date_diploma_receiving = resultSet2.getString(6);
                this.date_receiving_supplement = resultSet2.getString(6);
            }

//            getMarksFromDepartment(pass);
//            отримання даних про оцінки
//            з системи кафедра
//            getDataFromModule(statement, pass);
            getMarksFromDekanat(statement, pass, "arhivnavchplan");
            getMarksFromDekanat(statement, pass, "vuknavchplan");

            statement.close();
        }
        catch (Exception ex){
            System.out.println("Bad connection");
            ex.printStackTrace();
        }
    }
//    отримання даних з табллиці module
    private void getDataFromModule(Statement statement, String pass){
        ResultSet resultSet3 = null;
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

//ім'я дисципліни
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
//     Отримання даних про оцінки студента з таблиці Деканат
    public void getMarksFromDekanat(Statement statement, String pass, String table){
        ResultSet resultSet4 = null;
        String courseWork;
        String courseProject;
        String calculationWork;
        String exam;
        String zalik;
        String dyferentialZalik;
        try {
            resultSet4 = statement.executeQuery("SELECT D_ID, HH, KR, KP, ZV, EO, RGR, dyfZal FROM "+ table +" WHERE pass = '" + pass + "'");
            while (resultSet4.next()) {
                disciplineId = resultSet4.getString(1);
                hours = Integer.parseInt(resultSet4.getString(2));
                courseWork = resultSet4.getString(3);
                courseProject = resultSet4.getString(4);
                zalik = resultSet4.getString(5);
                exam = resultSet4.getString(6);
                calculationWork = resultSet4.getString(7);
                dyferentialZalik = resultSet4.getString(8);
                disciplineUkr = getDisciplineName(disciplineId)[0];
                disciplineEng = getDisciplineName(disciplineId)[1];
                if(hours != 0){
                    if(courseWork != null && courseWork != "0"){
                        courseWorks.add(new Mark("Курсова робота з дисципліни «" + disciplineUkr + "»", "Course Paper in "+ disciplineEng, hours, Integer.parseInt(courseWork)));
                    }
                    if(courseProject != null && courseProject != "0"){
                       courseProjects.add(new Mark("Курсовий проект з дисципліни «" + disciplineUkr + "»", "Course Project in "+ disciplineEng, hours, Integer.parseInt(courseProject)));
                    }
                    if(calculationWork != null && calculationWork != "0"){
                        calculationAndGraphicWorks.add(new Mark("Розрахунково-графічна робота з дисципліни «" + disciplineUkr + "»", "Calculation and Graphic Work in "+ disciplineEng, hours, Integer.parseInt(calculationWork)));
                    }
                    if(zalik != null && zalik != "0"){
                        mark = Integer.parseInt(zalik);
                    }
                    else if(exam != null && exam != "0"){
                        mark = Integer.parseInt(exam);
                    }
                    else if(dyferentialZalik != null && dyferentialZalik != "0"){
                        mark = Integer.parseInt(dyferentialZalik);
                    }

                    if(listPracties.contains(disciplineId)){
                        practisies.add(new Mark(disciplineUkr, disciplineEng, hours, mark));
                    }
                    else if (listAtestations.contains(disciplineId)){
                        atestatiion = new Mark(disciplineUkr, disciplineEng, hours, mark);
                    }
                    else {
                        marks.add(new Mark(disciplineUkr, disciplineEng, hours, mark));
                    }
                }
            }
        }
        catch (Exception ex){
            System.out.println("Bad connection");
            ex.printStackTrace();
        }
    }

//    private static void ChekNultySemesnrMarks(LinkedList<Mark> list){
//        for ()
//    }

    @Override
    public String toString() {
        return "StudentData{" +
                "date_birth='" + date_birth + '\'' +
                ", series_diploma='" + series_diploma + '\'' +
                ", number_diploma='" + number_diploma + '\'' +
                ", diploma_type='" + diploma_type + '\'' +
                ", date_diploma_receiving='" + date_diploma_receiving + '\'' +
                ", number_supplement_diploma='" + number_supplement_diploma + '\'' +
                ", date_receiving_supplement='" + date_receiving_supplement + '\'' +
                ", prev_diploma_number='" + prev_diploma_number + '\'' +
                ", prev_diploma_date='" + prev_diploma_date + '\'' +
                ", name_dok_osv='" + name_dok_osv + '\'' +
                ", prev_navch_zakl='" + prev_navch_zakl + '\'' +
                ", prev_navch_zakl_en='" + prev_navch_zakl_en + '\'' +
                ", name_ukr='" + name_ukr + '\'' +
                ", name_eng='" + name_eng + '\'' +
                ", surname_ukr='" + surname_ukr + '\'' +
                ", surname_eng='" + surname_eng + '\'' +
                ", middlename_ukr='" + middlename_ukr + '\'' +
                ", middlename_eng='" + middlename_eng + '\'' +
                ", diploma_theme='" + diploma_theme + '\'' +
                ", diploma_theme_E='" + diploma_theme_E + '\'' +
                ", total_credits='" + total_credits + '\'' +
                ", average_mark='" + average_mark + '\'' +
                ", average_grade='" + average_grade + '\'' +
                ", average_ECTS='" + average_ECTS + '\'' +
                ", marks=" + marks.toString() +
                ", courseWorks=" + courseWorks.toString() +
                ", courseProjects=" + courseProjects.toString() +
                ", CalculationAndGraphicWorks=" + calculationAndGraphicWorks.toString() +
                ", practisies=" + practisies.toString()+
                ", atestatiion=" + atestatiion +
                '}';
    }
    public String getDate_birth() {
        return date_birth;
    }
    public String getSeries_diploma() {
        return series_diploma;
    }
    public String getNumber_diploma() {
        return number_diploma;
    }
    public String getDiploma_type() {
        return diploma_type;
    }
    public String getDate_diploma_receiving() {
        return date_diploma_receiving;
    }
    public String getNumber_supplement_diploma() {
        return number_supplement_diploma;
    }
    public String getDate_receiving_supplement() {
        return date_receiving_supplement;
    }
    public String getPrev_diploma_number() {
        return prev_diploma_number;
    }
    public String getPrev_diploma_date() {
        return prev_diploma_date;
    }
    public String getName_dok_osv() {
        return name_dok_osv;
    }
    public String getPrev_navch_zakl() {
        return prev_navch_zakl;
    }
    public String getPrev_navch_zakl_en() {
        return prev_navch_zakl_en;
    }
    public String getName_ukr() {
        return name_ukr;
    }
    public String getName_eng() {
        return name_eng;
    }
    public String getSurname_ukr() {
        return surname_ukr;
    }
    public String getSurname_eng() {
        return surname_eng;
    }
    public String getMiddlename_ukr() {
        return middlename_ukr;
    }
    public String getMiddlename_eng() {
        return middlename_eng;
    }
    public String getDiploma_theme() {
        return diploma_theme;
    }
    public String getDiploma_theme_E() {
        return diploma_theme_E;
    }
    public String getTotal_credits() {
        return total_credits;
    }
    public String getAverage_mark() {
        return average_mark;
    }
    public String getAverage_grade() {
        return average_grade;
    }
    public String getAverage_ECTS() {
        return average_ECTS;
    }
    public LinkedList<Mark> getMarks() {
        return sort(marks);
    }
    public LinkedList<Mark> getCourseWorks() {
        return courseWorks;
    }
    public LinkedList<Mark> getCourseProjects() {
        return courseProjects;
    }
    public LinkedList<Mark> getCalculationAndGraphicWorks() {
        return calculationAndGraphicWorks;
    }
    public LinkedList<Mark> getPractisies() {
        return practisies;
    }
    public Mark getAtestatiion() {
        return atestatiion;
    }

//    метод сортування масиву з оцінками по назві дисциплін
    private LinkedList<Mark> sort(LinkedList<Mark> marks){
        Collator collator = Collator.getInstance(new Locale("uk","UA"));
        Collections.sort(marks,(d1, d2) ->{
            return collator.compare(d1.getDiscipline_ukr().toLowerCase(),d2.getDiscipline_ukr().toLowerCase());
        });
        System.out.println(marks);
        return checkDublicate(marks);
    }
    private LinkedList<Mark> checkDublicate(LinkedList<Mark> marks){
        System.out.println("checkDublicate");
        int counter = 0;
        int mark= 0;
        for (int i = 1; i < marks.size(); i++){
//            mark = marks.get(i-1).getMark();
            if (marks.get(i).getDiscipline_ukr().equals(marks.get(i-1).getDiscipline_ukr())){
                counter++;
                mark += marks.get(i).getMark();
                marks.remove(i);
                i--;
            }
            else {
                mark += marks.get(i-1).getMark();
                marks.get(i-1).setMark((int)Math.ceil(mark*1.0/(counter+1)));
                counter=0;
                mark=0;
            }
        }
        System.out.println(marks);
        return marks;
    }
}
