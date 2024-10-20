package com.studyflow.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.studyflow.dto.HomeworkDTO;
import com.studyflow.dto.StudentDTO;
import com.studyflow.entity.Homework;
import com.studyflow.entity.Student;
import com.studyflow.entity.Subject;
import com.studyflow.repository.HomeworkRepository;
import com.studyflow.repository.StudentRepository;

@Service
public class StudentService {

	StudentRepository stur;
	HomeworkRepository homr;

	@Autowired
	public StudentService(StudentRepository stur, HomeworkRepository homr) {
		this.stur = stur;
		this.homr = homr;
	}

//	public StudentDTO getHomeworkById(int id) {
//		Optional<Student> res = stur.findById(id);
//		StudentDTO studto = new StudentDTO();
//		if (res.isPresent()) {
//			Student tmp = res.get();
//
//			studto.setStudentId(tmp.getStudentId());
//			studto.setStudentName(tmp.getStudentName());
//
//			List<HomeworkDTO> homework = new ArrayList<>();
//			HomeworkDTO homdto = new HomeworkDTO();
//			for (Homework hm : tmp.getHomework()) {
//				homdto.setHomeworkId(hm.getHomeworkId());
//				homdto.getSubject().setSubjectId(hm.getSubject().getSubjectId());	// homdto.getSubject() is null 오류 수정중
//				homdto.setHomeworkPage(hm.getHomeworkPage());
//				homdto.setHomeworkDatetime(hm.getHomeworkDatetime());
//				homdto.setCompletedPage(hm.getCompletedPage());
//				homdto.setComment(hm.getComment());
//				homdto.setCompleteDatetime(hm.getCompleteDatetime());
//			}
//			homework.add(homdto);
//			studto.setHomework(homework);
//		}
//		return studto;
//	}
	
	public StudentDTO getHomeworkById(int id) {
	    Optional<Student> res = stur.findById(id);
	    StudentDTO studto = new StudentDTO();
	    if (res.isPresent()) {
	        Student tmp = res.get();

	        studto.setStudentId(tmp.getStudentId());
	        studto.setStudentName(tmp.getStudentName());

	        List<HomeworkDTO> homework = new ArrayList<>();
	        for (Homework hm : tmp.getHomework()) {
	            HomeworkDTO homdto = new HomeworkDTO();
	            homdto.setHomeworkId(hm.getHomeworkId());

	            // Subject 초기화
	            if (hm.getSubject() != null) {
	                Subject subject = new Subject();
	                subject.setSubjectId(hm.getSubject().getSubjectId());
	                homdto.setSubject(subject);
	            }

	            homdto.setHomeworkPage(hm.getHomeworkPage());
	            homdto.setHomeworkDatetime(hm.getHomeworkDatetime());
	            homdto.setCompletedPage(hm.getCompletedPage());
	            homdto.setComment(hm.getComment());
	            homdto.setCompleteDatetime(hm.getCompleteDatetime());

	            homework.add(homdto);
	        }
	        studto.setHomework(homework);
	    }
	    return studto;
	}
}
