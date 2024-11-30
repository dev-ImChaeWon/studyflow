package com.studyflow.dto;

import java.util.Date;

public class BillManagementDTO {

	private Integer studentSubjectId;

	private boolean isPay;

	private Date date;

	private Integer pay;

	protected Integer getStudentSubjectId() {
		return studentSubjectId;
	}

	protected void setStudentSubjectId(Integer studentSubjectId) {
		this.studentSubjectId = studentSubjectId;
	}

	protected boolean isPay() {
		return isPay;
	}

	protected void setPay(boolean isPay) {
		this.isPay = isPay;
	}

	protected Date getDate() {
		return date;
	}

	protected void setDate(Date date) {
		this.date = date;
	}

	protected Integer getPay() {
		return pay;
	}

	protected void setPay(Integer pay) {
		this.pay = pay;
	}

}
