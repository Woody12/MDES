/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.visa.controller;

import com.visa.services.TokenConnect;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author wuthichailee
 */

@RestController
public class WebServicesController {
    
    @Autowired 
    private WebController controller;
    
    @GetMapping("/rest")
    public String takeRest() {
        System.out.println("Rest is needed.. show json");
        controller = new WebController();
        
        return controller.seeYa().toString(); //"Rest is only necessary - Not a goal!!";
    }
    
    @RequestMapping(value="/listrest", method=RequestMethod.GET, headers={"Accept=application/json"})
    public ResponseEntity<List<String>> showRest() {
        List<String> values = new ArrayList<String>();
        values.add("Hello there");
        values.add("bye for now");
        values.add("See you later");
       
        return ResponseEntity.ok(values);
    }
    
}
