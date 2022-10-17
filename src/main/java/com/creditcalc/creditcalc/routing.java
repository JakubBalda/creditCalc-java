package com.creditcalc.creditcalc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class routing {

    @GetMapping("/home")
    public String get(){
        return "home";
    }
}
