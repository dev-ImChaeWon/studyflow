package com.studyflow.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.studyflow.entity.TestScore;

public interface TestScoreRepository extends JpaRepository<TestScore, Integer>{

	Optional<TestScore> findByStudent_studentIdAndSubject_subjectId(int studentId, int subjectId);
}
