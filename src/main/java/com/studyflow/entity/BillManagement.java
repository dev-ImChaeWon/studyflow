package com.studyflow.entity;

import java.util.Date;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class BillManagement {

	@EmbeddedId
	private BillId id;

	private boolean isPay;

	private Date payDate;

	private Integer pay;

	public BillId getId() {
		return id;
	}

	public void setId(BillId id) {
		this.id = id;
	}

	public boolean isPay() {
		return isPay;
	}

	public void setPay(boolean isPay) {
		this.isPay = isPay;
	}

	public Date getPayDate() {
		return payDate;
	}

	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}

	public Integer getPay() {
		return pay;
	}

	public void setPay(Integer pay) {
		this.pay = pay;
	}
	
	public Integer getStudentSubjectId() {
		return id.getStudentSubjectId();
	}

}
