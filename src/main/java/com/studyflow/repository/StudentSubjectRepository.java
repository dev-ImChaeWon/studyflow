package com.studyflow.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.studyflow.entity.StudentSubject;
import com.studyflow.entity.Subject;

public interface StudentSubjectRepository extends JpaRepository<StudentSubject, Integer>{

	@Query("SELECT ss.subject FROM StudentSubject ss WHERE ss.student.studentId = :studentId")
	public List<Subject> findAllSubjectsByStudentId(@Param("studentId") Integer studentId);
}
