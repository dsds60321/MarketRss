package com.gen.marketrss.interfaces.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class InitController {

    @RequestMapping(value = "/**/{[path:[^\\.]*}")
    public String forward() {
        return "forward:/index.html";
    }
}
