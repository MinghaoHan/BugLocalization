package com.nobug.backend.Service;

import org.springframework.web.bind.annotation.RequestParam;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;

public class BugDescriptionService {

    public static String[] getBugDescriptionImpl(@RequestParam String bugID){
        String[] res = new String[2];
        if(bugID.split("\\.").length<=1) {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            try {
                DocumentBuilder builder = factory.newDocumentBuilder();//利用Builder
                Document doc = builder.parse("data\\SWTBugRepository.xml");//读取文件
                NodeList nodelist = doc.getElementsByTagName("bug");//寻找节点
                for (int i = 0; i < nodelist.getLength(); i++) {
                    Element e = (Element) nodelist.item(i);
                    String currentID = e.getAttribute("id");
                    if (currentID.equals(bugID)) {
                        Element bugInfo = (Element) e.getElementsByTagName("buginformation").item(0);
                        Node summary = bugInfo.getElementsByTagName("summary").item(0);
                        //System.out.println(summary.getFirstChild().getNodeValue());
                        res[0] = summary.getFirstChild().getNodeValue();
                        Node description = bugInfo.getElementsByTagName("description").item(0);
                        //System.out.println(description.getFirstChild().getNodeValue());
                        res[1] = description.getFirstChild().getNodeValue();
                        break;
                    }
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }else{
            File file = new File("data\\upLoadFileSet");
            String fileName = heq(file, bugID);

            StringBuilder text = new StringBuilder();
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
                String line = "";

                while ((line = bufferedReader.readLine()) != null)
                    text.append(line + "\n");
                bufferedReader.close();//关闭字节流
            }
            catch (IOException e) {
                text.append("file not found");
            }
            System.out.println(text.toString());
            res[0]="";
            res[1]=text.toString();
        }
        return res;
    }

    public static String heq(File file, String name) {
        if (file.isFile()) {
            if (file.getName().equals(name))
                return file.getPath();
            else
                return "notFound";
        }
        File[] files = file.listFiles();
        for (File file1 : files) {
            String tmp = heq(file1, name);
            if(tmp!="notFound")
                return tmp;
        }
        return "notFound";
    }
}
