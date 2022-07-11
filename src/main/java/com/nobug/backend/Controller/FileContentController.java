package com.nobug.backend.Controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nobug.backend.Service.FileContentService;

@RestController
public class FileContentController {

    @CrossOrigin
    @RequestMapping("/getContentByName")
    public String getFileContent(@RequestParam String name) {
        return FileContentService.getFileContentImpl(name);
    }

}
