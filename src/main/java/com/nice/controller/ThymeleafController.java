package com.nice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("th")
public class ThymeleafController {

    @RequestMapping("index")
    public String index(Model model){
        return "thymeleaf/reg";
    }

    @RequestMapping("sub")
    public String login(Model model){
        return "thymeleaf/sublogin";
    }

}
