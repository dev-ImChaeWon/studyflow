package com.studyflow.dto;

import java.util.List;

public class StudentDTO {

	private Integer studentId;
	private String studentName;
	private List<HomeworkDTO> homework;
	private List<SubjectDTO> subject;

	public List<SubjectDTO> getSubject() {
		return subject;
	}

	public void setSubject(List<SubjectDTO> subject) {
		this.subject = subject;
	}

	public List<HomeworkDTO> getHomework() {
		return homework;
	}

	public void setHomework(List<HomeworkDTO> homework) {
		this.homework = homework;
	}

	public Integer getStudentId() {
		return studentId;
	}

	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

}
