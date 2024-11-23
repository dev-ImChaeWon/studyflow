package com.studyflow.controller;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.studyflow.dto.AttendanceDTO;
import com.studyflow.dto.StudentDTO;
import com.studyflow.dto.TestScoreDTO;
import com.studyflow.response.PageResponse;
import com.studyflow.service.StudentService;
import com.studyflow.service.TestScoreService;

@RestController
@CrossOrigin(origins = "http://localhost:8088")
public class StudentController {

	StudentService stus;
	TestScoreService tests;

	@Autowired
	public StudentController(StudentService stus, TestScoreService tests) {
		this.stus = stus;
		this.tests = tests;
	}

	// 테스트 점수 생성 API
	@PostMapping("/api/test-create")
	public ResponseEntity<TestScoreDTO> createTestScore(@RequestBody TestScoreDTO testScoreDTO) {
		try {
			// 서비스 계층을 호출하여 점수 생성
			TestScoreDTO createdTestScore = tests.createTestScore(testScoreDTO);

			// 성공적으로 생성된 경우 201 Created 응답
			return ResponseEntity.status(HttpStatus.CREATED).body(createdTestScore);
		} catch (Exception e) {
			// 기타 예기치 않은 예외 발생 시 500 Internal Server Error 응답
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	// 날짜별 출결 여부 조회 API
	@GetMapping("/api/attendance")
	public ResponseEntity<List<AttendanceDTO>> getAttendanceByDate(
			@RequestParam(name = "date", required = true) Date date) {
		List<AttendanceDTO> res = stus.getAttendanceByDate2(date);

		return ResponseEntity.status(200).body(res);
	}

	// 파라미터로 조건에 맞는 학생과 해당 학생의 숙제 목록 조회 API
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

	// 과목 이름으로 학생 불러오는 API
    @GetMapping("/api/student-by-subject")
    public List<StudentDTO> getStudentsBySubject(@RequestParam(name = "subjectId", required = false) Integer subjectId) {
        return stus.getStudentsBySubjectName(subjectId);
    }

	// 학생 추가하는 API
	@PostMapping("api/student")
	public ResponseEntity<StudentDTO> createStudent(@RequestBody StudentDTO student) {
		return ResponseEntity.ok(stus.createStudent(student));
	}

	// 학생 수정하는 API
	@PutMapping("/api/student/{studentId}")
	public ResponseEntity<StudentDTO> updateStudent(@PathVariable(name = "studentId") int studentId,
			@RequestBody StudentDTO student) {
		return ResponseEntity.ok(stus.updateStudent(studentId, student));
	}

}
