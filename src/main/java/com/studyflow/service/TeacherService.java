package com.studyflow.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.studyflow.dto.TeacherDTO;
import com.studyflow.entity.Teacher;
import com.studyflow.repository.TeacherRepository;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class TeacherService {

	TeacherRepository tear;
	BCryptPasswordEncoder encoder;
	
	@Autowired
	public TeacherService(TeacherRepository tear) {
		this.tear = tear;
		this.encoder = encoder;
	}

//	// 학원에 등록된 모든 선생님을 조회하는 API
//	public List<TeacherDTO> getAllTeacher() {
//		
//		List<Teacher> li = tear.findAll(); // 모든 Teacher 엔터티 조회
//		List<TeacherDTO> res = new ArrayList<>(); // TeacherDTO를 저장할 리스트 생성
//		
//		// 조회한 Teacher 엔티티 리스트를 반복
//		for(Teacher t : li) {
//			TeacherDTO teadto = new TeacherDTO(); // 새 TeacherDTO 인스턴스 생성
//			teadto.setUserId(t.getUserId()); // Teacher의 userId 설정
//			teadto.setUserName(t.getUserName()); // Teacher의 userName 설정
//			
//			res.add(teadto); // DTO를 리스트에 추가
//		}
//		
//		return res; // TeacherDTO 리스트 반환
//	}

	// 조교는 나오면 안 됨. 강사만 나올 수 있도록 코드 수정 ( findAllByUserRole(Character role) )
	// 학원에 등록된 user_role이 T인 선생님을 조회하는 API
	public List<TeacherDTO> getTeacherT(){
		List<Teacher> li = tear.findAllByUserRole('T');
		List<TeacherDTO> res = new ArrayList<>();
		
		for(Teacher t: li) {
			TeacherDTO teadto = new TeacherDTO();
			teadto.setUserId(t.getUserId());
			teadto.setUserName(t.getUserName());
			teadto.setUserRole(t.getUserRole());
			
			res.add(teadto);
		}
		
		return res;
		
	}
	
	public TeacherDTO createTeacher(TeacherDTO t) {
		Optional<Teacher> optT = tear.findById(t.getUserId());
		if(optT.isPresent()) {
			return null;
		}
		
		Teacher teacherEntity = new Teacher();
		teacherEntity.setUserId(t.getUserId());
		teacherEntity.setUserName(t.getUserName());
		teacherEntity.setUserPassword(t.getUserPassword());
		teacherEntity.setUserRole(t.getUserRole());

		Teacher resEntity = tear.save(teacherEntity);
		TeacherDTO resDto = new TeacherDTO();
		resDto.setUserId(resEntity.getUserId());
		resDto.setUserName(resEntity.getUserName());
		resDto.setUserRole(resEntity.getUserRole());
		
		return resDto;
		
		
	}
	
	public TeacherDTO updateTeacher(String userId, TeacherDTO t) {
		Optional<Teacher> optT = tear.findById(userId);
		if(!optT.isPresent()) {
			return null;
		}
		
		Teacher teacherEntity = optT.get();
		
		teacherEntity.setUserId(userId);
		
		if(t.getUserName() != null) {
			teacherEntity.setUserName(t.getUserName());			
		}
		
		if(t.getUserPassword() != null) {
			teacherEntity.setUserPassword(t.getUserPassword());			
		}
		
		if(t.getUserRole() != null) {
			teacherEntity.setUserRole(t.getUserRole());
		}

		Teacher resEntity = tear.save(teacherEntity);
		TeacherDTO resDto = new TeacherDTO();
		resDto.setUserId(resEntity.getUserId());
		resDto.setUserName(resEntity.getUserName());
		resDto.setUserRole(resEntity.getUserRole());
		
		return resDto;
	}
	
	public boolean deleteTeacher(String userId) {
		Optional<Teacher> optT = tear.findById(userId);
		if(!optT.isPresent()) {
			return false;
		}
		
		tear.deleteById(userId);
		
		return true;
	}
	
}
