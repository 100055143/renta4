package com.curso.hellospring.beans.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.curso.hellospring.beans.HelloBean;

@Configuration()
@ComponentScan(basePackages = "com.curso.hellospring")
public class SimpleConfigBean {
	
	@Bean(name = "helloBean") 
	public HelloBean helloBean(){
		return new HelloBean();
	}

}
