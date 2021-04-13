package Lucene_index.IndexCreate;


import lib.Sources;
import query.query;
import query.wordMap;

import java.io.File;
import java.sql.*;
import java.text.DecimalFormat;
import java.util.*;

public class CreateIndexDaoImpl implements CreateIndexDao{
    private Sources ss;
    public CreateIndexDaoImpl(Sources ss) {
        this.ss = ss;
    }

    public void createSourceIndex() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");

        //一开始必须填一个已经存在的数据库
        String url = "jdbc:mysql://106.15.88.231:3306/sourceFile";
        Connection conn = DriverManager.getConnection(url, "root", "hmh123456"); //修改成自己的账号密码
        Statement stat = conn.createStatement();

        //创建SourceFile表


        //获取最后一行的id
//        ResultSet tmpRS = stat.executeQuery("SELECT LAST_INSERT_ID()");

        stat.executeUpdate("delete from sourceFile;");
        int lastID=0;

        //添加数据
        File file = new File(ss.getPath());
        boolean flag= file.exists();
        File[] fs = file.listFiles();
        List<String> files = new ArrayList<String>();
        List<String> names = new ArrayList<String>();
        for(File f:fs){
            if(!f.isDirectory()) {        //若非目录(即文件)
                files.add(f.getAbsolutePath());
                names.add(f.getName());
            }
        }

        query queries = new query(files);
        ArrayList<wordMap> corpus = queries.getCorpus();
        int FNum = queries.getDFileNums();

        for(int i=0;i<FNum;i++) {
            int f_id = ++lastID;
            String name = names.get(i);
            String Path = corpus.get(i).getTextName();
            String Word = "";

            Map<String,Double> words = new HashMap<>();
            HashMap<String, Integer> wordCount = corpus.get(i).getWordCountTable();
            DecimalFormat df = new DecimalFormat("#00.0000");
            for(String tmpWord : wordCount.keySet()) {
                words.put(tmpWord,queries.countTF(Path,tmpWord)*queries.countIDF(tmpWord));
            }

            //选择前 30 个词，写入数据库
            List<Map.Entry<String,Double>> mappingList = null;
            mappingList = new ArrayList<Map.Entry<String,Double>>(words.entrySet());
            //通过比较器实现比较排序
            Collections.sort(mappingList, new Comparator<Map.Entry<String,Double>>(){
                public int compare(Map.Entry<String,Double> mapping1,Map.Entry<String,Double> mapping2){
                    if (mapping1.getValue()< mapping2.getValue()) return 1;
                    else return -1; }
            });

            for(int j = 0;j < (mappingList.size()) && j<30;j++) {
                Word = Word + mappingList.get(j).getKey() + " " + String.valueOf(df.format(mappingList.get(j).getValue())) + " ";
            }

            String sql_insert = "insert into sourceFile values(" + f_id + ", '" + name + "','" + Path + "','" + Word + "')";
            stat.executeUpdate(sql_insert);
            System.out.println(Path + " : Inserted Successfully!");
        }

        //关闭数据库
        stat.close();
        conn.close();
    }
}
