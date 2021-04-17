package Preprocess;

import Preprocess.preprocess;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ReadFromFile {

    static List<String> keyWordsList = new ArrayList<>();
    static List<String> stopWordsList = new ArrayList<>();

    public static void setStopWordsList(List<String> stopWordsList) {
        ReadFromFile.stopWordsList = stopWordsList;
    }

    public static void setKeyWordsList(List<String> keyWordsList) {
        ReadFromFile.keyWordsList = keyWordsList;
    }

    /**
     * 以字节为单位读取文件。
     */

    public static void recursiveRead(String path,String resultPath) {
        File file = new File(path);
        File[] files = file.listFiles();
        for(File f : files){
            if(f.isDirectory()){
                recursiveRead(f.getPath(),resultPath);
            }
            if(f.isFile()){
                readFileByBytes(f.getPath(),resultPath);
                //readFileByChars(f.getPath());
            }
        }
    }

    public static void readFileByBytes(String fileName,String resultPath) {

        int docIndex = fileName.lastIndexOf(".");
        int nameIndex = fileName.lastIndexOf("\\");
        String resultName = fileName.substring(nameIndex+1,docIndex);
        String type = fileName.substring(docIndex+1);
        if(type.equals("java")) {
            resultName += ".txt";

            //File file = new File(fileName);
            File os = new File(resultPath + resultName);
            //File os = new File("data/report_preprocessed/" + resultName);
            InputStream in = null;
            OutputStream out;
            try {
                System.out.println("以字节为单位读取文件内容，一次读多个字节：");
                // 一次读多个字节

                int byteread = 0;
                in = new FileInputStream(fileName);
                byte[] tempbytes = new byte[in.available()];
                out = new FileOutputStream(os);
                ReadFromFile.showAvailableBytes(in);

                // 读入多个字节到字节数组中，byteread为一次读入的字节数
                while ((byteread = in.read(tempbytes)) != -1) {
                    //System.out.write(tempbytes, 0, byteread);
                    String comments = new String(tempbytes);

                    preprocess.setKeyWordsList(keyWordsList);
                    preprocess.setStopWordsList(stopWordsList);
                    comments = comments.toLowerCase();
                    comments = preprocess.removeStopWords(comments);
                    comments = preprocess.removeKeyWords(comments);

                    comments = preprocess.lemmatisation(comments);
                    comments = preprocess.splitter(comments);
                    comments = preprocess.lemmatisation(comments);
                    comments = preprocess.stemming(comments);
                    comments = comments.replaceAll("\\s+"," ");//多空格替换为单空格
                    if(comments.endsWith(" ")){
                        comments = comments.substring(0,comments.length()-1);
                    }
                    out.write(comments.getBytes());

                }
            } catch (Exception e1) {
                e1.printStackTrace();
            } finally {
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e1) {
                    }
                }
            }
        }
    }
    public static void readBugFile(String fileName,String resultPath) {

        int docIndex = fileName.lastIndexOf(".");
        String resultName = ".txt";
        String type = fileName.substring(docIndex+1);
        if(type.equals("xml")) {
            InputStream in = null;
            OutputStream out = null;
            try {
                System.out.println("以字节为单位读取文件内容，一次读多个字节：");
                // 一次读多个字节

                int byteread = 0;
                in = new FileInputStream(fileName);
                byte[] tempbytes = new byte[in.available()];

                ReadFromFile.showAvailableBytes(in);
                // 读入多个字节到字节数组中，byteread为一次读入的字节数
                while ((byteread = in.read(tempbytes)) != -1) {
                    String comments = new String(tempbytes);
                    String comments0 = "";
                    for(int i = 0;i<byteread - 13;i++){
                        if(comments.substring(i,i+7).equals("bug id=")){
                            resultName = "report"+comments.substring(i+8,comments.indexOf("\"",i+8))+".txt";
                            File os = new File(resultPath + resultName);
                            out = new FileOutputStream(os);
                        }

                        if(comments.substring(i,i+9).equals("<summary>")){//title information
                            comments0 = comments.substring(i+9,comments.indexOf("</summary>",i));
                        }
                        if(comments.substring(i,i+13).equals("<description>")){
                            String comments1 = comments0 + comments.substring(i+13,comments.indexOf("</description>",i));
                            preprocess.setKeyWordsList(keyWordsList);
                            preprocess.setStopWordsList(stopWordsList);
                            comments1 = comments1.toLowerCase();
                            comments1 = preprocess.removeStopWords(comments1);
                            comments1 = preprocess.removeKeyWords(comments1);
                            comments1 = preprocess.lemmatisation(comments1);
                            comments1 = preprocess.splitter(comments1);
                            comments1 = preprocess.lemmatisation(comments1);
                            comments1 = preprocess.stemming(comments1);
                            comments1 = comments1.replaceAll("\\s+"," ");//多空格替换为单空格
                            if(comments1.endsWith(" ")){
                                comments1 = comments1.substring(0,comments.length()-1);
                            }
                            out.write(comments1.getBytes());


                        }
                    }

                }
            }
            catch (Exception e1) {
                e1.printStackTrace();
            }
            finally {
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e1) {
                    }
                }
            }
        }
    }
    public static void readFileByLines(String fileName, List<String> list) {
        File file = new File(fileName);
        BufferedReader reader = null;
        try {
            System.out.println("以行为单位读取文件内容，一次读一整行：");
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int line = 1;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                // 显示行号

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

    /**
     * 显示输入流中还剩的字节数
     */

    private static void showAvailableBytes(InputStream in) {
        try {

            System.out.println("当前字节输入流中的字节数为:" + in.available());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void getResult() {
        String path1 = "data\\swt-3.1";

        String path2 = "data\\SWTBugRepository.xml";
        List<String> keyWordsList = new ArrayList<String>();
        List<String> stopWordsList = new ArrayList<String>();
        ReadFromFile.readFileByLines("src/main/resources/keyword",keyWordsList);
        ReadFromFile.readFileByLines("src/main/resources/stopword",stopWordsList);
        ReadFromFile.setKeyWordsList(keyWordsList);
        ReadFromFile.setStopWordsList(stopWordsList);

        ReadFromFile.recursiveRead(path1,"data/class_preprocessed2/");
        ReadFromFile.readBugFile(path2,"data/report_preprocessed2/");

    }

}
