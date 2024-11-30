package com.studyflow.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.studyflow.service.BillManagementService;

@Controller
@CrossOrigin(origins = "http://localhost:8088")
public class BillManagementController {

	BillManagementService bils;
	
	@Autowired
	public BillManagementController(BillManagementService bils) {
		this.bils = bils;
	}
	
	
}
