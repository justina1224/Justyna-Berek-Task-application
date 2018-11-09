package com.crud.kodillatasks.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
public class StaticWebPageController {

    @RequestMapping("/")
    public String index(Map<String, Object> model) {
        model.put("variable", "My Thymleaf variable");
        model.put("one", 1);
        model.put("two", 2);
        model.put("four", 4);
        model.put("multiply", "*");
        model.put("plus", "+");
        model.put("minus", "-");
        model.put("space", " ");
        model.put("equals", "=");
        return "index";
    }

}
