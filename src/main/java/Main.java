import database.BaseConnector;
import database.JSONCreater;
import database.objects.StudentData;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.nio.charset.CharsetDecoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
//        getDate();
        LinkedList<StudentData> studentDataLinkedList = new LinkedList<>();
        studentDataLinkedList.add(new StudentData("59647"));
//        studentDataLinkedList.add(new StudentData("62154"));
//        studentDataLinkedList.add(new StudentData("64377"));
//        studentDataLinkedList.add(new StudentData("62155"));
//
//        studentDataLinkedList.add(new StudentData("62156"));
//        studentDataLinkedList.add(new StudentData("62157"));
//        studentDataLinkedList.add(new StudentData("62158"));
//        studentDataLinkedList.add(new StudentData("62159"));
//        studentDataLinkedList.add(new StudentData("64115"));
//        studentDataLinkedList.add(new StudentData("64124"));
//        studentDataLinkedList.add(new StudentData("64127"));
//        studentDataLinkedList.add(new StudentData("64132"));
//        studentDataLinkedList.add(new StudentData("64136"));
//        studentDataLinkedList.add(new StudentData("64140"));
//        studentDataLinkedList.add(new StudentData("64147"));
//        studentDataLinkedList.add(new StudentData("64162"));
//        studentDataLinkedList.add(new StudentData("64204"));
//        studentDataLinkedList.add(new StudentData("65107"));

        JSONCreater.writeJsonObject("student252.json",studentDataLinkedList);
//        System.out.println(studentData.toString());
//        Parent root = FXMLLoader.load(getClass().getResource("/javafx/main.fxml"));
//        primaryStage.setTitle("JSON Creator");
//        primaryStage.setScene(new Scene(root));
//        primaryStage.show();
        System.out.println("Done");
    }

    public static void getDate(){
        BaseConnector connector = new BaseConnector();
        try (Connection connection = connector.getConnection()) {
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT DFullName, D_ID FROM duscuplinu");
            ArrayList<String> tempPract = new ArrayList<String>();
            ArrayList<String> tempAtest = new ArrayList<String>();
            int temp = 0;
//            практика
            while (resultSet.next()){
                if (resultSet.getString(1).endsWith("практика")) {
                    tempPract.add(resultSet.getString(2));
                    System.out.println(resultSet.getString(2)+  " " + resultSet.getString(1));
                    temp++;
                }
                else  if(resultSet.getString(1).toLowerCase().indexOf("виробнича практика") != -1){
                    tempPract.add(resultSet.getString(2));
                    System.out.println(temp + " " + resultSet.getString(2)+  " " + resultSet.getString(1));
                    temp++;
                }
                else  if(resultSet.getString(1).toLowerCase().indexOf("навчальна практика") != -1){
                    tempPract.add(resultSet.getString(2));
                    System.out.println(temp + " " + resultSet.getString(2)+  " " + resultSet.getString(1));
                    temp++;
                }
                else  if(resultSet.getString(1).toLowerCase().indexOf("наукова практика") != -1){
                    tempPract.add(resultSet.getString(2));
                    System.out.println(temp + " " + resultSet.getString(2)+  " " + resultSet.getString(1));
                    temp++;
                }
                else  if(resultSet.getString(1).toLowerCase().indexOf("учбова практика") != -1){
                    tempPract.add(resultSet.getString(2));
                    System.out.println(temp + " " + resultSet.getString(2)+  " " + resultSet.getString(1));
                    temp++;
                }
                else if(resultSet.getString(1).indexOf("практика") != -1){
//                    System.out.println(resultSet.getString(2)+ " " + resultSet.getString(1));
                }

//            атестація
//            if(resultSet.getString(1).toLowerCase().indexOf("атестація") != -1) {
//                temp++;
//                tempAtest.add(resultSet.getString(2));
//                System.out.println(temp + " " + resultSet.getString(2)+  " " + resultSet.getString(1));
//            }
//            else  if(resultSet.getString(1).toLowerCase().indexOf("іспит") != -1){
//                temp++;
//                tempAtest.add(resultSet.getString(2));
//                System.out.println(temp + " " + resultSet.getString(2)+  " " + resultSet.getString(1));
//            }
//            else  if(resultSet.getString(1).toLowerCase().indexOf("екзамен") != -1){
//                temp++;
//                tempAtest.add(resultSet.getString(2));
//                System.out.println(temp + " " + resultSet.getString(2)+  " " + resultSet.getString(1));
//            }
//            else  if(resultSet.getString(1).toLowerCase().indexOf("виконання") != -1 && resultSet.getString(1).toLowerCase().indexOf("роботи") != -1 ){
//                temp++;
//                tempAtest.add(resultSet.getString(2));
//                System.out.println(temp + " " + resultSet.getString(2)+  " " + resultSet.getString(1));
//            }
            }
            System.out.println(tempPract);
            System.out.println();
            System.out.println(tempAtest);
            statement.close();

        }
        catch (Exception ex){
            ex.printStackTrace();
            System.out.println("Bad connection");
        }
    }
    public static void main(String[] args) {
        Application.launch(args);
    }
}
