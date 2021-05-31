package com.nobug.backend.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FileListController {

    @RequestMapping("/getFileList")
    public String getName() {
        return "hello";
    }

}
