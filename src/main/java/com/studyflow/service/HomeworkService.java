package com.studyflow.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.studyflow.repository.StudentRepository;
import com.studyflow.repository.SubjectRepository;

@Service
public class HomeworkService {
	StudentRepository stur;
	SubjectRepository subr;
	
	@Autowired
	public HomeworkService(StudentRepository stur, SubjectRepository subr) {
		this.stur = stur;
		this.subr = subr;
	}
	
	
}
