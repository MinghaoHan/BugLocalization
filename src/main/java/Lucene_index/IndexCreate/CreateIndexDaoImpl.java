package Lucene_index.IndexCreate;


import lib.Sources;
import query.query;
import query.wordMap;

import java.io.File;
import java.sql.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreateIndexDaoImpl implements CreateIndexDao{
    private Sources ss;
    public CreateIndexDaoImpl(Sources ss) {
        this.ss = ss;
    }

    public void createSourceIndex() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");

        //一开始必须填一个已经存在的数据库
        String url = "jdbc:mysql://localhost:3306/sourceFile";
        Connection conn = DriverManager.getConnection(url, "root", "Hmh08715"); //修改成自己的账号密码
        Statement stat = conn.createStatement();

        //创建SourceFile表


        //获取最后一行的id
//        ResultSet tmpRS = stat.executeQuery("SELECT LAST_INSERT_ID()");
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

            Map<String,String> words = new HashMap<>();
            HashMap<String, Integer> wordCount = corpus.get(i).getWordCountTable();
            DecimalFormat df = new DecimalFormat();
            for(String tmpWord : wordCount.keySet()) {
                Word = Word + tmpWord +" ";
                Word = Word + df.format(queries.countTF(Path,tmpWord)*queries.countIDF(tmpWord)) + " ";
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
