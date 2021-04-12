package PreProcess;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;
import org.tartarus.snowball.ext.PorterStemmer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class PreProcess {


    static List<String> keyWordsList = new ArrayList<String>();
    static List<String> stopWordsList = new ArrayList<String>();




    public PreProcess(String comments){

        removeStopWords(comments);
        removeKeyWords(comments);
    }

    public static void setKeyWordsList(List<String> keyWordsList) {
        PreProcess.keyWordsList = keyWordsList;
    }

    public static void setStopWordsList(List<String> stopWordsList) {
        PreProcess.stopWordsList = stopWordsList;
    }

    public static String removeKeyWords(String comments){
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
    public static String removeStopWords(String comments){
        int i = comments.indexOf("/*");

        while(i!=-1) {
            comments = comments.replace(comments.substring(i, comments.indexOf("*/",i)+2), "");
            i = comments.indexOf("/*");
        }
        /*
        int i0 = comments.indexOf("//");

        while (i0!=-1){

            comments = comments.replace(comments.substring(i0, comments.indexOf("\n",i0)), "");
            i0 = comments.indexOf("//");
        }

         */
        comments = comments.replace("_"," ");
        comments = comments.replace("\n"," ");
        for(int i1 = 0;i1<38;i1++){
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

    public static String lemmatisation(String comments){
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
                //System.out.println(lema);
                result+=lema + " ";
            }
        }
        //result = result.substring(0,result.length()-1);
        return result;
    }


    public static String stemming (String comments) {
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
    public static String splitterLittle(String comments){
        try {
            String[] args = new String[]{"python", "D:\\splitter.py", comments};
            Process proc = Runtime.getRuntime().exec(args);// 执行py文件

            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String line = null;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
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
    public static String splitter(String comments) {


            String result = "";
            while(comments.length()>20000){
                String comments1 = comments.substring(0,comments.indexOf(" ",10000));
                splitterLittle(comments1);
                result+=comments1;
                comments = comments.substring(comments.indexOf(" ",10000)+1);


            }
            splitterLittle(comments);
            result+=comments;

            return result;


    }

}





