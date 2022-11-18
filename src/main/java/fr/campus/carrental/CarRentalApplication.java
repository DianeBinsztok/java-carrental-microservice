package fr.campus.carrental;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;

/** https://developer.okta.com/blog/2019/04/16/spring-boot-tomcat */
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class CarRentalApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(CarRentalApplication.class, args);
	}

}