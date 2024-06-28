package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BaseConnector {
    public Connection getConnection(){
        String login = "admin";
        String password = "enE@^3e86pH3V-";
        String url = "jdbc:mysql://212.111.203.178/dekanat";
        String dataBaseName = "dekanat";
        String port = "3306";


        try{
            return DriverManager.getConnection(url, login, password);
        }
        catch(SQLException e){
            System.out.println("Bad connection");
            e.printStackTrace();
            return null;
        }
    }


}
