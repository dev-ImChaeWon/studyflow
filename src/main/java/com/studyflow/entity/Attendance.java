package com.studyflow.entity;

import java.util.Date;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;

@Entity
public class Attendance {

	@EmbeddedId
	private AttendanceId id;

	private String isAttend;

	@ManyToOne
	@MapsId("studentId")
	@JoinColumn(name = "student_id", referencedColumnName = "student_id", insertable = false, updatable = false)
	private Student student;

	public AttendanceId getId() {
		return id;
	}

	public void setId(AttendanceId id) {
		this.id = id;
	}

	public String getIsAttend() {
		return isAttend;
	}

	public void setIsAttend(String isAttend) {
		this.isAttend = isAttend;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

}
