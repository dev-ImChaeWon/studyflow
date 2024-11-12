package com.studyflow.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.studyflow.dto.SubjectDTO;
import com.studyflow.service.SubjectService;

@Controller
@CrossOrigin(origins = "http://localhost:8088")
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

	// 교사 id로 과목 가져오는 API
	@GetMapping("/api/teacher-subject/{userId}")
	public ResponseEntity<List<SubjectDTO>> getSubject(@PathVariable("userId") String userId) {
		List<SubjectDTO> res = subs.getSubjectByTeacherId(userId);

		return ResponseEntity.status(200).body(res);
	}

	@GetMapping("/api/student-subject")
	public List<SubjectDTO> getSubjectsByStudentId(@RequestParam Integer studentId) {
		return subs.getSubjectsByStudentId(studentId);
	}
}
