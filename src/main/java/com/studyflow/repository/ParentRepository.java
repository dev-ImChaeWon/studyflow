package com.studyflow.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.studyflow.entity.Parent;

public interface ParentRepository extends JpaRepository<Parent, String>{

}
