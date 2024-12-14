package com.studyflow.entity;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;

@Entity
public class BillManagement {

	@Id
	private Integer billId;

	@OneToOne
	@JoinColumn(name = "bill_id", referencedColumnName = "id", insertable = false, updatable = false)
	private StudentSubject studentSubject;

	private boolean isPay;

	private Date payDate;

	private Integer pay;

	public StudentSubject getStudentSubject() {
		return studentSubject;
	}

	public void setStudentSubject(StudentSubject studentSubject) {
		this.studentSubject = studentSubject;
	}

	public boolean getIsPay() {
		return isPay;
	}

	public void setIsPay(boolean isPay) {
		this.isPay = isPay;
	}

	public Date getPayDate() {
		return payDate;
	}

	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}

	public Integer getBillId() {
		return billId;
	}

	public void setBillId(Integer billId) {
		this.billId = billId;
	}

	public Integer getPay() {
		return pay;
	}

	public void setPay(Integer pay) {
		this.pay = pay;
	}

}
