package com.creditcalc.creditcalc;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class routing {

    @GetMapping("/home")
    public String get(HttpSession session){
            if(session.getAttribute("role") == null){
                return "login";
            }else{
                return "home";
            }
    }

    @PostMapping("/logging")
    public String logging(HttpSession session, @RequestParam("login") String login, @RequestParam("password") String password, Model model){
        String loginDTO = "Baldzik";
        String passwordDTO = "Baldiko123";

        if(login.equals(loginDTO) && password.equals(passwordDTO)){
            session.setAttribute("role", "User");
            session.setAttribute("login", login);

            return "home";
        }else{
            model.addAttribute("message", "Unable to login, wrong password or login");
            return "login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("role");
        return "login";
    }
}
