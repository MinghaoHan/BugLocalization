package Evaluation;

import java.sql.Connection;
import java.sql.DriverManager;

public class Evaluation {
    private static final String DRIVER_CLASS = "com.mysql.jdbc.Driver";
    private static final String DATABASE_URL = "jdbc:mysql://106.15.88.231:3306/sourceFile";
    private static final String DATABASE_USER = "root";
    private static final String DATABASE_PASSWORD = "hmh123456";
    private static Connection con = null;

    public Evaluation(){
        getConnection();
    }

    public void getConnection() {
        try {
            Class.forName(DRIVER_CLASS);
            con= DriverManager.getConnection(DATABASE_URL,DATABASE_USER,DATABASE_PASSWORD);
            this.con = con;
        } catch (Exception ex) {
            System.out.println("2:"+ex.getMessage());
        }
        this.con = con;
    }

    public String getFixed(String bug_id){
        //Todo
    }

    public String TopK(){

    }
}
