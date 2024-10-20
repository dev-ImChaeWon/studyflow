package com.studyflow.dto;

public class StudentParentDTO {

	private Integer id;

	private StudentDTO student;

	private ParentDTO parent;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public StudentDTO getStudent() {
		return student;
	}

	public void setStudent(StudentDTO student) {
		this.student = student;
	}

	public ParentDTO getParent() {
		return parent;
	}

	public void setParent(ParentDTO parent) {
		this.parent = parent;
	}

}
