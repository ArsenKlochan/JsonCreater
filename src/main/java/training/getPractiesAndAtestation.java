package training;

import database.BaseConnector;
import javafx.application.Application;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class getPractiesAndAtestation extends Application{

        @Override
        public void start(Stage primaryStage) throws Exception{
            getDate();
        }

        public static void getDate(){
            BaseConnector connector = new BaseConnector();
            try (Connection connection = connector.getConnection()) {
                Statement statement = connection.createStatement();

                ResultSet resultSet = statement.executeQuery("SELECT DFullName, D_ID FROM duscuplinu");
                ArrayList<String> tempPract = new ArrayList<String>();
                ArrayList<String> tempAtest = new ArrayList<String>();
                int tempPrac = 1;
                int tempAtes = 1;
//            практика
                while (resultSet.next()){
                    if (resultSet.getString(1).endsWith("практика")) {
                        tempPract.add(resultSet.getString(2));
                        System.out.println(resultSet.getString(2)+  " " + resultSet.getString(1));
                        tempPrac++;
                    }
                    else  if(resultSet.getString(1).toLowerCase().indexOf("виробнича практика") != -1){
                        tempPract.add(resultSet.getString(2));
                        System.out.println(tempPrac + " " + resultSet.getString(2)+  " " + resultSet.getString(1));
                        tempPrac++;
                    }
                    else  if(resultSet.getString(1).toLowerCase().indexOf("навчальна практика") != -1){
                        tempPract.add(resultSet.getString(2));
                        System.out.println(tempPrac + " " + resultSet.getString(2)+  " " + resultSet.getString(1));
                        tempPrac++;
                    }
                    else  if(resultSet.getString(1).toLowerCase().indexOf("наукова практика") != -1){
                        tempPract.add(resultSet.getString(2));
                        System.out.println(tempPrac + " " + resultSet.getString(2)+  " " + resultSet.getString(1));
                        tempPrac++;
                    }
                    else  if(resultSet.getString(1).toLowerCase().indexOf("учбова практика") != -1){
                        tempPract.add(resultSet.getString(2));
                        System.out.println(tempPrac + " " + resultSet.getString(2)+  " " + resultSet.getString(1));
                        tempPrac++;
                    }
                    else if(resultSet.getString(1).indexOf("практика") != -1){
//                    System.out.println(resultSet.getString(2)+ " " + resultSet.getString(1));
                    }

//            атестація
            if(resultSet.getString(1).toLowerCase().indexOf("атестація") != -1) {
                tempAtes++;
                tempAtest.add(resultSet.getString(2));
                System.out.println(tempAtes + " " + resultSet.getString(2)+  " " + resultSet.getString(1));
            }
            else  if(resultSet.getString(1).toLowerCase().indexOf("іспит") != -1){
                tempAtes++;
                tempAtest.add(resultSet.getString(2));
                System.out.println(tempAtes + " " + resultSet.getString(2)+  " " + resultSet.getString(1));
            }
            else  if(resultSet.getString(1).toLowerCase().indexOf("екзамен") != -1){
                tempAtes++;
                tempAtest.add(resultSet.getString(2));
                System.out.println(tempAtes + " " + resultSet.getString(2)+  " " + resultSet.getString(1));
            }
            else  if(resultSet.getString(1).toLowerCase().indexOf("виконання") != -1 && resultSet.getString(1).toLowerCase().indexOf("роботи") != -1 ){
                tempAtes++;
                tempAtest.add(resultSet.getString(2));
                System.out.println(tempAtes + " " + resultSet.getString(2)+  " " + resultSet.getString(1));
            }
                }
                System.out.println("Практики");
                System.out.println(tempPract);
                System.out.println();
                System.out.println("Атестація");
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
