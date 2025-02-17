package com.studyflow.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.studyflow.dto.TeacherDTO;
import com.studyflow.service.TeacherService;

@Controller
@CrossOrigin(origins = "http://localhost:8088")
public class TeacherController {

	TeacherService teas;

	@Autowired
	public TeacherController(TeacherService teas) {
		this.teas = teas;
	}

	// 학원에 등록된 user_role이 T인 선생님을 조회하는 API
	@GetMapping("/api/teacher")
	public ResponseEntity<List<TeacherDTO>> getTeacherT() { // List<TeacherDTO> 타입으로
		List<TeacherDTO> res = teas.getTeacherT();

		return ResponseEntity.status(200).body(res);
	}

	// 선생님 추가 API
	@PostMapping("/api/teacher")
	public ResponseEntity<TeacherDTO> createTeacher(@RequestBody TeacherDTO teacher) {
		return ResponseEntity.ok(teas.createTeacher(teacher));
	}

	// 선생님 과목 수정 API
	@PutMapping("/api/teacher/{userId}")
	public ResponseEntity<TeacherDTO> updateTeacher(@PathVariable(name = "userId") String userId,
			@RequestBody TeacherDTO teacher) {
		return ResponseEntity.ok(teas.updateTeacher(userId, teacher));
	}

	// 선생님 삭제 API
	@DeleteMapping("/api/teacher/{userId}")
	public ResponseEntity<String> deleteTeacher(@PathVariable(name = "userId") String userId) {
		return ResponseEntity.ok(teas.deleteTeacher(userId) ? "성공" : "실패");
	}
}
