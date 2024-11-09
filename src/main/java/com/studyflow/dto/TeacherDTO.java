package com.studyflow.dto;

import java.util.List;

public class TeacherDTO {

	private String userId;
	private String userPassword;
	private Character userRole;
	private String userName;
	private List<SubjectDTO> subject;

	public List<SubjectDTO> getSubject() {
		return subject;
	}

	public void setSubject(List<SubjectDTO> subject) {
		this.subject = subject;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public Character getUserRole() {
		return userRole;
	}

	public void setUserRole(Character userRole) {
		this.userRole = userRole;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
