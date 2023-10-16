package com.example.jsonfile;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.example.jsonfile.dto.Student;

@Component
public class AppConfig {
	@Bean
	public Student student() {
		return new Student();
}
}