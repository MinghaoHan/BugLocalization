package com.nobug.backend.Preprocess;

import com.nobug.backend.Optimize.Bee;
import org.eclipse.jdt.core.dom.CompilationUnit;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ReadFromFile {

    static PreProcess p = new PreProcess();

    /** 以字节为单位读取文件 **/
    public void recursiveRead(String path,String resultPath) throws IOException {
        File file = new File(path);
        File[] files = file.listFiles();
        for(File f : files){
            if(f.isDirectory()){
                recursiveRead(f.getPath(),resultPath);
            }
            if(f.isFile()){
                readFileByBytes(f.getPath(),resultPath);
            }
        }
    }

    /** 单个源文件的预处理 **/
    public void readFileByBytes(String fileName,String resultPath) throws IOException {

        int docIndex = fileName.lastIndexOf(".");
        int nameIndex = fileName.lastIndexOf(File.separator);
        String resultName = fileName.substring(nameIndex+1,docIndex);
        String type = fileName.substring(docIndex+1);

        if(type.equals("java")) {
            System.out.println("********"+"Source File: "+resultName+".java********");
            resultName += ".txt";
            File os = new File(resultPath + resultName);
            InputStream in = null;
            OutputStream out = new FileOutputStream(os);;

            List<String> contents = new ArrayList<>();
            contents = this.getInformation(fileName);

            List<String> tmpPre = new ArrayList<>();
            String pre = "";

            for(int i=0;i<contents.size();i++){
                String s = contents.get(i);
                s = s.toLowerCase();
                String[] tmp = s.split("_");
                for(int j=0;j<tmp.length;j++){
                    if(tmp.length==1 || !tmpPre.contains(tmp[j])) {
                        tmpPre.add(tmp[j]);
                        pre = pre + " " + tmp[j];
                    }
                }
            }

            pre = p.completePreProcess(pre);

            for(int i=0;i<contents.size();i++){
                pre = pre + " " + contents.get(i).toLowerCase();
            }

            out.write(pre.getBytes());
        }
    }



    public void readBugFile(String fileName, String resultPath) {

        int docIndex = fileName.lastIndexOf(".");
        String resultName = ".txt";
        String type = fileName.substring(docIndex+1);

        if(type.equals("xml")) {
            InputStream in = null;
            OutputStream out = null;

            try {
                int byteread = 0;
                in = new FileInputStream(fileName);
                byte[] tempbytes = new byte[in.available()];

                while ((byteread = in.read(tempbytes)) != -1) {
                    String comments = new String(tempbytes);
                    String title = "";
                    String description = "";
                    boolean flag = false;
                    for(int i = 0;i<byteread - 13;i++){

                        /** 形成文件名 **/
                        if(comments.substring(i,i+7).equals("bug id=")){
                            flag = true;
                            resultName = "report"+comments.substring(i+8,comments.indexOf("\"",i+8))+".txt";
                            File os = new File(resultPath + resultName);
                            out = new FileOutputStream(os);
                        }

                        /** Title **/
                        if(comments.substring(i,i+9).equals("<summary>")){
                            title = comments.substring(i+9,comments.indexOf("</summary>",i));
                        }

                        /** bug详细内容 **/
                        if(comments.substring(i,i+13).equals("<description>")){
                            description = comments.substring(i+13,comments.indexOf("</description>",i));
                        }

                        if(flag==true&&comments.substring(i,i+6).equals("</bug>")){
                            System.out.println("********"+"Bug Report: "+resultName+"********");
                            if(!description.isEmpty())
                                description = getContent(description);
                            String comments1 = title +" "+description;
                            comments1 = getResultString(comments1);

                            out.write(comments1.getBytes());
                            flag = false;
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

    public String getContent(String sentence) throws Exception {
        String[] tmpSen = sentence.split("[.][\\s]");
        String res = "";
        for(int i = 0 ; i<tmpSen.length;i++){
            if(tmpSen[i].length()>=6&&this.isOB(tmpSen[i])==true){
                res += tmpSen[i] + " ";
            }
        }
        return res;
    }

    public boolean isOB(String sentence) throws Exception {
        String res = new Bee().getBee(sentence);
        return res.contains("OB");
    }

    private String getResultString(String comments) {
        PreProcess p = new PreProcess();
        p.setKeyWordsList();
        p.setStopWordsList();
        comments = comments.toLowerCase();
        comments = p.completePreProcess(comments);
        return comments;
    }

    public void getResult() throws IOException {
        /** 此处修改待预处理的文件 **/
        String path1 = "data"+File.separator+"swt-3.1";
        String path2 = "data"+File.separator+"SWTBugRepository.xml";

        /** 预处理源文件 **/
//        this.recursiveRead(path1,"data/class_preprocessed3/");

        /** 预处理bug报告 **/
        this.readBugFile(path2,"data/report_preprocessed3/");
    }

    /** AST: 返回Java文件 所有标识符（类名、方法名、变量名） **/
    public List<String> getInformation(String javaFilePath){
        CompilationUnit comp = JdtAstUtil.getCompilationUnit(javaFilePath);

        DemoVisitor visitor = new DemoVisitor();
        comp.accept(visitor);
        List<String> comment = visitor.comments;
        return comment;
    }


}
