package com.studyflow.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.studyflow.entity.Attendance;
import com.studyflow.entity.AttendanceId;

public interface AttendanceRepository extends JpaRepository<Attendance, AttendanceId>{

	public List<Attendance> findAll();

	public List<Attendance> findByAttendanceDate();
}
