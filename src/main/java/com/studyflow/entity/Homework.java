package com.studyflow.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Homework {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer homeworkId;

	@ManyToOne
	@JoinColumn(name = "subject_id")
	private Subject subject;

	@ManyToOne
	@JoinColumn(name = "student_id", referencedColumnName = "student_id")
	private Student student;

	private Integer homeworkPage;

	private LocalDateTime homeworkDatetime;

	private Integer completedPage;

	private String comment;

	private LocalDateTime completeDatetime;

	public Integer getHomeworkId() {
		return homeworkId;
	}

	public void setHomeworkId(Integer homeworkId) {
		this.homeworkId = homeworkId;
	}

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Integer getHomeworkPage() {
		return homeworkPage;
	}

	public void setHomeworkPage(Integer homeworkPage) {
		this.homeworkPage = homeworkPage;
	}

	public LocalDateTime getHomeworkDatetime() {
		return homeworkDatetime;
	}

	public void setHomeworkDatetime(LocalDateTime homeworkDatetime) {
		this.homeworkDatetime = homeworkDatetime;
	}

	public Integer getCompletedPage() {
		return completedPage;
	}

	public void setCompletedPage(Integer completedPage) {
		this.completedPage = completedPage;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public LocalDateTime getCompleteDatetime() {
		return completeDatetime;
	}

	public void setCompleteDatetime(LocalDateTime completeDatetime) {
		this.completeDatetime = completeDatetime;
	}

}
