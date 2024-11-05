package com.studyflow.dto;

import java.util.List;

public class SubjectDTO {

    private Integer subjectId; // 과목 ID
    private String subjectName; // 과목 이름
    private Object teacher; // 교사 정보 (현재 null)
    private List<HomeworkDTO> homework; // 숙제 목록

	private Integer subjectId;
	private String subjectName;
	private TeacherDTO teacher;
	private List<HomeworkDTO> homework;


    public Integer getSubjectId() {
        return subjectId;
    }


    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
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

    public Object getTeacher() {
        return teacher;
    }

    public void setTeacher(Object teacher) {
        this.teacher = teacher;
    }

    public List<HomeworkDTO> getHomework() {
        return homework; // 숙제 목록 반환
    }

    public void setHomework(List<HomeworkDTO> homework) {
        this.homework = homework; // 숙제 목록 설정
    }
}
