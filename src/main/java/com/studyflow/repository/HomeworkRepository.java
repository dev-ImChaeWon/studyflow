package com.studyflow.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.studyflow.entity.Homework;

import jakarta.transaction.Transactional;

public interface HomeworkRepository extends JpaRepository<Homework, Integer> {

	// 날짜 필터 테스트용 쿼리
	@Query("SELECT h FROM Homework h WHERE h.student.studentId = :studentId AND h.homeworkDatetime >= :startDate AND h.homeworkDatetime < :endDate")
	List<Homework> findHomeworkInDateRange(@Param("studentId") int studentId,
			@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

	@Transactional
	public void deleteBySubject_subjectId(Integer subjectId);
	
}
