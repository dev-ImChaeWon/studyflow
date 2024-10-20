package com.studyflow.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.studyflow.repository.TeacherRepository;

@Service
public class TeacherService {

	TeacherRepository tear;
	
	@Autowired
	public TeacherService(TeacherRepository tear) {
		this.tear = tear;
	}
}
