package com.studyflow.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.studyflow.entity.Subject;
import com.studyflow.entity.Teacher;

public interface SubjectRepository extends JpaRepository<Subject, Integer>{

	public List<Subject> findAll();
	
	@Query("SELECT s FROM Subject s WHERE s.teacher.userId = :userId")
	public List<Subject> findAllByTeacher_UserId(@Param("userId") String userId);
	
}
