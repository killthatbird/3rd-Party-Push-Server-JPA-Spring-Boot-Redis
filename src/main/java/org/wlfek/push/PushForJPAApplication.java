package org.wlfek.push;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@ImportResource("/tasks.xml")
public class PushForJPAApplication {

	public static void main(String[] args) {
		SpringApplication.run(PushForJPAApplication.class, args);
	}
}
