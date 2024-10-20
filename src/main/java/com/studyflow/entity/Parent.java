package com.studyflow.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Parent {

	@Id
	private String userId;
	private String userPassword;
	private Character userRole;
	private String userName;

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

	public char getUserRole() {
		return userRole;
	}

	public void setUserRole(char userRole) {
		this.userRole = userRole;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
