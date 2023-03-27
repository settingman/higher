package com.hyundai.higher;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({ "com.hyundai.higher.controller.beauty", "com.hyundai.higher.mapper.beauty", "com.hyundai.higher.service.beauty" 
	 , "com.hyundai.higher.mapper.product" , "com.hyundai.higher.service.product" 	

})
public class TestConfig {
}