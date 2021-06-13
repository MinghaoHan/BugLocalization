package com.nobug.backend.Controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.nobug.backend.Service.BugDescriptionService;

@RestController
public class BugDescriptionController {
    @CrossOrigin
    @RequestMapping("/getBugDescription")
    public String[] getBugDescription(@RequestParam String bugID) {
        return BugDescriptionService.getBugDescriptionImpl(bugID);
    }
}
