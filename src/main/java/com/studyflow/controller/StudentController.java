package com.studyflow.controller;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.studyflow.dto.AttendanceDTO;
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

	// 출결 여부 조회 API
		@GetMapping("/api/attendance")
		public ResponseEntity<List<AttendanceDTO>> getAttendanceByDate(@RequestParam(name = "date", required = false) LocalDate date){
			List<AttendanceDTO> res = stus.getAttendanceByDate();
			
			return ResponseEntity.status(200).body(res);
		}
	
	@GetMapping("/api/student")
	public ResponseEntity<PageResponse<StudentDTO>> getStudent2(
			@RequestParam(name = "page", defaultValue = "1") int page,
			@RequestParam(name = "size", defaultValue = "10") int size,
			@RequestParam(name = "date", required = true) LocalDate date,
			@RequestParam(name = "teacherId", required = false) String teacherId,
			@RequestParam(name = "homeworkStatus", required = false) String homeworkStatus,
			@RequestParam(name = "studentName", required = false) String studentName) {

		PageResponse<StudentDTO> res = stus.getStudent2(page, size, date, teacherId, homeworkStatus, studentName);
//		stus.getStudent2(page, size, date, teacherId, homeworkStatus, studentName);
		return ResponseEntity.status(200).body(res);

	}

}
