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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.studyflow.dto.StudentSubjectDTO;
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

	// 학생 id로 과목 가져오는 API
	@GetMapping("/api/student-subject")
	public List<SubjectDTO> getSubjectsByStudentId(@RequestParam Integer studentId) {
		return subs.getSubjectsByStudentId(studentId);
	}
	
	// 과목 추가하는 API
	@PostMapping("/api/subject")
	public ResponseEntity<SubjectDTO> createSubject(@RequestBody SubjectDTO subject){
		return ResponseEntity.ok(subs.createSubject(subject));
	}
	
	// 과목 id로 과목 삭제하는 API
	@DeleteMapping("/api/subject/{subjectId}")
	public ResponseEntity<String> deleteSubject(@PathVariable(name="subjectId") int subjectId){
		return ResponseEntity.ok(subs.deleteSubject(subjectId) ? "성공" : "실패");
	}
	
	// 학생-과목 추가하는 API
	@PostMapping("/api/student-subject")
	public ResponseEntity<StudentSubjectDTO> createStudentSubject(@RequestBody StudentSubjectDTO studentSubject){
		return ResponseEntity.ok(subs.createStudentSubject(studentSubject));
	}
	
}
