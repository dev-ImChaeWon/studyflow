package com.studyflow.repository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.studyflow.entity.Attendance;
import com.studyflow.entity.AttendanceId;

public interface AttendanceRepository extends JpaRepository<Attendance, AttendanceId> {

	public List<Attendance> findAll();

	@Query("SELECT a FROM Attendance a WHERE a.id.attendanceDate = :attendanceDate")
	List<Attendance> findByAttendanceDate(@Param("attendanceDate") Date attendanceDate);
	
	@Query("SELECT a FROM Attendance a WHERE a.id.studentId = :studentId AND a.id.attendanceDate = :attendanceDate")
    Optional<Attendance> findById(@Param("studentId") Integer studentId, @Param("attendanceDate") java.util.Date attendanceDate);

}
