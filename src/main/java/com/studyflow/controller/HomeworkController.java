package com.studyflow.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.studyflow.dto.HomeworkDTO;
import com.studyflow.dto.StudentDTO;
import com.studyflow.service.HomeworkService;
import com.studyflow.service.StudentService;

//@Controller
@RestController
@CrossOrigin(origins = "http://localhost:8088")
public class HomeworkController {

	StudentService stus;
	HomeworkService homs;

	@Autowired
	public HomeworkController(StudentService stus, HomeworkService homs) {
		this.stus = stus;
		this.homs = homs;
	}
	
	// 학생 숙제 수정 API
    @PostMapping("/api/homework-update")
    public ResponseEntity<HomeworkDTO> updateHomework(@RequestBody HomeworkDTO homeworkDTO) {
        return ResponseEntity.ok(homs.updateHomework(homeworkDTO));
    }
	
	// 학생 숙제 생성 API
    @PostMapping("/api/homework-create")
    public ResponseEntity<HomeworkDTO> createHomework(@RequestBody HomeworkDTO homeworkDTO) {
        HomeworkDTO createdHomeworkDTO = homs.createHomework(homeworkDTO);
        
        if (createdHomeworkDTO == null) {
            return ResponseEntity.badRequest().body(null); // 잘못된 요청 처리 (학생이나 과목이 존재하지 않을 때)
        }

        return ResponseEntity.ok(createdHomeworkDTO); // 성공적으로 생성된 숙제 반환
    }
   
    
	// id로 해당 학생 전체 숙제 정보를 조회하는 API
	@GetMapping("/api/student-homework/{id}")
	public ResponseEntity<StudentDTO> getHomeworkById(@PathVariable("id") int id) {
		return ResponseEntity.status(200).body(stus.getHomeworkById(id));
	}
	
	// id와 date로 해당 날짜와 해당 학생 숙제 정보 조회 API
	@GetMapping("/api/student-homework")
	public ResponseEntity<StudentDTO> getHomeworkByIdAndDate(
			@RequestParam(name = "id", required = false) int id,
			@RequestParam(name = "date", required = true) LocalDate date) {
		return ResponseEntity.status(200).body(stus.getHomeworkByIdAndDate(id, date));
	}
	
	// 날짜 필터 테스트용 API
	@GetMapping("/api/get-homework")
	public ResponseEntity<HomeworkDTO> getHomeworkByDateRange(
			@RequestParam(name = "date", required=true) LocalDate date){
		
		return ResponseEntity.status(200).body(homs.getHomeworkByDateRange(date));
	}

}
