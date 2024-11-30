package com.studyflow.service;

import java.util.HashMap;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.studyflow.dto.ParentDTO;
import com.studyflow.dto.UserDTO;
import com.studyflow.entity.Parent;
import com.studyflow.entity.Teacher;
import com.studyflow.repository.ParentRepository;
import com.studyflow.repository.TeacherRepository;
import com.studyflow.util.JwtUtil;

@Service
public class UserService {

    private final ParentRepository parentRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final TeacherRepository teacherRepository;

    
    public UserService(ParentRepository parentRepository, BCryptPasswordEncoder passwordEncoder,
			TeacherRepository teacherRepository) {
		super();
		this.parentRepository = parentRepository;
		this.passwordEncoder = passwordEncoder;
		this.teacherRepository = teacherRepository;
	}
    public HashMap<String, String> signIn(UserDTO userDTO) {
        // 로그인 로직
        // 예시로 부모 id와 비밀번호를 간단히 확인
    	HashMap<String, String> responseBody = new HashMap<>();
    	
    	if(userDTO.getUserRole() == 'P') {
    		Optional<Parent> optPa =  parentRepository.findById(userDTO.getUserId());
    		if(optPa.isEmpty()) {
    			return null;
    		}
    		Parent p = optPa.get();
    		if(p.getUserId().equals(userDTO.getUserId()) && passwordEncoder.matches(userDTO.getUserPassword(), p.getUserPassword())) {   
    			responseBody.put("authToken", JwtUtil.generateToken(userDTO.getUserId(), userDTO.getUserRole()));
    			responseBody.put("userRole", userDTO.getUserRole() + "");
    			responseBody.put("userId", userDTO.getUserId());
    			return responseBody;
    		}
    	}else if(userDTO.getUserRole() != 'P') {
    		Optional<Teacher> optTe =  teacherRepository.findById(userDTO.getUserId());
    		if(optTe.isEmpty()) {
    			return null;
    		}
    		Teacher t = optTe.get();
    		if(t.getUserId().equals(userDTO.getUserId()) && passwordEncoder.matches(userDTO.getUserPassword(), t.getUserPassword())) {    			
    			responseBody.put("authToken", JwtUtil.generateToken(userDTO.getUserId(), userDTO.getUserRole()));
    			responseBody.put("userRole", userDTO.getUserRole() + "");
    			responseBody.put("userId", userDTO.getUserId());
    			return responseBody;
    		}
    	}
        // 로그인 실패 시
        return null;
    }
    
	public boolean isDup(String id, Character role) {
    	if(role == 'P') {
    		return parentRepository.findById(id).isPresent();
    	}
    	
    	if(role == 'T' || role == 'M') {
    		return teacherRepository.findById(id).isPresent();
    	}
    	
    	return false;
    }
    public String signUp(ParentDTO parentDTO) {
        // 비밀번호 암호화
        String encryptedPassword = passwordEncoder.encode(parentDTO.getUserPassword());

        // Parent 엔티티 생성
        Parent parent = new Parent();
        parent.setUserId(parentDTO.getUserId());
        parent.setUserPassword(encryptedPassword);
        parent.setUserName(parentDTO.getUserName());
        parent.setUserRole(parentDTO.getUserRole());

        // DB에 저장
        parentRepository.save(parent);

        return "회원가입 성공!";
    }
}
