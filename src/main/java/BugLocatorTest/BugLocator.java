package BugLocatorTest;

import Models.VectorSpaceModel;
import lib.BugReporter;
import lib.Sources;

import java.io.File;
import java.sql.*;
import java.util.*;

public class BugLocator {

    private static final String DRIVER_CLASS = "com.mysql.jdbc.Driver";
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/sourceFile";
    private static final String DATABASE_USER = "root";
    private static final String DATABASE_PASSWORD = "Hmh08715";
    private static Connection con = null;
    private List<HashMap<String,HashMap<String,Double>>> columns;

    private BugReporter bugs;
    private Sources ss;

    public BugLocator(BugReporter bugs, Sources ss) throws SQLException {
        this.bugs = bugs;
        this.ss = ss;
        sysoutStrTablePdmCloumns();
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

    public void bugLocator(){
        File file = new File(bugs.getPath());
        File[] bugFiles = file.listFiles();
        List<String> files = new ArrayList<String>();
        List<String> names = new ArrayList<String>();
        for(File f:bugFiles){
            if(!f.isDirectory()) {
                files.add(f.getAbsolutePath());
                names.add(f.getName());
            }
        }

        //遍历每一个bug文件
        for(int i=0;i<files.size();i++) {
            String name = names.get(i);
            String path = files.get(i);

            System.out.println("-------------------------------------------------");
            System.out.println("BugFile Name:"+name);


            //VSM，排序，打印
            System.out.println("-------------------------------------------------");
            List<Map.Entry<String, Double>> res = bugSort(path);
            for(int j=0;j<res.size();j++){
                System.out.println(String.format("%-40s",res.get(j).getKey())+"  "+res.get(j).getValue());
            }

            System.out.println("-------------------------------------------------");
        }
    }

    /**
     * 调用模型，计算相似度，排序
     * @param bugPath
     * @return SourceSort(sortedSourceFile)
     */
    public List<Map.Entry<String, Double>> bugSort(String bugPath){
        Map<String,Double> SourceSort = new HashMap<>();

        //计算相似度
        for(int i=0;i<this.columns.size();i++){
            HashMap<String, HashMap<String, Double>> tmpSF = this.columns.get(i);

            HashMap<String,Double> wordSF = new HashMap<>();
            String filename="";
            for(Map.Entry<String,HashMap<String,Double>> entry : tmpSF.entrySet() ){
                wordSF = entry.getValue();
                filename = entry.getKey();
            }

            //Todo: 在这里加入相似度模型，VSM等
            SourceSort.put(filename, new VectorSpaceModel().calculate(ss.getPath(),filename,wordSF,bugPath));
        }

        //根据value，降序排列
        List<Map.Entry<String,Double>> mappingList = null;
        mappingList = new ArrayList<Map.Entry<String,Double>>(SourceSort.entrySet());
        //通过比较器实现比较排序
        Collections.sort(mappingList, new Comparator<Map.Entry<String,Double>>(){
            public int compare(Map.Entry<String,Double> mapping1,Map.Entry<String,Double> mapping2){
                if (mapping1.getValue()< mapping2.getValue()) return 1;
                else return -1; }
        });

        return mappingList;
    }


    /***
     * 查询数据库
     * @throws SQLException
     */
    public void sysoutStrTablePdmCloumns() throws SQLException{
        getConnection();

        List<HashMap<String,HashMap<String,Double>>> columns = new ArrayList<HashMap<String,HashMap<String,Double>>>();

        try{
            Statement stmt = con.createStatement();

            String sql= "select name,word from sourceFile";
            ResultSet rs = ((Statement) stmt).executeQuery(sql);
            while (rs.next()){
                HashMap<String,HashMap<String,Double>> map = new HashMap<String,HashMap<String, Double>>();
                HashMap<String,Double> tmp = new HashMap<>();
                String[] words = rs.getString("word").split(" ");
                for(int item=0;item < words.length;item+=2){
                    tmp.put(words[item],Double.valueOf(words[item+1]));
                }
                map.put(rs.getString("name"),tmp);
                columns.add(map);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }finally{
            this.con.close();
        }

        this.columns=columns;
    }
}
