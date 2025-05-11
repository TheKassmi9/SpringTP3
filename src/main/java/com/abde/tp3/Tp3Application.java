package com.abde.tp3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import lombok.extern.slf4j.Slf4j;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

@SpringBootApplication
@Slf4j
public class Tp3Application {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(Tp3Application.class, args);
	}

}
