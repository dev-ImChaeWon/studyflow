package com.studyflow.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.studyflow.entity.Teacher;

public interface TeacherRepository extends JpaRepository<Teacher, String>{
	
	public List<Teacher> findAllByUserRole(String role);
}
