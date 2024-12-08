package com.studyflow.entity;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Embeddable;

@Embeddable
public class BillId implements Serializable {

	private Integer billId;

	public Integer getBillId() {
		return billId;
	}

	public void setBillId(Integer billId) {
		this.billId = billId;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		BillId that = (BillId) obj;
		return Objects.equals(billId, that.billId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(billId);
	}
}
