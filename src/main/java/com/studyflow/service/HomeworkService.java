package com.studyflow.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.studyflow.dto.HomeworkDTO;
import com.studyflow.dto.StudentDTO;
import com.studyflow.dto.SubjectDTO;
import com.studyflow.entity.Homework;
import com.studyflow.entity.Student;
import com.studyflow.entity.Subject;
import com.studyflow.repository.HomeworkRepository;
import com.studyflow.repository.StudentRepository;
import com.studyflow.repository.SubjectRepository;

import jakarta.transaction.Transactional;

@Service
public class HomeworkService {
	StudentRepository stur;
	SubjectRepository subr;
	HomeworkRepository homr;
	
	@Autowired
	public HomeworkService(StudentRepository stur, SubjectRepository subr, HomeworkRepository homr) {
		this.stur = stur;
		this.subr = subr;
		this.homr = homr;
	}
	
	// 날짜 필터 테스트용
	public HomeworkDTO getHomeworkByDateRange(LocalDate date) {
		HomeworkDTO homdto = new HomeworkDTO();
		
		List<Homework> homeworkList = homr.findHomeworkInDateRange(2, LocalDateTime.of(date, LocalTime.of(0, 0)),
				LocalDateTime.of(date.plusDays(1), LocalTime.of(0, 0)));
		
		for(Homework h : homeworkList) {
			homdto.getStudent().setStudentId(h.getStudent().getStudentId());
			homdto.getStudent().setStudentName(h.getStudent().getStudentName());
			homdto.setHomeworkId(h.getHomeworkId());
			homdto.setHomeworkDatetime(h.getHomeworkDatetime());
		}
		
		return homdto;
	}
	
	// 학생 숙제 생성
	@Transactional
	public HomeworkDTO createHomework(HomeworkDTO homeworkDTO) {
		Integer homeworkId = homeworkDTO.getHomeworkId();
		Integer subjectId = homeworkDTO.getSubject().getSubjectId();
		Integer studentId = homeworkDTO.getStudent().getStudentId();
		
	    // 1. 학생과 과목 정보 조회
        Optional<Student> optionalStudent = stur.findById(studentId);
        Optional<Subject> optionalSubject = subr.findById(subjectId);

        // 학생이나 과목이 없으면
        if (!optionalStudent.isPresent() || !optionalSubject.isPresent()) {
            
            return null; 
        }

        Student student = optionalStudent.get();
        Subject subject = optionalSubject.get();

        // 2. Homework 엔티티 생성
        Homework homework = new Homework();
        homework.setHomeworkPage(homeworkDTO.getHomeworkPage());
        homework.setHomeworkDatetime(LocalDateTime.now());  // 현재 날짜로 설정
        homework.setCompletedPage(0);  // 기본적으로 완료된 페이지 수는 0
        homework.setStudent(student);  // 학생 정보 설정
        homework.setSubject(subject);  // 과목 정보 설정

        // 3. Homework 저장
        Homework savedHomework = homr.save(homework);

        // 4. HomeworkDTO로 변환하여 반환
        HomeworkDTO createdHomeworkDTO = new HomeworkDTO();
        createdHomeworkDTO.setHomeworkId(savedHomework.getHomeworkId());
        createdHomeworkDTO.setHomeworkPage(savedHomework.getHomeworkPage());
        createdHomeworkDTO.setHomeworkDatetime(savedHomework.getHomeworkDatetime());
        createdHomeworkDTO.setCompletedPage(savedHomework.getCompletedPage());
        createdHomeworkDTO.setComment(savedHomework.getComment());
        createdHomeworkDTO.setCompleteDatetime(savedHomework.getCompleteDatetime());
        
        // 학생과 과목 정보를 DTO 형식으로 포함
        createdHomeworkDTO.setStudent(new StudentDTO());
        createdHomeworkDTO.setSubject(new SubjectDTO());

        return createdHomeworkDTO;
 
	}
	
	
//	// 학생 숙제 생성
//	@Transactional
//	public HomeworkDTO createHomework(HomeworkDTO homeworkDTO) {
//		Integer homeworkId = homeworkDTO.getHomeworkId();
//		
//		// 숙제 정보 조회
//		Optional<Homework> optionalHomework = homr.findById(homeworkId);
//		Homework homework;
//		
//		if (!optionalHomework.isPresent()) {
//			// 만약 optionalHomework가 비어 있으면 (즉, 해당 homeworkId의 숙제가 없으면)
//		    // 새로운 숙제를 생성
//			homework = new Homework();
//			homework.setHomeworkId(homeworkId);
//			homework.getStudent().setStudentId(homeworkId);
//			homework.getSubject().setSubjectId(homeworkId);
//			
//		}
//		
//		return null;
//		
//	}
	
}
