package com.visa;

import com.visa.filter.ServletFilter;
import com.visa.listener.ServletListener;
import com.visa.servlet.Servlet;
import javax.servlet.ServletContextListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class VisaApplication {

    // Register Servlet
    @Bean
    public ServletRegistrationBean servletRegistrationBean() {
        ServletRegistrationBean bean = new ServletRegistrationBean(new Servlet(), "/tokenConnect/*");
        return bean;
    }
    
    // Register Filter
    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean bean = new FilterRegistrationBean(new ServletFilter());
        // Mapping filter to a Servlet
        bean.addServletRegistrationBeans(new ServletRegistrationBean[] {
              servletRegistrationBean() 
           });
        return bean;
    }

    // Register ServletContextListener
    @Bean
    public ServletListenerRegistrationBean<ServletContextListener> listenerRegistrationBean() {
        ServletListenerRegistrationBean<ServletContextListener> bean = 
            new ServletListenerRegistrationBean<>();
        bean.setListener(new ServletListener());
        return bean;
    }
    
    public static void main(String[] args) {
            SpringApplication.run(VisaApplication.class, args);
    }
}
