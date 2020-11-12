package com.visa;

import com.visa.controller.WebController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class VisaApplicationTests {

        @Autowired 
        private WebController controller;
        
	@Test
	void contextLoads() {
            Assertions.assertNotNull(controller);
	}
        
  

}
