package com.studyflow.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.studyflow.entity.Teacher;

public interface TeacherRepository extends JpaRepository<Teacher, String>{
	
	public List<Teacher> findAllByUserRole(Character role);

	public Optional<Teacher> findByUserId(String userId);
}
