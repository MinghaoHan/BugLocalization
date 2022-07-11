package com.nobug.backend.Service;

import org.springframework.web.bind.annotation.RequestParam;

import java.io.*;

public class FileContentService {

    public static String getFileContentImpl(@RequestParam String name) {
        File file = new File("data\\swt-3.1");
        String fileName = heq(file, name);

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
        return text.toString();
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
