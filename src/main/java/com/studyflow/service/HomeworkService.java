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

	// 학생 숙제 수정 (완료페이지, 코멘트 수정)
	@Transactional
	public HomeworkDTO updateHomework(HomeworkDTO homeworkDTO) {
		Integer homeworkId = homeworkDTO.getHomeworkId();
		String comment = homeworkDTO.getComment();
		Integer completedPage = homeworkDTO.getCompletedPage();
		Integer homeworkPage = homeworkDTO.getHomeworkPage();
		
		// 1. 숙제 조회
        Optional<Homework> homeworkOptional = homr.findById(homeworkId);
        if (!homeworkOptional.isPresent()) {
            // 숙제가 존재하지 않으면 null 반환 (혹은 예외 처리)
            return null;
        }
		
        Homework homework = homeworkOptional.get();
        
        if(comment != null && !comment.equals("")) {
        	homework.setComment(comment);
        }
        
        if(completedPage != null) {
        	homework.setCompletedPage(completedPage);
        }
        
        if(homeworkPage != null) {
        	homework.setHomeworkPage(homeworkPage);
        }
        
        homework.setCompleteDatetime(LocalDateTime.now());
        
        // 3. 숙제 저장 (업데이트)
        Homework updatedHomework = homr.save(homework);

        // 4. HomeworkDTO로 변환하여 반환
        HomeworkDTO updatedHomeworkDTO = new HomeworkDTO();
        updatedHomeworkDTO.setHomeworkId(updatedHomework.getHomeworkId());
        updatedHomeworkDTO.setHomeworkPage(updatedHomework.getHomeworkPage());
        updatedHomeworkDTO.setHomeworkDatetime(updatedHomework.getHomeworkDatetime());
        updatedHomeworkDTO.setCompletedPage(updatedHomework.getCompletedPage());
        updatedHomeworkDTO.setComment(updatedHomework.getComment());
        updatedHomeworkDTO.setCompleteDatetime(updatedHomework.getCompleteDatetime());

        return updatedHomeworkDTO;  // 업데이트된 숙제 DTO 반환
		
	}
	
//	// 학생 숙제 수정 (코멘트 수정)
//		@Transactional
//		public HomeworkDTO updateCommentHomework(HomeworkDTO homeworkDTO) {
//			Integer homeworkId = homeworkDTO.getHomeworkId();
//			String comment = homeworkDTO.getComment();
//			
//			// 1. 숙제 조회
//	        Optional<Homework> homeworkOptional = homr.findById(homeworkId);
//	        if (!homeworkOptional.isPresent()) {
//	            // 숙제가 존재하지 않으면 null 반환 (혹은 예외 처리)
//	            return null;
//	        }
//			
//	        Homework homework = homeworkOptional.get();
//	        
//	        // 2. 코멘트 업데이트
//	        homework.setComment(comment);
//	        
//	        // 3. 숙제 저장 (업데이트)
//	        Homework updatedHomework = homr.save(homework);
//
//	        // 4. HomeworkDTO로 변환하여 반환
//	        HomeworkDTO updatedHomeworkDTO = new HomeworkDTO();
//	        updatedHomeworkDTO.setHomeworkId(updatedHomework.getHomeworkId());
//	        updatedHomeworkDTO.setHomeworkPage(updatedHomework.getHomeworkPage());
//	        updatedHomeworkDTO.setHomeworkDatetime(updatedHomework.getHomeworkDatetime());
//	        updatedHomeworkDTO.setCompletedPage(updatedHomework.getCompletedPage());
//	        updatedHomeworkDTO.setComment(updatedHomework.getComment());
//	        updatedHomeworkDTO.setCompleteDatetime(updatedHomework.getCompleteDatetime());
//
//	        return updatedHomeworkDTO;  // 업데이트된 숙제 DTO 반환
//			
//		}
	
//	// 학생 숙제 수정 (숙제 완료페이지 수정)
//	@Transactional
//	public HomeworkDTO updateCompletedHomework(HomeworkDTO homeworkDTO) {
//		Integer homeworkId = homeworkDTO.getHomeworkId();
//		Integer completedPage = homeworkDTO.getCompletedPage();
//		
//		// 1. 숙제 조회
//        Optional<Homework> homeworkOptional = homr.findById(homeworkId);
//        if (!homeworkOptional.isPresent()) {
//            // 숙제가 존재하지 않으면 null 반환 (혹은 예외 처리)
//            return null;
//        }
//		
//        Homework homework = homeworkOptional.get();
//        
//        // 2. 완료된 페이지 수 업데이트
//        if(completedPage != null) {
//        	homework.setCompletedPage(completedPage);        	
//        }
//        
//        // 2. 숙제페이지를 업데이트 하려는 경우
//        if(homeworkDTO.getHomeworkPage() != null) {
//        	homework.setHomeworkPage(homeworkDTO.getHomeworkPage());
//        }
//        
//        // 2. comment 업데이트 하려는 경우
//        if(homeworkDTO.getComment() != null && !homeworkDTO.getComment().equals("")) {
//        	homework.setComment(homeworkDTO.getComment());
//        }
//        
//        homework.setCompleteDatetime(LocalDateTime.now());
//        
//        
//        // 3. 숙제 저장 (업데이트)
//        Homework updatedHomework = homr.save(homework);
//
//        // 4. HomeworkDTO로 변환하여 반환
//        HomeworkDTO updatedHomeworkDTO = new HomeworkDTO();
//        updatedHomeworkDTO.setHomeworkId(updatedHomework.getHomeworkId());
//        updatedHomeworkDTO.setHomeworkPage(updatedHomework.getHomeworkPage());
//        updatedHomeworkDTO.setHomeworkDatetime(updatedHomework.getHomeworkDatetime());
//        updatedHomeworkDTO.setCompletedPage(updatedHomework.getCompletedPage());
//        updatedHomeworkDTO.setComment(updatedHomework.getComment());
//        updatedHomeworkDTO.setCompleteDatetime(updatedHomework.getCompleteDatetime());
//
//        return updatedHomeworkDTO;  // 업데이트된 숙제 DTO 반환
//		
//	}

	
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
}