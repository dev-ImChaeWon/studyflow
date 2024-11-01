package com.studyflow.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.studyflow.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Integer> {

	public List<Student> findAll();

	@Query("SELECT s FROM Homework h JOIN h.student s WHERE h.homeworkDatetime = :homeworkDatetime")
	Page<Student> findAllByHomeworkDatetime(@Param("homeworkDatetime") LocalDateTime homeworkDatetime, Pageable pageable);

	public Page<Student> findAllBy(Pageable p);

//	public Page<Student> findAllByStudentName(String findByStudentPattern, Pageable pr);

	// 특정 학생 이름을 포함하는 학생 목록 조회
	@Query("SELECT s FROM Student s WHERE s.studentName LIKE :studentNamePattern")
	Page<Student> findAllByStudentName(@Param("studentNamePattern") String studentNamePattern, Pageable p);

	// 특정 선생님이 담당하는 학생 목록 조회
	@Query("SELECT DISTINCT s FROM Student s " + "JOIN StudentSubject ss ON s.studentId = ss.student.studentId "
			+ "JOIN Subject subj ON ss.subject.subjectId = subj.subjectId " + "JOIN subj.teacher t "
			+ "WHERE t.userName LIKE :teacherName")
	public Page<Student> findAllStudentByTeacher(@Param("teacherName") String teacherName, Pageable p);

	// 특정 선생님이 담당하는 학생 중 특정 학생 이름을 포함하는 학생 목록 조회
	@Query("SELECT DISTINCT s FROM Student s " + "JOIN StudentSubject ss ON s.studentId = ss.student.studentId "
			+ "JOIN Subject subj ON ss.subject.subjectId = subj.subjectId " + "JOIN subj.teacher t "
			+ "WHERE t.userName LIKE :teacherName " + "AND s.studentName LIKE :studentNamePattern")
	public Page<Student> findAllStudentByTeacherAndStudent(@Param("teacherName") String teacherName,
			@Param("studentNamePattern") String studentNamePattern, Pageable p);
	
	
	@Query("SELECT s "
		     + "FROM Student s "
		     + "JOIN StudentSubject ss ON s.studentId = ss.student.studentId "
		     + "JOIN Subject sub ON ss.subject.subjectId = sub.subjectId "
		     + "AND s.studentId IN ( "
		     + "    SELECT h.student.studentId "
		     + "    FROM Homework h "
		     + "    WHERE h.homeworkDatetime >= :startDate "
		     + "    AND h.homeworkDatetime < :endDate "
		     + "    GROUP BY h.student.studentId "
		     + "    HAVING SUM(h.homeworkPage) = SUM(h.completedPage) "
		     + ") "
		     + "AND s.studentName LIKE :studentName")
	public Page<Student> findCompletedStudentWithoutTeacher(
			@Param("startDate") LocalDateTime startDate, 
			@Param("endDate") LocalDateTime endDate,
			@Param("studentName") String studentName, Pageable p);
	
	@Query("SELECT s "
		     + "FROM Student s "
		     + "JOIN StudentSubject ss ON s.studentId = ss.student.studentId "
		     + "JOIN Subject sub ON ss.subject.subjectId = sub.subjectId "
		     + "AND s.studentId IN ( "
		     + "    SELECT h.student.studentId "
		     + "    FROM Homework h "
		     + "    WHERE h.homeworkDatetime >= :startDate "
		     + "    AND h.homeworkDatetime < :endDate "
		     + "    GROUP BY h.student.studentId "
		     + "    HAVING SUM(h.homeworkPage) != SUM(h.completedPage) "
		     + ") "
		     + "AND s.studentName LIKE :studentName")
	public Page<Student> findNotCompletedStudentWithoutTeacher(
			@Param("startDate") LocalDateTime startDate, 
			@Param("endDate") LocalDateTime endDate,
			@Param("studentName") String studentName, Pageable p);
	
	@Query("SELECT s "
		     + "FROM Student s "
//			 + "JOIN Homework h ON s.studentId = h.student.studentId "
		     + "WHERE NOT EXISTS ("
		     + "SELECT h "
		     + "FROM Homework h "
		     + "WHERE h.student.id = s.id "
		     + "AND h.homeworkDatetime >= :startDate "
		     + "AND h.homeworkDatetime < :endDate) "
		     + "AND s.studentName LIKE :studentName")
	public Page<Student> findNoHomeworkStudentWithoutTeacher(
			@Param("startDate") LocalDateTime startDate, 
			@Param("endDate") LocalDateTime endDate,
			@Param("studentName") String studentName, Pageable p);
	
	@Query("SELECT s "
			+ "FROM Student s "
			+ "JOIN StudentSubject ss ON s.studentId = ss.student.studentId "
			+ "JOIN Subject sub ON ss.subject.subjectId = sub.subjectId "
			+ "AND s.studentId IN ( "
			+ "    SELECT h.student.studentId "
			+ "    FROM Homework h "
			+ "    WHERE h.homeworkDatetime >= :startDate "
			+ "    AND h.homeworkDatetime < :endDate "
			+ "    GROUP BY h.student.studentId "
			+ ") "
			+ "AND s.studentName LIKE :studentName")
	public Page<Student> findAllStudentWithoutTeacher(
			@Param("startDate") LocalDateTime startDate, 
			@Param("endDate") LocalDateTime endDate,
			@Param("studentName") String studentName, Pageable p);
	
	@Query("SELECT s "
		     + "FROM Student s "
		     + "JOIN StudentSubject ss ON s.studentId = ss.student.studentId "
		     + "JOIN Subject sub ON ss.subject.subjectId = sub.subjectId "
		     + "WHERE sub.teacher.id = :teacherId "
		     + "AND s.studentId IN ( "
		     + "    SELECT h.student.studentId "
		     + "    FROM Homework h "
		     + "    WHERE h.homeworkDatetime >= :startDate "
		     + "    AND h.homeworkDatetime < :endDate "
		     + "    GROUP BY h.student.studentId "
		     + "    HAVING SUM(h.homeworkPage) = SUM(h.completedPage) "
		     + ") "
		     + "AND s.studentName LIKE :studentName")
	public Page<Student> findCompletedStudentWithTeacher(
			@Param("teacherId") String teacherId,
			@Param("startDate") LocalDateTime startDate, 
			@Param("endDate") LocalDateTime endDate,
			@Param("studentName") String studentName, Pageable p);
	
	@Query("SELECT s "
		     + "FROM Student s "
		     + "JOIN StudentSubject ss ON s.studentId = ss.student.studentId "
		     + "JOIN Subject sub ON ss.subject.subjectId = sub.subjectId "
		     + "WHERE sub.teacher.id = :teacherId "
		     + "AND s.studentId IN ( "
		     + "    SELECT h.student.studentId "
		     + "    FROM Homework h "
		     + "    WHERE h.homeworkDatetime >= :startDate "
		     + "    AND h.homeworkDatetime < :endDate "
		     + "    GROUP BY h.student.studentId "
		     + "    HAVING SUM(h.homeworkPage) != SUM(h.completedPage) "
		     + ") "
		     + "AND s.studentName LIKE :studentName")
	public Page<Student> findNotCompletedStudentWithTeacher(
			@Param("teacherId") String teacherId,
			@Param("startDate") LocalDateTime startDate, 
			@Param("endDate") LocalDateTime endDate,
			@Param("studentName") String studentName, Pageable p);
	
	@Query("SELECT s "
		       + "FROM Student s "
		       + "JOIN StudentSubject ss ON s.studentId = ss.student.studentId "
		       + "JOIN Subject sub ON ss.subject.subjectId = sub.subjectId "
		       + "WHERE sub.teacher.id = :teacherId "
		       + "AND s.studentId NOT IN ( "
		       + "    SELECT h.student.studentId "
		       + "    FROM Homework h "
		       + "    WHERE h.homeworkDatetime >= :startDate "
		       + "    AND h.homeworkDatetime < :endDate "
		       + "    GROUP BY h.student.studentId) "
			   + "AND s.studentName LIKE :studentName")
	public Page<Student> findNoHomeworkStudentWithTeacher(
			@Param("teacherId") String teacherId,
			@Param("startDate") LocalDateTime startDate, 
			@Param("endDate") LocalDateTime endDate,
			@Param("studentName") String studentName, Pageable p);
	
	@Query("SELECT s "
		     + "FROM Student s "
		     + "JOIN StudentSubject ss ON s.studentId = ss.student.studentId "
		     + "JOIN Subject sub ON ss.subject.subjectId = sub.subjectId "
		     + "WHERE sub.teacher.userId = :teacherId "
		     + "AND s.studentId IN ( "
		     + "    SELECT h.student.studentId "
		     + "    FROM Homework h "
		     + "    WHERE h.homeworkDatetime >= :startDate "
		     + "    AND h.homeworkDatetime < :endDate "
		     + "    GROUP BY h.student.studentId) "
		     + "AND s.studentName LIKE :studentName")
	public Page<Student> findAllStudentWithTeacher(
			@Param("teacherId") String teacherId,
			@Param("startDate") LocalDateTime startDate, 
			@Param("endDate") LocalDateTime endDate,
			@Param("studentName") String studentName, Pageable p);
}










