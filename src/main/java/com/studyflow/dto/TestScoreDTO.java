package com.studyflow.dto;

import java.util.Date;

public class TestScoreDTO {
	private Integer id;

	private StudentDTO student;

	private SubjectDTO subject;

	private Integer score;

	private Date weeklyTestDate;

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

	public SubjectDTO getSubject() {
		return subject;
	}

	public void setSubject(SubjectDTO subject) {
		this.subject = subject;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public Date getWeeklyTestDate() {
		return weeklyTestDate;
	}

	public void setWeeklyTestDate(Date weeklyTestDate) {
		this.weeklyTestDate = weeklyTestDate;
	}

}
