package com.studyflow.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.studyflow.entity.Homework;

public interface HomeworkRepository extends JpaRepository<Homework, Integer>{

}
