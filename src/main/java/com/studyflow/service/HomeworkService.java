package com.studyflow.service;

import org.springframework.stereotype.Service;

import com.studyflow.repository.StudentRepository;
import com.studyflow.repository.SubjectRepository;

@Service
public class HomeworkService {
	StudentRepository stur;
	SubjectRepository subr;
}
