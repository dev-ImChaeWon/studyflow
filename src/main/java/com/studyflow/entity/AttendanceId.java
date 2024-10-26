package com.studyflow.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import jakarta.persistence.Column;

public class AttendanceId implements Serializable {

	@Column(name = "student_id")
	private Integer studentId;

	@Column(name = "attendance_date")
	private Date attendanceDate;

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof AttendanceId) {
			AttendanceId tmp = (AttendanceId) obj;
			return this.studentId == tmp.studentId && this.attendanceDate.equals(tmp.attendanceDate);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.studentId, this.attendanceDate);
	}

	public Integer getStudentId() {
		return studentId;
	}

	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
	}

	public Date getAttendanceDate() {
		return attendanceDate;
	}

	public void setAttendanceDate(Date attendanceDate) {
		this.attendanceDate = attendanceDate;
	}

}
