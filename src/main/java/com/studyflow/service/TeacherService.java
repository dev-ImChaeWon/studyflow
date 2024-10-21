package com.studyflow.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.studyflow.dto.TeacherDTO;
import com.studyflow.entity.Teacher;
import com.studyflow.repository.TeacherRepository;

@Service
public class TeacherService {

	TeacherRepository tear;
	
	@Autowired
	public TeacherService(TeacherRepository tear) {
		this.tear = tear;
	}

	public List<TeacherDTO> getAllTeacher() {
		
		List<Teacher> li = tear.findAll();
		List<TeacherDTO> res = new ArrayList<>();
		
		for(Teacher t : li) {
			TeacherDTO teadto = new TeacherDTO();
			teadto.setUserId(t.getUserId());
			teadto.setUserName(t.getUserName());
			
			res.add(teadto);
		}
		
		return res;
	}
	
}
