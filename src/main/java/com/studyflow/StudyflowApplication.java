package com.studyflow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"com.studyflow"})
@EnableJpaRepositories
public class StudyflowApplication {

	public static void main(String[] args) {
		//코드 수정함!
		SpringApplication.run(StudyflowApplication.class, args);
	}

}
