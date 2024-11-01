package com.studyflow.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.studyflow.dto.SubjectDTO;
import com.studyflow.service.SubjectService;

@Controller
public class SubjectController {
	
	SubjectService subs;
	
	@Autowired
	public SubjectController(SubjectService subs) {
		this.subs = subs;
	}
	
	// 과목 이름 리스트 가져오는 API
	@GetMapping("/api/subject")
	public ResponseEntity<List<SubjectDTO>> getSubjectName() {
		List<SubjectDTO> res = subs.getSubjectName();
		
		return ResponseEntity.status(200).body(res);
	}
}
