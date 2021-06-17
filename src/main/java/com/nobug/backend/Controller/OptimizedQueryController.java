package com.nobug.backend.Controller;

import com.nobug.backend.Service.OptimizedQueryService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class OptimizedQueryController {

    @CrossOrigin
    @RequestMapping("/optimizedQuery")
    public void optimizedQuery(@RequestParam String name){
        //得到文件"upload"的服务器路径
        OptimizedQueryService.optimizedQueryImpl(name);

    }

}
