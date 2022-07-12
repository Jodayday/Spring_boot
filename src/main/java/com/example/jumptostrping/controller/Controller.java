package com.example.jumptostrping.controller;

import org.springframework.web.bind.annotation.RequestMapping;


@org.springframework.stereotype.Controller
public class Controller {

    @RequestMapping("/")
    public String root(){
        return "redirect:/question/list";
    }


}
