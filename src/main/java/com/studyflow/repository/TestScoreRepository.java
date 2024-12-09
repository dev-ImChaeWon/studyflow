package com.studyflow.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.studyflow.entity.TestScore;

public interface TestScoreRepository extends JpaRepository<TestScore, Integer>{

	Optional<TestScore> findByStudent_studentIdAndSubject_subjectId(int studentId, int subjectId);
	
	List<TestScore> findAllByStudent_studentIdAndSubject_subjectId(Integer studentId, Integer subjectId);
	
	// 주간 테스트 날짜로 검색
    @Query("SELECT t FROM TestScore t WHERE t.weeklyTestDate = :weeklyTestDate")
    Page<TestScore> findByWeeklyTestDate(@Param("weeklyTestDate") Date weeklyTestDate, Pageable pageable);

    // 주간 테스트 날짜와 과목 ID로 검색
    @Query("SELECT t FROM TestScore t WHERE t.weeklyTestDate = :weeklyTestDate AND t.subject.subjectId = :subjectId")
    Page<TestScore> findByWeeklyTestDateAndSubjectId(@Param("weeklyTestDate") Date weeklyTestDate, @Param("subjectId") Integer subjectId, Pageable pageable);

    // 주간 테스트 날짜와 학생 ID로 검색
    @Query("SELECT t FROM TestScore t WHERE t.weeklyTestDate = :weeklyTestDate AND t.student.studentId = :studentId")
    Page<TestScore> findByWeeklyTestDateAndStudentId(@Param("weeklyTestDate") Date weeklyTestDate, @Param("studentId") Integer studentId, Pageable pageable);

    // 주간 테스트 날짜, 학생 ID, 과목 ID로 검색
    @Query("SELECT t FROM TestScore t WHERE t.weeklyTestDate = :weeklyTestDate AND t.student.studentId = :studentId AND t.subject.subjectId = :subjectId")
    Page<TestScore> findByWeeklyTestDateAndStudentIdAndSubjectId(@Param("weeklyTestDate") Date weeklyTestDate, @Param("studentId") Integer studentId, @Param("subjectId") Integer subjectId, Pageable pageable);
}
