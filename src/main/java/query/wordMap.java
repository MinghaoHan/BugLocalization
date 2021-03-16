package query;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

// 每个文本文件对应一个wordMap

public class wordMap {
    private String textName;    // 文本名
    private HashMap<String, Integer> wordCountTable;    // 词频统计表
    private int allWordsNum;    // 该文本内含的单词总数

    wordMap(String fileName) {
        textName = fileName;
        wordCountTable = wordCounter.getWordCountTable(textName);

        Set<Map.Entry<String, Integer>> entrySet = wordCountTable.entrySet();
        for (Map.Entry<String, Integer> entry : entrySet) {
            allWordsNum += entry.getValue();
        }
    }

    public String getTextName() { return textName; }

    public int getAllWordsNum() {
        return allWordsNum;
    }

    public boolean hasWord(String word) {
        return wordCountTable.containsKey(word);
    }

    public int getWordRepeatTime(String word) {
        if (hasWord(word))
            return wordCountTable.get(word);
        else
            return 0;
    }

}
