package com.studyflow.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.studyflow.dto.HomeworkDTO;
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
		homr.findHomeworkInDateRange(1, null, null)
	}
}
