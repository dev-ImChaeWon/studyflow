package com.studyflow.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.studyflow.dto.HomeworkDTO;
import com.studyflow.entity.Homework;
import com.studyflow.repository.HomeworkRepository;
import com.studyflow.repository.StudentRepository;
import com.studyflow.repository.SubjectRepository;

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
}
