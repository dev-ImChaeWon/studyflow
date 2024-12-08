package com.studyflow.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.studyflow.dto.ParentDTO;
import com.studyflow.service.ParentService;

@RestController
public class ParentController {

	ParentService pars;

	@Autowired
	public ParentController(ParentService pars) {
		this.pars = pars;
	}

	@GetMapping("/api/parent")
	public ResponseEntity<List<ParentDTO>> getParent() {
		List<ParentDTO> res = pars.findAllParent();
		return ResponseEntity.status(200).body(res);
	}
}
