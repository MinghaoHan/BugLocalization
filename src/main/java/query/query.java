package query;

import java.util.ArrayList;
import java.util.List;

public class query {

    private ArrayList<wordMap> corpus=new ArrayList<wordMap>();    // 语料库
    private int DFileNums;    // 语料库里的文件总数

    public ArrayList<wordMap> getCorpus() {
        return corpus;
    }

    public int getDFileNums() {
        return DFileNums;
    }

    /**
     * 初始化语料库
     * @param textFiles
     */
    public query(List<String> textFiles) {
        DFileNums = 0;
        for (String fileName : textFiles) {
            DFileNums += 1;
            corpus.add(new wordMap(fileName));
        }
    }

    public double countTF(String fileName, String word) {
        // 遍历语料库，查询文件
        for (wordMap w : corpus) {
            if (fileName.equals(w.getTextName())) {
                // 计算TF
                return (double)w.getWordRepeatTime(word) / w.getAllWordsNum();
            }
        }
        return -1;
    }

    public double countIDF(String word) {
        int hasWordFileNum = 1;
        for (wordMap w : corpus) {
            if (w.hasWord(word))
                hasWordFileNum += 1;
        }
        return Math.log((double)DFileNums / hasWordFileNum);
    }

}
