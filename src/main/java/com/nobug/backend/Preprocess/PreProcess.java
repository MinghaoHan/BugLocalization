package com.nobug.backend.Preprocess;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;
import edu.stanford.nlp.util.StringUtils;
import org.tartarus.snowball.ext.PorterStemmer;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class PreProcess {

    List<String> keyWordsList = new ArrayList<String>();
    List<String> stopWordsList = new ArrayList<String>();

    public PreProcess(){
        this.setKeyWordsList();
        this.setStopWordsList();
    }

    public String completePreProcess(String comments){

        comments = removeStopWords(comments);
        comments = removeKeyWords(comments);

        comments = lemmatisation(comments);
        comments = splitter(comments);
        comments = lemmatisation(comments);
        comments = stemming(comments);
        comments = comments.replaceAll("\\s+"," ");//多空格替换为单空格
        if(comments.endsWith(" ")){
            comments = comments.substring(0,comments.length()-1);
        }
        return  comments;
    }

    public static void readFileByLines(String fileName, List<String> list) {
        File file = new File(fileName);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int line = 1;
            while ((tempString = reader.readLine()) != null) {
                list.add(tempString);
                line++;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
    }

    public void setKeyWordsList() {
        this.readFileByLines("src/main/resources/keyword",this.keyWordsList);
    }

    public void setStopWordsList() {
        this.readFileByLines("src/main/resources/stopword",this.stopWordsList);
    }

    public String removeKeyWords(String comments){
        String[] commentsList = comments.split(" ");
        for(String str1 : keyWordsList){
            for(int i3 = 0;i3<commentsList.length;i3++){
                if(str1.equals(commentsList[i3])){
                    commentsList[i3] = "";
                }
            }

        }
        comments = String.join(" ",commentsList);
        comments = comments.replaceAll("\\s+"," ");//多空格替换为单空格
        comments = comments.trim();
        return comments;
    }
    public String removeStopWords(String comments){
        int i = comments.indexOf("/*");

        while(i!=-1) {
            comments = comments.replace(comments.substring(i, comments.indexOf("*/",i)+2), "");
            i = comments.indexOf("/*");
        }

        comments = comments.replace("\n"," ");

        for(int i1 = 0;i1<46;i1++){
            comments = comments.replace(stopWordsList.get(i1)," ");
        }
        comments = comments.replaceAll("\\s+"," ");//多空格替换为单空格
        comments = comments.trim();
        String[] commentsList = comments.split(" ");
        for(String str1 : stopWordsList){
            for(int i3 = 0;i3<commentsList.length;i3++){
                if(str1.equals(commentsList[i3]) || commentsList[i3].length()==1){
                    commentsList[i3] = "";
                }
            }

        }
        comments = String.join(" ",commentsList);
        return comments;
    }

    public String lemmatisation(String comments){
        Properties props = new Properties();  // set up pipeline properties
        props.put("annotators", "tokenize, ssplit, pos, lemma");   //分词、分句、词性标注和次元信息。
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
        String result = "";
        Annotation document = new Annotation(comments);
        pipeline.annotate(document);
        List<CoreMap> words = document.get(CoreAnnotations.SentencesAnnotation.class);
        for(CoreMap word_temp: words) {
            for (CoreLabel token: word_temp.get(CoreAnnotations.TokensAnnotation.class)) {

                String lema = token.get(CoreAnnotations.LemmaAnnotation.class);  // 获取对应上面word的词元信息，即我所需要的词形还原后的单词

                result+=lema + " ";
            }
        }

        return result;
    }


    public String stemming (String comments) {
        String[] words = comments.split(" ");
        PorterStemmer stem = new PorterStemmer();
        String result = "";
        for(String word : words) {
            stem.setCurrent(word);
            stem.stem();
            result += stem.getCurrent()+" ";
        }
        return result.substring(0,result.length()-1);
    }


    public String splitterLittle(String comments){
        try {
            File directory = new File("");

            String path = directory.getAbsolutePath()+"\\src\\main\\java\\PreProcess\\splitter.py";
            String[] args = new String[]{"python", path, comments};
            Process proc = Runtime.getRuntime().exec(args);// 执行py文件

            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String line = null;
            while ((line = in.readLine()) != null) {
                //System.out.println(line);
                comments = line.replace("][", " ");
                comments = comments.replace("[", "");
                comments = comments.replace(",", "");
                comments = comments.replace("]", "");
                comments = comments.replace("\'", "");
            }
            in.close();
            proc.waitFor();
            return comments;
        }catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String splitter(String comments) {
        String result = "";
        while(comments.length()>20000){
            String comments1 = comments.substring(0,comments.indexOf(" ",10000));
            splitterLittle(comments1);
            result+=comments1;
            comments = comments.substring(comments.indexOf(" ",10000)+1);
        }

        comments = splitterLittle(comments);
        result+=comments;
        result = result.replaceAll("\\s+"," ");//多空格替换为单空格
        String[] strlist = result.split(" ");
        for(int i = 0;i<strlist.length;i++){
            if(strlist[i].length()==1){
                strlist[i] = "";
            }
        }
        result = StringUtils.join(strlist," ");
        return result;

    }

}
