package aqueryTest.test;

import org.junit.Test;
import com.nobug.backend.query.query;

import java.io.File;
import java.text.DecimalFormat;
import java.util.*;

import com.nobug.backend.query.wordMap;

public class AQueryTest {
    @Test
    public void querytest(){
        File file = new File("data/class_preprocessed/");
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
            System.out.println("Name:"+name);
            String Path = corpus.get(i).getTextName();
            String Word = "";

            Map<String,Double> words = new HashMap<>();
            HashMap<String, Integer> wordCount = corpus.get(i).getWordCountTable();

            DecimalFormat df = new DecimalFormat("#00.0000");
            for(String tmpWord : wordCount.keySet()) {
                words.put(tmpWord,queries.countTF(Path,tmpWord)*queries.countIDF(tmpWord));
            }

            List<Map.Entry<String,Double>> mappingList = null;
            mappingList = new ArrayList<Map.Entry<String,Double>>(words.entrySet());
            //通过比较器实现比较排序
            Collections.sort(mappingList, new Comparator<Map.Entry<String,Double>>(){
                public int compare(Map.Entry<String,Double> mapping1,Map.Entry<String,Double> mapping2){
                if (mapping1.getValue()< mapping2.getValue()) return 1;
                else return -1; }
            });

            for(int j = 0;j < (mappingList.size()) && j<10;j++){
                System.out.println(mappingList.get(j).getKey()+":"+df.format(mappingList.get(j).getValue()));
            }

        }
    }
}
