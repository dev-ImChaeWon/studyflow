package com.studyflow.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.studyflow.dto.SubjectDTO;
import com.studyflow.entity.Subject;
import com.studyflow.repository.SubjectRepository;

import jakarta.transaction.Transactional;

@Service
public class SubjectService {

	SubjectRepository subr;

	@Autowired
	public SubjectService(SubjectRepository subr) {
		this.subr = subr;
	}
	
	@Transactional
	public List<SubjectDTO> getSubjectName() {
		List<Subject> li = subr.findAll();
		List<SubjectDTO> res = new ArrayList<>();
		
		for(Subject s : li) {
			SubjectDTO subdto = new SubjectDTO();
			subdto.setSubjectName(s.getSubjectName());
			res.add(subdto);
		}
		
		return res;
	}
}
