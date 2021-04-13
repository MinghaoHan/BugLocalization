package BugLocator;

import Models.VectorSpaceModel;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import lib.BugReporter;
import lib.Sources;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.*;
import java.util.logging.Logger;

public class BugLocator {

    private static Logger logger = Logger.getLogger(BugLocator.class.getName());

    private static final String DRIVER_CLASS = "com.mysql.jdbc.Driver";
    private static final String DATABASE_URL = "jdbc:mysql://106.15.88.231:3306/sourceFile";
    private static final String DATABASE_USER = "root";
    private static final String DATABASE_PASSWORD = "hmh123456";
    private static Connection con = null;
    private List<HashMap<String,HashMap<String,Double>>> columns;

    private BugReporter bugs;
    private Sources ss;
    private String RankList;

    public BugLocator(BugReporter bugs, Sources ss, String ListPath) throws SQLException {
        this.bugs = bugs;
        this.ss = ss;
        this.RankList = ListPath;
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

    public void bugLocator() throws IOException, WriteException {
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
                String name_temp = res.get(j).getKey();
                String v_temp = String.valueOf(res.get(j).getValue());

                writeIn(name,name_temp,v_temp);
                System.out.println(String.format("%-40s",name_temp)+"  "+v_temp);
            }

            System.out.println("-------------------------------------------------");
        }
    }

    public void writeIn(String bugR, String SF, String v) throws IOException, WriteException {
        String [] title = {"BugReport","ClassFile","Similarity"};
        File f = new File(this.RankList);

        //不存在，创建
        try {
            if (!f.exists()) {
                f.createNewFile();
                //创建工作簿
                WritableWorkbook workbookA = Workbook.createWorkbook(f);
                //创建sheet
                WritableSheet sheetA = workbookA.createSheet("RankList", 0);
                Label labelA = null;
                //设置列名
                for (int i = 0; i < title.length; i++) {
                    labelA = new Label(i, 0, title[i]);
                    sheetA.addCell(labelA);
                }
                workbookA.write();
                workbookA.close();
            }

            Workbook wb = Workbook.getWorkbook(f);
            WritableWorkbook workbook = Workbook.createWorkbook(f,wb);
            WritableSheet sheet=workbook.getSheet(0);  //获取到工作表，因为一个excel可能有多个工作表
            int length = sheet.getRows();

            Label lable = new Label(0, length, bugR);
            sheet.addCell(lable);
            lable = new Label(1,length,SF);
            sheet.addCell(lable);
            lable = new Label(2,length,v);
            sheet.addCell(lable);

            workbook.write();
            wb.close();
            workbook.close();
            logger.info("Success. 写入一条记录。");
        }catch (Exception e){
            logger.info("Error: 文件写入失败!");
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
