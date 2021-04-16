package Evaluation;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
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

    public  String[] getFixed(String bug_id) throws IOException {
        //Todo
        FileInputStream in = new FileInputStream("data/SWTBugRepository.xml");
        byte[] tempbytes = new byte[in.available()];
        int lenOffile = in.read(tempbytes);
        String comments = new String(tempbytes);

        int tem = comments.indexOf("<fixedFiles>",comments.indexOf(bug_id));
        String str = comments.substring(tem+12,comments.indexOf("</fixedFiles>",comments.indexOf(bug_id)));
        str = str.replace(" ","");
        str = str.replace("\n","");
        str = str.replace("<file>","");
        String[] strL = str.split("</file>");
        String[] result = new String[strL.length];
        int i = 0;
        for(String s :strL) {
            String[] strlist = s.split("\\.");
            String re = strlist[strlist.length-2]+"."+strlist[strlist.length-1];
            result[i++] = re;
        }

        return  result;
    }


}
