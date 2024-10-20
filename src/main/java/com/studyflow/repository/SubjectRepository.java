package com.studyflow.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.studyflow.entity.Subject;

public interface SubjectRepository extends JpaRepository<Subject, Integer>{

}
