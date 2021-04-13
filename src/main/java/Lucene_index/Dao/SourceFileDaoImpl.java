package Lucene_index.Dao;

import Lucene_index.Pojo.SourceFile;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SourceFileDaoImpl implements SourceFileDao{
    /**
     * 从数据库中读取所有源文件信息
     * @return 源文件list: String id, name, path, Map<String,String>word
     * @throws Exception
     */
    public List<SourceFile> querySFList() throws Exception{
        // 数据库链接
        Connection connection = null;
        // 预编译statement
        PreparedStatement preparedStatement = null; // 结果集
        ResultSet resultSet = null;
        // 源文件列表
        List<SourceFile> list = new ArrayList<SourceFile>();
        try {
        // 加载数据库驱动
            Class.forName("com.mysql.jdbc.Driver"); // 连接数据库
            connection = DriverManager.getConnection("jdbc:mysql://106.15.88.231:3306/sourceFile", "root", "hmh123456");
            String sql = "SELECT * FROM SourceFile";
            preparedStatement = connection.prepareStatement(sql); // 获取结果集
            resultSet = preparedStatement.executeQuery();
        // 结果集解析
            while (resultSet.next()) {
                SourceFile SF = new SourceFile();
                SF.setId(resultSet.getString("id"));
                SF.setName(resultSet.getString("name"));
                SF.setPath(resultSet.getString("path"));

                Map<String,String> Word = new HashMap<String,String>();
                {
                    ResultSetMetaData rsmd = resultSet.getMetaData();
                    int numberOfColumns = rsmd.getColumnCount();
                    String sKey = "";
                    String sValue = "";
                    if (Word != null) {
                        for (int i = 1; i <= numberOfColumns; i++) {
                            sKey = (rsmd.getColumnName(i)).trim();
                            String tString = resultSet.getString(i);
                            if (tString == null) sValue = "0";
                            else {
                                if (tString.trim()==null) sValue="0";
                                else   sValue = tString.trim();
                            }
                            Word.put(sKey, sValue);
                        }
                    } else {
                        throw new Exception("dataSource object is null.");
                    }
                }
                SF.setWord(Word);
                list.add(SF);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
