package com.nobug.backend.Models;

import com.nobug.backend.query.query;
import com.nobug.backend.query.wordMap;
import com.nobug.backend.Models.Distance;

import java.io.File;
import java.text.DecimalFormat;
import java.util.*;

public class VectorSpaceModel {

    /**
     *
     * @param path
     * @param filename doc1的文件名
     * @param doc1 一个源代码文件doc1的<word,TF-IDF>
     * @param doc2Path 一个bug文件的绝对地址
     * @return
     */

    String Model = "VSM";
    public VectorSpaceModel(String a){
        this.Model=a;
    }

    public double calculate(String path, String filename, Map<String, Double> doc1, String doc2Path){
        DecimalFormat df = new DecimalFormat("#00.0000");
        File file = new File(path);
        File[] fs = file.listFiles();
        List<String> files = new ArrayList<String>();
        List<String> names = new ArrayList<String>();
        for(File f:fs){
            if(!f.isDirectory() && !f.getAbsoluteFile().equals(filename)) {        //去除filename
                files.add(f.getAbsolutePath());
                names.add(f.getName());
            }
        }

        files.add(doc2Path);
        names.add(doc2Path.substring(doc2Path.lastIndexOf("/")+1));
        query queries = new query(files);
        ArrayList<wordMap> corpus = queries.getCorpus();
        int FNum = queries.getDFileNums();

        Map<String,Double> doc2 = new HashMap<>();

        HashMap<String, Integer> wordCount = corpus.get(corpus.size()-1).getWordCountTable();
        for(String tmpWord : wordCount.keySet()) {
            doc2.put(tmpWord,queries.countTF(doc2Path,tmpWord)*queries.countIDF(tmpWord));
        }

        //选择前 20 个词
        List<Map.Entry<String,Double>> mappingList = null;
        mappingList = new ArrayList<Map.Entry<String,Double>>(doc2.entrySet());
        //通过比较器实现比较排序
        Collections.sort(mappingList, new Comparator<Map.Entry<String,Double>>(){
            public int compare(Map.Entry<String,Double> mapping1,Map.Entry<String,Double> mapping2){
                if (mapping1.getValue()< mapping2.getValue()) return 1;
                else return -1; }
        });

        doc2.clear();
        for(int j = 0;j < (mappingList.size()) && j<20;j++) {
            doc2.put(mappingList.get(j).getKey(),mappingList.get(j).getValue());
       }

        Vector<Double> v1 = new Vector<>();
        Vector<Double> v2 = new Vector<>();
        for(String w:doc1.keySet()) {
            v1.add(doc1.get(w));
            if(doc2.containsKey(w))
                v2.add(doc2.get(w));
            else
                v2.add(0.0);
        }
        for(String w:doc2.keySet()) {
            if(!doc1.containsKey(w)){
                v1.add(0.0);
                v2.add(doc2.get(w));
            }
        }

        Distance distance = new Distance(v1,v2);

        if(Model=="COS")
            return distance.cosDistance();
        else if(Model=="EUC")
            return distance.euclideanDistance();
        else if(Model=="MAN")
            return distance.manhattanDistance();
        else //Model=="VSM"
            return distance.vsmDistance();
    }
}
