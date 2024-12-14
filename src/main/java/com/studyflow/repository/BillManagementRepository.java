package com.studyflow.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.studyflow.entity.BillManagement;

public interface BillManagementRepository extends JpaRepository<BillManagement, Integer>{

	void deleteByStudentSubject_Subject_subjectId(int subjectId);
}
