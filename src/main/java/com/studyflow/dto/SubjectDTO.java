package com.studyflow.dto;

import java.util.List;

public class SubjectDTO {

	private Integer subjectId;
	private String subjectName;
	private TeacherDTO teacher;
	private List<HomeworkDTO> homework;

	public Integer getSubjectId() {
		return subjectId;
	}

	public List<HomeworkDTO> getHomework() {
		return homework;
	}

	public void setHomework(List<HomeworkDTO> homework) {
		this.homework = homework;
	}

	public void setSubjectId(Integer subjectId) {
		this.subjectId = subjectId;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public TeacherDTO getTeacher() {
		return teacher;
	}

	public void setTeacher(TeacherDTO teacher) {
		this.teacher = teacher;
	}

}
