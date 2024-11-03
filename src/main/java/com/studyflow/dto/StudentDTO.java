package com.studyflow.dto;

import java.util.List;

public class StudentDTO {

<<<<<<< HEAD
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
=======
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
>>>>>>> 0e372e891cdb198d83d3c0f2728a53df3bbfaaf2

	public List<HomeworkDTO> getHomework() {
		return homework;
	}

	public void setHomework(List<HomeworkDTO> homework) {
		this.homework = homework;
	}
}
