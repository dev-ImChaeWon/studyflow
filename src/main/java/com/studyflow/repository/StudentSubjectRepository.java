package com.studyflow.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.studyflow.entity.StudentSubject;
import com.studyflow.entity.Subject;

public interface StudentSubjectRepository extends JpaRepository<StudentSubject, Integer> {

	@Query("SELECT ss.subject FROM StudentSubject ss WHERE ss.student.studentId = :studentId")
	public List<Subject> findAllSubjectsByStudentId(@Param("studentId") Integer studentId);

	@Query("SELECT ss.subject FROM StudentSubject ss WHERE ss.student.studentId = :studentId")
	List<Subject> findSubjectsByStudentId(@Param("studentId") Integer studentId);

	List<StudentSubject> findBySubject_subjectId(int subjectId);

	void deleteBySubject_subjectId(int subjectId);

	public Optional<StudentSubject> findByStudent_studentIdAndSubject_subjectId(Integer studentId, Integer subjectId);
	
	List<StudentSubject> findAllByStudent_studentId(Integer studentId);
}
