package Evaluation;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class Evaluation {
    private static Logger logger = Logger.getLogger(Evaluation.class.getName());

    private static final String DRIVER_CLASS = "com.mysql.jdbc.Driver";
    private static final String DATABASE_URL = "jdbc:mysql://106.15.88.231:3306/sourceFile";
    private static final String DATABASE_USER = "root";
    private static final String DATABASE_PASSWORD = "hmh123456";
    private static Connection con = null;
    private int K;

    public Evaluation(int topK){
        this.K = topK;
        getConnection();
    }

    public void getConnection() {
        try {
            Class.forName(DRIVER_CLASS);
            con= DriverManager.getConnection(DATABASE_URL,DATABASE_USER,DATABASE_PASSWORD);
            this.con = con;
        } catch (Exception ex) {
            System.out.println("3:"+ex.getMessage());
        }
        this.con = con;
    }

    public  String[] getFixed(String bug_id) throws IOException {
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

    public Boolean TopK(String bug_id) throws IOException {
        Boolean res = false;
        String[] fixFile = getFixed(bug_id);
        int theK=this.K;

        if(fixFile==null) return res;

        try{
            Statement stmt = con.createStatement();

            String tmp_bug = "report"+bug_id+".txt";
            String sql= "select sourcefile from vsm where bug = '" +tmp_bug +"' Order by sim DESC;";
            ResultSet rs = ((Statement) stmt).executeQuery(sql);
            while(rs.next() && theK>0){
                String tempSF = rs.getString("sourcefile");
                for(String tmp:fixFile){
                    if(tempSF.equals(tmp))
                        return true;
                }
                theK--;
            }

            return false;
        }
        catch (SQLException e){
            e.printStackTrace();
        }finally{
            logger.info("report"+bug_id+" : Top@"+this.K);
        }

        return false;
    }

    public static void main(String[] args) throws IOException {
        File file = new File("data/report_preprocessed2/");
        File[] bugFiles = file.listFiles();
        List<String> names = new ArrayList<String>();
        for(File f:bugFiles){
            if(!f.isDirectory()) {
                String fname=f.getName();
                int indexNum=0;
                while(fname.charAt(indexNum)<'0'||fname.charAt(indexNum)>'9') indexNum++;
                names.add(fname.substring(indexNum,fname.indexOf(".")));
            }
        }

        int topK = 10;
        Evaluation e = new Evaluation(topK);

        //Todo:计算TopK
        int num=0;
        int TopKKK=0;
        for(String bug_id:names) {
            if(e.TopK(bug_id))
                TopKKK++;
            num++;
        }
        System.out.println("Top@"+topK+":"+Double.valueOf(TopKKK)/num);
    }

}
