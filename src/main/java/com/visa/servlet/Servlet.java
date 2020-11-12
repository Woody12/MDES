/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.visa.servlet;

import com.visa.services.TokenConnect;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author wuthichailee
 */

// NOTE:  Added this class to capture the Context and httpRequest
// In the end, we don't need it but good to know how it works
// https://www.boraji.com/spring-boot-using-servlet-filter-and-listener-example-1
public class Servlet extends HttpServlet {

  private static final long serialVersionUID = 1L;

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    System.out.println("Token Connect's doGet() method is invoked.");
      try {
          doAction(req, resp);
      } catch (Exception ex) {
          Logger.getLogger(Servlet.class.getName()).log(Level.SEVERE, null, ex);
      }
  }
  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    System.out.println("MyServlet's doPost() method is invoked.");
      try {
          doAction(req, resp);
      } catch (Exception ex) {
          Logger.getLogger(Servlet.class.getName()).log(Level.SEVERE, null, ex);
      }
  }
  
  private void doAction(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException, Exception {
    
    resp.setContentType("application/json");
   
    TokenConnect tokenConnect = new TokenConnect();
    
    switch (req.getRequestURI()) {
        case "/tokenConnect/list": 
            resp.getWriter().write(tokenConnect.retrieveTokenRequestors());
            break;
        case "/tokenConnect/asset":
            resp.getWriter().write(tokenConnect.retrieveAsset(req.getParameter("id")));
            break;
        case "/tokenConnect/pushCard":
            resp.getWriter().write(tokenConnect.pushCard(req.getParameter("trid")));
            break;  
    }
   
    //String name = req.getParameter("name");
    //System.out.println("req path is " + req.getServletPath() + " last path only " + req.getPathInfo() + " queryString: " + req.getQueryString());
    //System.out.println("/n req method is " + req.getMethod() + " url is " + req.getRequestURL());
  }
}