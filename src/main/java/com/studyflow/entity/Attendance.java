package com.studyflow.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Attendance {
	
	@Id
	private Integer studentId; 
}
