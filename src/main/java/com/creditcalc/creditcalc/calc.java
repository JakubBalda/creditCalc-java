package com.creditcalc.creditcalc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.beans.PropertyEditorSupport;


@Controller
public class calc {

    @Autowired
    JdbcTemplate jdbcTemplate;
    @PostMapping("/calculate")
    public String calculate(@RequestParam("value") double value, @RequestParam("years") int years, @RequestParam(name = "percentage", defaultValue = "0") int percentage, Model model){
        double rate = 0.0;



        if(percentage != 0){
            rate = (value + (value * (percentage/100)))/(years*12);
        }else{
            rate = value / (years*12);
        }
        rate = Math.round(rate * 100.0)/100.0;
        model.addAttribute("rate", rate);

        storeInDb(rate);
        return "home";
    }

    public void storeInDb(double result){
        String query = "INSERT INTO results (result) VALUES (?)";

        jdbcTemplate.update(query, result);
    }
}
