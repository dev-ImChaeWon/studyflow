package com.studyflow.entity;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Attendance {

	@Id
	private Integer id;

	private String isAttend;

	private Date attendanceDate;

	@ManyToOne
	@JoinColumn(name = "student_id", referencedColumnName = "student_id", insertable = false, updatable = false)
	private Student student;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getIsAttend() {
		return isAttend;
	}

	public void setIsAttend(String isAttend) {
		this.isAttend = isAttend;
	}

	public Date getAttendanceDate() {
		return attendanceDate;
	}

	public void setAttendanceDate(Date attendanceDate) {
		this.attendanceDate = attendanceDate;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

}
