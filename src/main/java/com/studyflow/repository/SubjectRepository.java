package com.studyflow.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.studyflow.entity.Subject;

public interface SubjectRepository extends JpaRepository<Subject, Integer> {

	public List<Subject> findAll();

	@Query("SELECT s FROM Subject s WHERE s.teacher.userId = :userId")
	public List<Subject> findAllByTeacher_UserId(@Param("userId") String userId);

	Optional<Subject> findBySubjectName(String subjectName);

	@Query("SELECT ss.subject FROM StudentSubject ss WHERE ss.student.studentId = :studentId")
	List<Subject> findSubjectsByStudentId(@Param("studentId") Integer studentId);
}
