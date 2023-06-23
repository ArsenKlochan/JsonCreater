package database.objects;

import com.fasterxml.jackson.databind.util.ISO8601Utils;
import database.BaseConnector;
import org.w3c.dom.ls.LSOutput;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.Collator;
import java.util.*;

public class StudentData {
    private static int countOfStudents = 0;
//    поля для джейсона (дані про студента)
    private String date_birth = "";
    private String series_diploma = "";
    private String number_diploma = "";
    private String diploma_type = "Диплом / Diploma";
    private String date_diploma_receiving = "";
    private String number_supplement_diploma = "";
    private String date_receiving_supplement = "";
    private String prev_diploma_number = "";
    private String prev_diploma_date = "";
    private String prev_diploma_date_slash = "";
//    private String prev_diploma_theme = "";
    private String name_dok_osv = "";
    private String name_dok_osv_en = "";
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
    private String person_Fo_EdboId = "";
    private String person_Edu_EdboId = "";
    private String percent_twos = "";
    private String percent_threes = "";
    private String percent_fours = "";
    private String percent_fives = "";
    private String percent_sum = "";
    private String excellence = "";
    private String excellenceEng = "";
//    кількість годин по КР, КП, РГР по факультетах (індекс в масиві -1 є номером факультету)
//    {Транспортного будівництва, Автомеханічний, Менеджменту логістики та туризму, ЦПКз, ЦЗДН, Транспортних та інформаційних технроргій, ЦМОд, ЦПКд ЦМОз, Економіка  та права}
    private int [] kpHoursForFaculty ={45,45,30,45,30,15,45,45,45,45};
    private int [] krHoursForFaculty ={30,30,30,30,30,15,30,30,30,30};
    private int [] rgrHoursForFaculty ={30,30,30,30,30,15,30,30,30,30};
//  ІД-шки дисциплін, використовується для перевірки двохсеместрових дисциплін
    private LinkedList<String> marksID = new LinkedList<>();
//  динамічні масиви для дисциплін, курсових робіт, курсових проектів, розрахунково-графічних робіт, практик
    private LinkedList<Mark> marks = new LinkedList<>();
    private LinkedList<Mark> courseWorks = new LinkedList<>();
    private LinkedList<Mark> courseProjects = new LinkedList<>();
    private LinkedList<Mark> calculationAndGraphicWorks = new LinkedList<>();
    private LinkedList<Mark> practisies = new LinkedList<>();
//    оцінка за підсумкову атестацію
    private LinkedList<Mark> atestatiion = new LinkedList<>();
//    середня оцінка
    private Mark avarage;
    private static int sumMark = 0;
    private static int sumHours = 0;
    private static int countMarks = 0;
    // кількість п'ятірок, чітвірок, трійок тв двійок
    private static int five = 0;
    private static int four = 0;
    private static int three = 0;
    private static int two = 0;
//    матриці для назви документів про освіту українською та англійською
    private static String [] nameDokOsv={"свідоцтво про здобуття повної загальної середньої освіти","атестат про повну загальну середню освіту","диплом фахового молодшого бакалавра","диплом молодшого спеціаліста","диплом бакалавра","диплом спеціаліста","диплом магістра"};
    private static String [] nameDokOsvEn={"Certificate of Complete General Secondary Education","Certificate of Complete General Secondary Education","Professional Junior Bachelor Diploma","Junior Specialist Diploma","Bachelor’s Diploma","Specialist Diploma","Master’s Diploma"};

//   Списки ідентифікаторів дисциплін, які являються практиками
    private static String[] practiseDId = {"1112", "1156", "1157", "1158", "1159", "1160", "1210", "5522", "5523", "5547", "5591", "5600", "5602", "5616", "5617", "5643", "5657", "5960", "5995", "6023", "6050", "6068", "6069", "6070", "6071", "6105", "6111", "6121", "6130", "6131", "6174", "6202", "6203", "6205", "6206", "6207", "6208", "6306", "6404", "6405", "6416", "6428", "6433", "6563", "6565", "6566", "6567", "6568", "6839", "6864", "6865", "6866", "6867", "6868", "6869", "7058", "7059", "7061", "7106", "7432", "7434", "7439", "7441", "7457", "7537", "8045", "8046", "8392", "8476", "8477", "8559", "9099", "9160", "9368", "9369", "9547", "9549"};
    private static List<String> listPracties = Arrays.asList(practiseDId);
//   Списки ідентифікаторів дисциплін, які являються підсумковими атестаціями
    private static String[] atestationID = {"9166", "9821", "7590" ,"5590", "6065", "6066", "6067", "6167", "6175", "6195", "6367", "6368", "6369", "6424", "6429", "6430", "6434", "6444", "6447", "6507", "6516", "6520", "6521", "6528", "6558", "6710", "6711", "6813", "6814", "6821", "6828", "6829", "6830", "6831", "6832", "6870", "6871", "6877", "6882", "6883", "6884", "6885", "6886", "6887", "6888", "6889", "6890", "6891", "6892", "6893", "6894", "6895", "6896", "6897", "6898", "6899", "6900", "6902", "6903", "6904", "6958", "6959", "6963", "6964", "6965", "6967", "6968", "6970", "6971", "6972", "6973", "6974", "6975", "6976", "6977", "6978", "6979", "6980", "6981", "6982", "6983", "6984", "6985", "6986", "6987", "6988", "6989", "6990", "6991", "6992", "6993", "7003", "7004", "7005", "7006", "7007", "7010", "7011", "7065", "7066", "7067", "7087", "7088", "7089", "7099", "7101", "7102", "7103", "7107", "7108", "7109", "7110", "7111", "7112", "7113", "7114", "7115", "7116", "7117", "7118", "7137", "7138", "7139", "7446", "7447", "7453", "7455", "7456", "7599", "7618", "7668", "7671", "7672", "7673", "7674", "7675", "7676", "7677", "7678", "7679", "7680", "8081", "8254", "8360", "8519", "8653", "8755", "8756", "9107", "9153", "9251", "9261", "9326", "9338", "9357", "9359", "9360", "9435", "9551", "9560", "9720"};
    private static List<String> listAtestations = Arrays.asList(atestationID);
//    Список ідентифікаторів дисциплін, які є факультативними і не вписуються в додаток
    private static String[] optionalDisciplineId = {"6346", "5692", "804"};
    private static List<String> listOptionalDiscipline = Arrays.asList(optionalDisciplineId);

//    об'єкт з'єднання з базою даних
    private static BaseConnector connector = new BaseConnector();
    private static Connection connection = connector.getConnection();
    int count = 0;

    private static int facultyId;

//    Отримання даних про студента з БД
    public StudentData(String pass) {
        try {
            five = 0;
            four = 0;
            three = 0;
            two = 0;
            countMarks = 0;
            System.out.print(".");
            Statement statement = connection.createStatement();
//отримання даних з таблиці анкета
            ResultSet resultSet1 = statement.executeQuery("SELECT Birth, SNomDokOsv, DateDokOsv, NameDokOsv, NavchZakl, NavchZaklEn, P, I, B, p_en, i_en, b_en, FAC_ID, educationEdboId, personEdboId FROM anketu WHERE pass = '" + pass + "'");
            while (resultSet1.next()) {
                this.date_birth = dateFormatString(resultSet1.getString(1));
                String birthDate = this.date_birth.substring(0,2)+"/"+this.date_birth.substring(3,5)+"/"+this.date_birth.substring(6,10); //дата народжденнґя через слеш
                this.date_birth=birthDate;
                this.prev_diploma_number = resultSet1.getString(2);
                this.prev_diploma_date = dateFormatString(resultSet1.getString(3));
                this.prev_diploma_date_slash=this.prev_diploma_date.substring(0,2)+"/"+this.prev_diploma_date.substring(3,5)+"/"+this.prev_diploma_date.substring(6,10);
                this.name_dok_osv = resultSet1.getString(4);
                this.name_dok_osv_en = getNameDocOsvEn(name_dok_osv);
                this.prev_navch_zakl = resultSet1.getString(5) + ", Україна";
                this.prev_navch_zakl_en = resultSet1.getString(6) + ", Ukraine";
                this.surname_ukr = resultSet1.getString(7);
                this.name_ukr = resultSet1.getString(8);
                this.middlename_ukr = resultSet1.getString(9);
                this.surname_eng = resultSet1.getString(10);
                this.name_eng = resultSet1.getString(11);
                this.middlename_eng = resultSet1.getString(12);
                facultyId = Integer.parseInt(resultSet1.getString(13));
                this.person_Edu_EdboId = resultSet1.getString(14);
                this.person_Fo_EdboId = resultSet1.getString(15);
            }
//отримання даних з таблиці додаток
            ResultSet resultSet2 = statement.executeQuery("SELECT ser_dyplom, nom_dyplom, topicU, topicE, release_date, NomDod FROM dodatok WHERE pass = '" + pass + "'");
            while (resultSet2.next()) {
                this.series_diploma = resultSet2.getString(1);
                this.number_diploma = resultSet2.getString(2);
                this.diploma_theme = resultSet2.getString(3);
                this.diploma_theme_E = resultSet2.getString(4);
                this.date_diploma_receiving = dateFormatString(resultSet2.getString(5));
                this.date_receiving_supplement = dateFormatString(resultSet2.getString(5));
                this.number_supplement_diploma = resultSet2.getString(6);
            }
//            отримання оцінок з дисциплін за попередні роки навчання
            getMarksFromDekanat(statement, pass, "arhivnavchplan", facultyId);
//            отримання оцінок з дисциплін за поточний рік навчання
            getMarksFromDekanat(statement, pass, "vuknavchplan", facultyId);
            sort(marks);
            sort(courseProjects);
            sort(courseWorks);
            sort(calculationAndGraphicWorks);
            sort(practisies);
            checkDublicate(marks);
//            checkDublicate(courseProjects);
//            checkDublicate(courseWorks);
//            checkDublicate(calculationAndGraphicWorks);
//            checkDublicate(practisies);
            getAvarageMark();
            statement.close();
        }
        catch (Exception ex){
            System.out.println("Bad connection");
            ex.printStackTrace();
        }
    }

//  метод отримання назви дисципліни українською та англійською за ідентифікатором з таблиці дисциплін
    private static String[] getDisciplineName(String disciplineId){
        String[] nameAray = new String[2];
        try {
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
    public void getMarksFromDekanat(Statement statement, String pass, String table, int facultyId){
        String disciplineId = "";
        String disciplineUkr;
        String disciplineEng;
        int hours = 0;
        int mark = 0;
        ResultSet resultSet4 = null;
        String courseWork;
        String courseProject;
        String calculationWork;
        String exam;
        String zalik;
        String dyferentialZalik;
        String[] disciplineName = new String[2];
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
                disciplineName = getDisciplineName(disciplineId);
                disciplineUkr = disciplineName[0];
                disciplineEng = disciplineName[1];
                if(hours != 0 && !listOptionalDiscipline.contains(disciplineId)){
                    int hourKp = kpHoursForFaculty[facultyId-1];
                    int hourKR = krHoursForFaculty[facultyId-1];
                    int hourRgr = rgrHoursForFaculty[facultyId-1];
                    if(courseWork != null && courseWork != "0"){
                        courseWorks.add(new Mark("Курсова робота з дисципліни «" + disciplineUkr + "»", "Course Paper in "+ disciplineEng, hourKR, Integer.parseInt(courseWork)));
                        hours -= hourKR;
                    }
                    if(courseProject != null && courseProject != "0"){
                       courseProjects.add(new Mark("Курсовий проект з дисципліни «" + disciplineUkr + "»", "Course Project in "+ disciplineEng, hourKp, Integer.parseInt(courseProject)));
                       hours -= hourKp;
                    }
                    if(calculationWork != null && calculationWork != "0"){
                        calculationAndGraphicWorks.add(new Mark("Розрахунково-графічна робота з дисципліни «" + disciplineUkr + "»", "Calculation and Graphic Work in "+ disciplineEng, hourRgr, Integer.parseInt(calculationWork)));
                        hours -= hourRgr;
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
                        atestatiion.add(new Mark(disciplineUkr, disciplineEng, hours, mark));
                    }
                    else {
                        marks.add(new Mark(disciplineUkr, disciplineEng, hours, mark));
                    }
                }
            }
        } catch (NumberFormatException ex){
            System.out.println(disciplineId);
            ex.printStackTrace();
        } catch (Exception ex){
            System.out.println("Bad connection");
            ex.printStackTrace();
        }
    }
//    метод toString
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
                ", name_dok_osv_en='" + name_dok_osv_en + '\'' +
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
//    гетери
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
    public String getName_dok_osv_en() {
        return name_dok_osv_en;
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
        return avarage.getCredits();
        //.substring(0, avarage.getCredits().indexOf(','));
    }
    public String getAverage_mark() {
        return average_mark;
    }
    public String getAverage_grade() {
        return avarage.getNational_grade();
    }
    public String getAverage_ECTS() {
        return avarage.getECTS();
    }
    public LinkedList<Mark> getMarks() {
        return marks;
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
    public LinkedList<Mark> getAtestatiion() {
        return atestatiion;
    }
    public String getPersonFoEdboId() {
        return person_Fo_EdboId;
    }
    public String getPerson_Edu_EdboId() {
        return person_Edu_EdboId;
    }
    public String getPrev_diploma_date_slash() {
        return prev_diploma_date_slash;
    }
    public String getPercent_twos() {
        return percent_twos;
    }
    public String getPercent_threes() {
        return percent_threes;
    }
    public String getPercent_fours() {
        return percent_fours;
    }
    public String getPercent_fives() {
        return percent_fives;
    }
    public String getExcellence() {
        return excellence;
    }
    public String getExcellenceEng() {
        return excellenceEng;
    }
    public String getPercent_sum() {
        return percent_sum;
    }

    //    метод сортування масиву з оцінками по назві дисциплін
    private void sort(LinkedList<Mark> marks){
        Collator collator = Collator.getInstance(new Locale("uk","UA"));
        Collections.sort(marks,(d1, d2) ->{
            return collator.compare(d1.getDiscipline_ukr().toLowerCase(),d2.getDiscipline_ukr().toLowerCase());
        });
    }
//    метод визначення середньої оцінки для дисциплін, які читаються кілька семестрів
    private void checkDublicate(LinkedList<Mark> marks){
        int counter = 0;
        int mark = marks.get(0).getMark()*marks.get(0).getHours();
        int disciplineHours = marks.get(0).getHours();
        boolean isDuble=false;
        for (int i = 1; i < marks.size(); i++){
            if (marks.get(i).getDiscipline_ukr().equals(marks.get(i-1).getDiscipline_ukr())){
                isDuble=true;
                counter++;
                mark += marks.get(i).getMark()*marks.get(i).getHours();
                disciplineHours += marks.get(i).getHours();
                marks.remove(i);
                i--;
//                System.out.println("iner 1");
//                System.out.println(marks.get(i).getDiscipline_ukr());
//                System.out.println(disciplineHours);
//                System.out.println("iner out");
            }
            else {
                isDuble=false;
                marks.get(i-1).setMark((int)Math.ceil(mark*1.0/disciplineHours));
                marks.get(i-1).setHours(disciplineHours);
//                System.out.println(marks.get(i-1).getDiscipline_ukr());
//                System.out.println(disciplineHours);
                counter=0;
                mark=0;
                disciplineHours=0;
                mark += marks.get(i).getMark()*marks.get(i).getHours();
                disciplineHours += marks.get(i).getHours();
            }
        }
        // якщо остання дисципліна в списку була кількохсеместровою, то запише цю об'єднану дисципліну в матрицю дисциплін
        if(isDuble) {
            marks.get(marks.size() - 1).setMark((int) Math.ceil(mark * 1.0 / disciplineHours));
            marks.get(marks.size() - 1).setHours(disciplineHours);
        }

//        for (int i = 0; i < marks.size(); i++){
//            System.out.println(marks.get(i).getDiscipline_ukr());
//            System.out.println(marks.get(i).getHours());
//            System.out.println(marks.get(i).getCredits());
//        }
    }
//    метод отримання середньої оцінки
    private void getAvarageMark(){
        sumMark = 0;
        sumHours = 0;
        sumMarks(marks);
        sumMarks(courseWorks);
        sumMarks(courseProjects);
        sumMarks(calculationAndGraphicWorks);
        sumMarks(practisies);
        sumMarks(atestatiion);
        double avarrageMark = (sumMark*1.0/countMarks);
        avarage = new Mark("","",sumHours, (int)Math.floor(avarrageMark));
        average_mark = String.format("%.1f", avarrageMark);
        getPrrcentMarks();
    }
//   метод визначення відсотку п'ятірок, чітвірок, трійок та двійок
    private void getPrrcentMarks(){
        double percentTwos = Math.round(two*10000.0/countMarks)/100.0;
        double percentThrees = Math.round(three*10000.0/countMarks)/100.0;
        double percentFours = Math.round(four*10000.0/countMarks)/100.0;
        double percentFives = Math.round(five*10000.0/countMarks)/100.0;
        double percentSum = percentFives + percentFours + percentThrees + percentTwos;
        percent_fives = String.format("%.2f", percentFives) + " %";
        percent_fours = String.format("%.2f", percentFours) + " %";
        percent_threes = String.format("%.2f", percentThrees) + " %";
        percent_twos = String.format("%.2f", percentTwos) + " %";
        percent_sum = String.format("%.2f", percentSum) + " %";
        if(two == 0 && three == 0 && percentFours<=25.0){
            excellence= "З відзнакою";
            excellenceEng = "With honours";
        }
        else {
            excellence= "Особливі досягнення та відзнаки відсутні";
            excellenceEng = "Without special academic excellence and honours";
        }
    }

//    метод підраховування суми оцінок в масиві
    private void sumMarks(LinkedList<Mark> marks){
        for (Mark mark: marks){
            sumMark += mark.getMark();
            sumHours += mark.getHours();
            countMarks++;
            if (mark.getMark() > 89){
                five++;
            }
            else if(mark.getMark() > 73){
                four++;
            }
            else if(mark.getMark() > 59){
                three++;
            }
            else{
               two++;
            }
        }
    }
//    метод формування строки дати в необхідному форматі
    private static String dateFormatString(String string){
        if(string != null && string.length()>=10) {
            return string.substring(8, 10) + "." + string.substring(5, 7) + "." + string.substring(0, 4);
        }
        else return string;
    }
//    метод визначення англіської назви типу документу про попередню освіту
    private static String getNameDocOsvEn(String nameEducationDocument){
        if (nameEducationDocument != null) {
            for (int i = 0; i < nameDokOsv.length; i++) {
                if (nameDokOsv[i].equals(nameEducationDocument.trim().toLowerCase())) {
                    return nameDokOsvEn[i];
                }
            }
        }
        return "";
    }
}
