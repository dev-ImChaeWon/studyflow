package com.studyflow.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.studyflow.entity.StudentParent;

public interface StudentParentRepository extends JpaRepository<StudentParent, Integer>{

	Optional<StudentParent> findByParent_userId(String parentId);
	
	List<StudentParent> findAllByParent_userId(String parentId);
}
