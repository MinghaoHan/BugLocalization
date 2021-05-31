package com.nobug.backend.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

@RestController
public class FileContentController {

    @RequestMapping("/getContentByName")
    public String getFileContent(@RequestParam String name) {
        String fileName = "data/class_preprocessed2/" + name;
        StringBuilder text = new StringBuilder();
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
            String line = "";

            while ((line = bufferedReader.readLine()) != null)
                text.append(line);
            bufferedReader.close();//关闭字节流
        }
        catch (IOException e) {
            text.append("file not found");
        }
        return text.toString();
    }

}
