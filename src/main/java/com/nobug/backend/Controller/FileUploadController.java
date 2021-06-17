package com.nobug.backend.Controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.nobug.backend.Service.FileUploadService;

import javax.servlet.http.HttpServletRequest;
import java.io.*;

@RestController
public class FileUploadController {

    @CrossOrigin
    @RequestMapping("/uploadFile")
    public void upload(@RequestParam MultipartFile file, HttpServletRequest request){
        FileUploadService.uploadImpl(file,request);

    }

}
