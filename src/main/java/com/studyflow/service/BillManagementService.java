package com.studyflow.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.studyflow.repository.BillManagementRepository;

@Service
public class BillManagementService {

	BillManagementRepository bilr;
	
	@Autowired
	public BillManagementService(BillManagementRepository bilr) {
		this.bilr = bilr;
	}
}
