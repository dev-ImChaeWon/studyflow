package com.studyflow.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.studyflow.entity.StudentSubject;

public interface StudentSubjectRepository extends JpaRepository<StudentSubject, Integer>{

}
