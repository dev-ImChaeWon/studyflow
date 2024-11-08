package com.studyflow.controller;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.studyflow.dto.HomeworkDTO;
import com.studyflow.dto.StudentDTO;
import com.studyflow.entity.Homework;
import com.studyflow.entity.Student;
import com.studyflow.entity.Subject;
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
	
	// 숙제 수정 API (완료된 페이지 수 수정)
    @PostMapping("/api/homework-update")
    public HomeworkDTO updateHomework(@RequestBody HomeworkDTO homeworkDTO) {
        return homs.updateHomework(homeworkDTO);
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
