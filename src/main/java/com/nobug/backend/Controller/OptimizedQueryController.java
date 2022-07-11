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
        //此处需要返回一个list 即优化查询的定位结果，包括按照rate从高到低排列的20个源文件与rate
        OptimizedQueryService.optimizedQueryImpl(name);

    }

}
