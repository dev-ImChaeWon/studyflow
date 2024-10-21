package com.studyflow.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.studyflow.dto.TeacherDTO;
import com.studyflow.service.TeacherService;

@Controller
public class TeacherController {
	
	TeacherService teas;
	
	@Autowired
	public TeacherController(TeacherService teas) {
		this.teas = teas;
	}
	
	// 학원에 등록된 모든 선생님을 조회하는 API
	@GetMapping("/api/teacher")
	public ResponseEntity<List<TeacherDTO>> getAllTeacher() {	// List<TeacherDTO> 타입으로 
		List<TeacherDTO> res = teas.getAllTeacher();
		
		return ResponseEntity.status(200).body(res);
	}
}
