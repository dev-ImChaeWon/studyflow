package com.studyflow.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.studyflow.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Integer> {

	public Page<Student> findAllBy(Pageable p);

	public Page<Student> findAllByStudentName(String findByStudentPattern, Pageable pr);

//	 @Query("SELECT s FROM Student s "
//	         + "JOIN s.subjects subj "
//	         + "JOIN subj.teacher t "
//	         + "WHERE t.userName LIKE :teacherName")
//	    Page<Student> findAllStudentByTeacher(@Param("teacherName") String teacherName, Pageable pr);
//
//	@Query("SELECT DISTINCT s FROM Student s "
//	         + "JOIN s.subjects subj "
//	         + "JOIN subj.teacher t "
//	         + "WHERE t.userName LIKE :teacherName "
//	         + "AND s.studentName LIKE :studentNamePattern")
//	    Page<Student> findAllStudentByTeacherAndStudent(
//	        @Param("teacherName") String teacherName,
//	        @Param("studentNamePattern") String studentNamePattern,
//	        Pageable pr);
}
