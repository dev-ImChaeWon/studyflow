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

	// 학원에 등록된 모든 선생님을 조회하는 API
	public List<TeacherDTO> getAllTeacher() {
		
		List<Teacher> li = tear.findAll(); // 모든 Teacher 엔터티 조회
		List<TeacherDTO> res = new ArrayList<>(); // TeacherDTO를 저장할 리스트 생성
		
		// 조회한 Teacher 엔티티 리스트를 반복
		for(Teacher t : li) {
			TeacherDTO teadto = new TeacherDTO(); // 새 TeacherDTO 인스턴스 생성
			teadto.setUserId(t.getUserId()); // Teacher의 userId 설정
			teadto.setUserName(t.getUserName()); // Teacher의 userName 설정
			
			res.add(teadto); // DTO를 리스트에 추가
		}
		
		return res; // TeacherDTO 리스트 반환
	}

	
}
