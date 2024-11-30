package com.studyflow.entity;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;

public class BillId implements Serializable {

	@Column(name = "student_subject_id")
	private Integer studentSubjectId;

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof BillId) {
			BillId tmp = (BillId) obj;
			return this.studentSubjectId == tmp.studentSubjectId;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.studentSubjectId);
	}

	public Integer getStudentSubjectId() {
		return studentSubjectId;
	}

	public void setStudentSubjectId(Integer studentSubjectId) {
		this.studentSubjectId = studentSubjectId;
	}

}
