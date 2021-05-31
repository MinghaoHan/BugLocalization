package com.nobug.backend.Controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;

@RestController
public class FileContentController {

    @CrossOrigin
    @RequestMapping("/getContentByName")
    public String getFileContent(@RequestParam String name) {
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


    public String heq(File file, String name) {
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
