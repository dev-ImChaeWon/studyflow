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
	
	// 강좌 개설 시 과목 만드는 메소드
	public void createSubject(SubjectDTO s) {
		Subject subjectEntity = new Subject();
		subjectEntity.setSubjectName(s.getSubjectName());
		
		subjectEntity.setTeacher(null);
		
	}
	
	// 교사 id로 과목 가져오는 메소드
	public List<SubjectDTO> getSubjectByTeacherId(String userId){
		List<Subject> li = subr.findAllByTeacher_UserId(userId);
		List<SubjectDTO> res = new ArrayList<>();
		
		for(Subject s : li) {
			SubjectDTO subdto = new SubjectDTO();
			subdto.setSubjectId(s.getSubjectId());
			subdto.setSubjectName(s.getSubjectName());
			
			res.add(subdto);
		}
		
		return res;
	}
	
}
