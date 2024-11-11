package com.studyflow.dto;

import java.util.List;

public class StudentDTO {

    private Integer studentId; // 학생 ID
    private String studentName; // 학생 이름
    private List<HomeworkDTO> homework;
    private List<SubjectDTO> subjects; // 과목 목록


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

    public List<SubjectDTO> getSubjects() { // 과목 목록 반환
        return subjects;
    }

    public void setSubjects(List<SubjectDTO> subjects) { // 과목 목록 설정
        this.subjects = subjects;
    }

	public List<HomeworkDTO> getHomework() {
		return homework;
	}

	public void setHomework(List<HomeworkDTO> homework) {
		this.homework = homework;
	}
}
