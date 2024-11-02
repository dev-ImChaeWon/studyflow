package com.studyflow.controller;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.studyflow.dto.HomeworkDTO;
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
	
	// id와 date로 해당 날짜와 해당 학생 숙제 정보 조회
	@GetMapping("/api/student-homework")
	public ResponseEntity<StudentDTO> getHomeworkByIdAndDate(
			@RequestParam(name = "id", required = false) int id,
			@RequestParam(name = "date", required = true) LocalDate date) {
		return ResponseEntity.status(200).body(stus.getHomeworkByIdAndDate(id, date));
	}
	
	// 테스트용 
	@GetMapping("/api/get-homework")
	public ResponseEntity<HomeworkDTO> getHomeworkByDateRange(
			@RequestParam(name = "date", required=true) LocalDate date){
		
		return ResponseEntity.status(200).body(homs.getHomeworkByDateRange(date));
	}

}
