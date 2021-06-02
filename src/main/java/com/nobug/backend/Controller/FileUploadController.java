package com.nobug.backend.Controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;

@RestController
public class FileUploadController {

    @CrossOrigin
    @RequestMapping("/uploadFile")
    public void upload(@RequestParam MultipartFile file, HttpServletRequest request){
        //得到文件"upload"的服务器路径
        System.out.println(file.getOriginalFilename());

        try {
            InputStream is = file.getInputStream();

            File filedir = new File("data\\uploadFileSet");
            if (!filedir.exists()) {
                filedir.mkdirs();
            }

            String newFileName = file.getOriginalFilename();
            if (newFileName == null)
                newFileName = "emptyName" + Math.random();

            File newfile = new File(filedir, file.getOriginalFilename());
            OutputStream os = new FileOutputStream(newfile);

            byte[] byteStr = new byte[1024];
            int len = 0;
            while ((len = is.read(byteStr)) > 0) {
                os.write(byteStr,0,len);
            }
            is.close();
            os.flush();
            os.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
