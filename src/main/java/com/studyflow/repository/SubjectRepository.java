package com.studyflow.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.studyflow.entity.Subject;

public interface SubjectRepository extends JpaRepository<Subject, Integer>{

	public List<Subject> findAll();
	
}
