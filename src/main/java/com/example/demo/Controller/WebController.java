package com.example.demo.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WebController
{
    @RequestMapping("/")
    public String hello(){
        return "forward:index.html";
    }
}
