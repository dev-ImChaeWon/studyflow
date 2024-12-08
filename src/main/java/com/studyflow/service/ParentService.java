package com.studyflow.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.studyflow.dto.ParentDTO;
import com.studyflow.entity.Parent;
import com.studyflow.repository.ParentRepository;

import jakarta.transaction.Transactional;

@Service
public class ParentService {

	ParentRepository parr;
	
	@Autowired
	public ParentService(ParentRepository parr) {
		this.parr = parr;
	}
	
	@Transactional
	public List<ParentDTO> findAllParent() {
		List<Parent> parentList = parr.findAll();
		List<ParentDTO> resList = new ArrayList<>();
		for(Parent p : parentList) {
			ParentDTO pardto = new ParentDTO();
			pardto.setUserId(p.getUserId());
			pardto.setUserName(p.getUserName());
			pardto.setUserRole(p.getUserRole());
			resList.add(pardto);
		}
		return resList;
	}
}
