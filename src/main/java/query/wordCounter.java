package query;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

// 负责进行词频统计的工具类
public class wordCounter {

    public static HashMap<String, Integer> getWordCountTable(String fileName) {
        HashMap<String, Integer> WordCountTable = new HashMap<>();;
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
            String line = "";

            while ((line = bufferedReader.readLine()) != null) {
                String[] words = line.split(" ");

                for (String word : words) {

                    if (WordCountTable.containsKey(word)) {
                        Integer value = WordCountTable.get(word);
                        WordCountTable.put(word, value + 1);
                    }
                    else
                        WordCountTable.put(word, 1);
                }
            }

            bufferedReader.close();//关闭字节流

            //打印结果
            //Set<Map.Entry<String, Integer>> entrySet = wordMap.entrySet();
            //for (Map.Entry<String, Integer> entry : entrySet) {
            //    System.out.println(entry.getKey() + ":" + entry.getValue());
            //}
        }
        catch (IOException e) {
            System.out.println("File Not Found.");
        }
        return WordCountTable;
    }

}
