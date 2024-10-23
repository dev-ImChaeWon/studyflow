package com.studyflow.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.studyflow.dto.StudentDTO;
import com.studyflow.response.PageResponse;
import com.studyflow.service.StudentService;

@Controller
public class StudentController {

	StudentService stus;

	@Autowired
	public StudentController(StudentService stus) {
		this.stus = stus;
	}

	// 학생 목록 조회 API
//	public ResponseEntity<List<StudentDTO>> getAllStudent() { // List<StudentDTO> 타입
//		List<StudentDTO> res = stus.getAllStudent();
//		
//		return ResponseEntity.status(200).body(res);
//	}
	@GetMapping("/api/student")
	public ResponseEntity<PageResponse<StudentDTO>> getStudent(
			@RequestParam(name = "page", defaultValue = "1") int page,
			@RequestParam(name = "size", defaultValue = "10") int size,
			@RequestParam(name = "date", required = true) LocalDateTime date,
			@RequestParam(name = "teacher", required = false) String teacherName,
			@RequestParam(name = "isEnd", required = false) String isEnd,
			@RequestParam(name = "student", required = false) String studentName) {

		return ResponseEntity.status(200).body(stus.getStudent(page, size, date, teacherName, isEnd, studentName));

	}

}
