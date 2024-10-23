package com.studyflow.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.studyflow.dto.StudentDTO;
import com.studyflow.service.HomeworkService;
import com.studyflow.service.StudentService;

@Controller
public class HomeworkController {

	StudentService stus;
	HomeworkService homs;

	@Autowired
	public HomeworkController(StudentService stus, HomeworkService homs) {
		this.stus = stus;
		this.homs = homs;
	}

	// id로 해당 학생 전체 숙제 정보를 조회하는 api
	@GetMapping("/api/student-homework/{id}")
	public ResponseEntity<StudentDTO> getHomeworkById(@PathVariable("id") int id) {
		return ResponseEntity.status(200).body(stus.getHomeworkById(id));
	}

}
