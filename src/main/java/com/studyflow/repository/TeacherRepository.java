package com.studyflow.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.studyflow.entity.Teacher;

public interface TeacherRepository extends JpaRepository<Teacher, String>{

}
