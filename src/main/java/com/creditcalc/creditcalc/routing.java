package com.creditcalc.creditcalc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.beans.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Controller
public class routing {

    @Autowired
    JdbcTemplate jdbcTemplate;

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
        String username = null;
        String pass = null;
        String role = null;

        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/creditcalcjava", "root", "");

            String query = "SELECT Username, Password, Role FROM Users WHERE Username = ?";

            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, login);
            ResultSet rs = preparedStatement.executeQuery();


            while (rs.next()) {
                System.out.println(rs.getString(1));
                username = rs.getString(1);
                pass = rs.getNString(2);
                role = rs.getString(3);
            }

        }catch (Exception ex)
        {
            System.out.println(ex);
        }

        if(username.equals(login) && password.equals(pass)){
            session.setAttribute("role", role);
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
