package com.Infotrixs.Payroll_System;

import com.Infotrixs.Payroll_System.PayrollUI.Main;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PayrollSystemApplication {

	public static void main(String[] args) {
		// Start Spring Boot application
		SpringApplication.run(PayrollSystemApplication.class, args);

		// Launch JavaFX application
		Main.launchJavaFxApp(args);
	}
}
