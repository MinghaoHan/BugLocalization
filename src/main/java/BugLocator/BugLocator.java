package BugLocator;

import Lucene_index.Pojo.SourceFile;
import Models.VectorSpaceModel;
import lib.BugReporter;
import lib.Sources;
import query.query;
import query.wordMap;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BugLocator {

    private static final String DRIVER_CLASS = "oracle.jdbc.driver.OracleDriver";
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/sourceFile";
    private static final String DATABASE_USER = "root";
    private static final String DATABASE_PASSWORD = "Hmh08715";
    private static Connection con = null;

    private BugReporter bugs;
    private Sources ss;

    public BugLocator(BugReporter bugs, Sources ss) {
        this.bugs = bugs;
        this.ss = ss;
    }

    public static Connection getConnection() {
        try {
            Class.forName(DRIVER_CLASS);
            con= DriverManager.getConnection(DATABASE_URL,DATABASE_USER,DATABASE_PASSWORD);
            return con;
        } catch (Exception ex) {
            System.out.println("2:"+ex.getMessage());
        }
        return con;
    }

    public void bugLocator(){
        File file = new File(bugs.getPath());
        File[] fs = file.listFiles();
        List<String> files = new ArrayList<String>();
        List<String> names = new ArrayList<String>();
        for(File f:fs){
            if(!f.isDirectory()) {
                files.add(f.getAbsolutePath());
                names.add(f.getName());
            }
        }

        query queries = new query(files);
        ArrayList<wordMap> corpus = queries.getCorpus();
        int FNum = queries.getDFileNums();

        for(int i=0;i<FNum;i++) {
            String name = names.get(i);
            System.out.println("-------------------------------------------------");
            System.out.println("File Name:"+name);
            String Path = corpus.get(i).getTextName();
            String Word = "";

            Map<String,Double> res = new HashMap<>();
            Map<String,String> words = new HashMap<>();
            HashMap<String, Integer> wordCount = corpus.get(i).getWordCountTable();
            for(String tmpWord : wordCount.keySet()) {
                System.out.println("W:"+tmpWord);
                System.out.println("TF:"+queries.countTF(Path,tmpWord));
                System.out.println("IDF:"+queries.countIDF(tmpWord));

            }

        }
    }

    /***
     * 打印test
     * @throws SQLException
     */
    public static void sysoutStrTablePdmCloumns(String Table,String Owner) throws SQLException{
        getConnection();

        List<HashMap<String,String>> columns = new ArrayList<HashMap<String,String>>();

        try{
            Statement stmt = con.createStatement();

            String sql= "";
            System.out.println(sql);
            ResultSet rs = ((Statement) stmt).executeQuery(sql);
            while (rs.next()){
                HashMap<String,String> map = new HashMap<String,String>();
                map.put("Name", rs.getString("Name"));
                map.put("Code", rs.getString("Code"));
                map.put("DataType", rs.getString("DataType"));
                map.put("Comment", rs.getString("Comment"));
                map.put("Primary", rs.getString("Primary"));
                map.put("Mandatory", rs.getString("Mandatory"));
                columns.add(map);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }finally{
            con.close();
        }
    }

    public Map<String,Double> bugSort(){
        Map<String,Double> SourceSord=new HashMap<>();

    }
}
