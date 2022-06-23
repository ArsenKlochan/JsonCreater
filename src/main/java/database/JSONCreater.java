package database;

import database.objects.Mark;
import database.objects.StudentData;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class JSONCreater {
    public static void writeJsonObject(String filename, List<StudentData> list){
        JSONObject jsonObject = new JSONObject();
        JSONArray users = new JSONArray();
        for (StudentData student: list) {
            JSONObject oneStudent = new JSONObject();
            oneStudent.put("date_birth", student.getDate_birth());
            oneStudent.put("series_diploma", student.getSeries_diploma());
            oneStudent.put("number_diploma", student.getNumber_diploma());
            oneStudent.put("diploma_type", student.getDiploma_type());
            oneStudent.put("date_diploma_receiving", student.getDate_diploma_receiving());
            oneStudent.put("number_supplement_diploma", student.getNumber_supplement_diploma());
            oneStudent.put("date_receiving_supplement", student.getDate_receiving_supplement());
//            oneStudent.put("person_Fo_EdboId", student.getPersonFoEdboId());
            oneStudent.put("prev_diploma_number", student.getPrev_diploma_number());
            oneStudent.put("prev_diploma_date", student.getPrev_diploma_date());
            oneStudent.put("name_dok_osv", student.getName_dok_osv());
            oneStudent.put("name_dok_osv_en", student.getName_dok_osv_en());
            oneStudent.put("prev_navch_zakl", student.getPrev_navch_zakl());
            oneStudent.put("prev_navch_zakl_en", student.getPrev_navch_zakl_en());
            oneStudent.put("name_ukr", student.getName_ukr());
            oneStudent.put("name_eng", student.getName_eng());
            oneStudent.put("surname_ukr", student.getSurname_ukr());
            oneStudent.put("surname_eng", student.getSurname_eng());
            oneStudent.put("middlename_ukr", student.getMiddlename_ukr());
            oneStudent.put("middlename_eng", student.getMiddlename_eng());
            oneStudent.put("total_credits", student.getTotal_credits());
            oneStudent.put("average_mark", student.getAverage_mark());
            oneStudent.put("average_grade", student.getAverage_grade());
            oneStudent.put("average_ECTS", student.getAverage_ECTS());
            oneStudent.put("diploma_theme", student.getDiploma_theme());
            oneStudent.put("diploma_theme_E", student.getDiploma_theme_E());
            JSONArray marks = new JSONArray();
            JSONArray course_works = new JSONArray();
            JSONArray practisies = new JSONArray();
            for (Mark mark: student.getMarks()){
                marks.add(getMark(mark));
            }
            for (Mark mark: student.getCourseProjects()){
                course_works.add(getMark(mark));
            }
            for (Mark mark: student.getCourseWorks()){
                course_works.add(getMark(mark));
            }
            for (Mark mark: student.getCalculationAndGraphicWorks()){
                course_works.add(getMark(mark));
            }
            for (Mark mark: student.getPractisies()){
                practisies.add(getMark(mark));
            }
            oneStudent.put("marks", marks);
            oneStudent.put("course_works", course_works);
            oneStudent.put("practisies", practisies);
            oneStudent.put("atestation", getMark(student.getAtestatiion()));
            users.add(oneStudent);
        }
        jsonObject.put("users", users);
        try {
            Files.write(Paths.get(filename),jsonObject.toJSONString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static JSONObject getMark (Mark mark){
        JSONObject oneMark = new JSONObject();
        oneMark.put("discipline_ukr", mark.getDiscipline_ukr());
        oneMark.put("discipline_eng", mark.getDiscipline_eng());
        oneMark.put("credits", mark.getCredits());
        oneMark.put("mark", String.valueOf(mark.getMark()));
        oneMark.put("national_grade", mark.getNational_grade());
        oneMark.put("ECTC", mark.getECTS());
        return oneMark;
    }
}
