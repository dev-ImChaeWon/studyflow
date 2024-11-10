package com.studyflow.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.studyflow.dto.ParentDTO;
import com.studyflow.service.UserService;

@Controller
@CrossOrigin(origins = "http://localhost:8088")
public class UserController {
	UserService userService;
	
	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
    @PostMapping("/api/auth/signup")
    public ResponseEntity<String> signUp(@RequestBody ParentDTO parentDTO) {
        String message = userService.signUp(parentDTO);
        return ResponseEntity.ok(message);
    }
    
    @GetMapping("/api/auth/signup")
    public ResponseEntity<Boolean> isDup( @RequestParam(name="id") String id, @RequestParam(name="userRole") Character role){
    	return ResponseEntity.ok(userService.isDup(id, role));
    }
    @PostMapping("/api/auth/signin")
    public ResponseEntity<String> login( @RequestBody ParentDTO parentDTO){
    	
    	return ResponseEntity.ok(userService.signIn(parentDTO));
    }
    
}
