package com.studyflow.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.studyflow.dto.AttendanceDTO;
import com.studyflow.service.AttendanceService;

@Controller
public class AttendanceController {
	AttendanceService atts;
	
	@Autowired
	public AttendanceController(AttendanceService atts) {
		this.atts = atts;
	}
	
	// 출결 수정(생성) API
	@PostMapping("/api/attendance-update")
    public ResponseEntity<AttendanceDTO> updateAttendance(@RequestBody AttendanceDTO attendanceDTO) {
        AttendanceDTO updatedAttendanceDTO = atts.updateAttendance(attendanceDTO);
        
        return ResponseEntity.status(201).body(atts.updateAttendance(attendanceDTO));
    }
}
