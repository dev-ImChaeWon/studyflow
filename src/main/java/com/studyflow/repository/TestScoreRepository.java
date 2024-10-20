package com.studyflow.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.studyflow.entity.TestScore;

public interface TestScoreRepository extends JpaRepository<TestScore, Integer>{

}
