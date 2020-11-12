/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.visa.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author wuthichailee
 */
@Controller
public class WebController {
    @RequestMapping("/myServlet/hello")
    
    public String sayHello(Model model) {
        System.out.println("Greeting!!  How are you?");
        model.addAttribute("message", "Welcome to Spring Boot with NetBeans IDE");
        return "hello";
    }      
    
    public String seeYa() {
        return "See Ya People!";
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("City{");
        sb.append("id=").append("100");
        sb.append(", name='").append("Woody").append('\'');
        sb.append(", population=").append("10000");
        sb.append('}');
        return sb.toString();
    }
}
