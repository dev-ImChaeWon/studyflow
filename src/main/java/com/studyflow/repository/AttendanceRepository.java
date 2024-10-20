package com.studyflow.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.studyflow.entity.Attendance;

public interface AttendanceRepository extends JpaRepository<Attendance, Integer>{

}
