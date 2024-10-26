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

//	public Page<Student> findAllByStudentName(String findByStudentPattern, Pageable pr);
	
	// 특정 학생 이름을 포함하는 학생 목록 조회
    @Query("SELECT s FROM Student s WHERE s.studentName LIKE :studentNamePattern")
    Page<Student> findAllByStudentName(@Param("studentNamePattern") String studentNamePattern, Pageable p);

    // 특정 선생님이 담당하는 학생 목록 조회
    @Query("SELECT DISTINCT s FROM Student s " +
           "JOIN StudentSubject ss ON s.studentId = ss.student.studentId " +
           "JOIN Subject subj ON ss.subject.subjectId = subj.subjectId " +
           "JOIN subj.teacher t " +
           "WHERE t.userName LIKE :teacherName")
    public Page<Student> findAllStudentByTeacher(@Param("teacherName") String teacherName, Pageable p);

    // 특정 선생님이 담당하는 학생 중 특정 학생 이름을 포함하는 학생 목록 조회
    @Query("SELECT DISTINCT s FROM Student s " +
           "JOIN StudentSubject ss ON s.studentId = ss.student.studentId " +
           "JOIN Subject subj ON ss.subject.subjectId = subj.subjectId " +
           "JOIN subj.teacher t " +
           "WHERE t.userName LIKE :teacherName " +
           "AND s.studentName LIKE :studentNamePattern")
    public Page<Student> findAllStudentByTeacherAndStudent(
        @Param("teacherName") String teacherName,
        @Param("studentNamePattern") String studentNamePattern,
        Pageable p);
    
}
