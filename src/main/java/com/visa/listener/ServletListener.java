/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.visa.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 *
 * @author wuthichailee
 */

public class ServletListener implements ServletContextListener {
    
  @Override
  public void contextInitialized(ServletContextEvent e) {
    System.out.println("MyServletContextListener Context Initialized");
  }

  @Override
  public void contextDestroyed(ServletContextEvent e) {
    System.out.println("MyServletContextListener Context Destroyed");
  }

}