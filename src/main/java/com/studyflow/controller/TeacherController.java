package com.studyflow.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.studyflow.dto.TeacherDTO;
import com.studyflow.service.TeacherService;

@Controller
public class TeacherController {
	
	TeacherService teas;
	
	@GetMapping("/api/teacher")
	public ResponseEntity<List<TeacherDTO>> getTeacherById() {	// List<TeacherDTO> 타입으로 
		return ResponseEntity.status(200).body(null);
	}
}
