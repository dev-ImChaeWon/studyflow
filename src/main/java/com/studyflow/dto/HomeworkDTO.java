package com.studyflow.dto;

import java.time.LocalDateTime;

import com.studyflow.entity.Student;
import com.studyflow.entity.Subject;

public class HomeworkDTO {

	private Integer homeworkId;

	private Subject subject = new Subject();

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
