package com.studyflow.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.studyflow.entity.BillId;
import com.studyflow.entity.BillManagement;

public interface BillManagementRepository extends JpaRepository<BillManagement, BillId>{
	
}
