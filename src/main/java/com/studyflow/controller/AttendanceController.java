package com.studyflow.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.studyflow.dto.AttendanceDTO;
import com.studyflow.service.AttendanceService;

@RestController
@CrossOrigin(origins = "*")
public class AttendanceController {
	AttendanceService atts;

	@Autowired
	public AttendanceController(AttendanceService atts) {
		this.atts = atts;
	}

	// 학생ID로 출석 가져오는 API
	@GetMapping("/api/attendance/{studentId}")
	public ResponseEntity<List<AttendanceDTO>> getAttendance(@PathVariable("studentId") Integer studentId) {
		System.out.println("Request received for studentId: " + studentId); // 디버그 로그
		List<AttendanceDTO> res = atts.getAttendance(studentId);
		return ResponseEntity.status(200).body(res);
	}

	// 출결 수정(생성) API
	@PostMapping("/api/attendance-update")
	public ResponseEntity<AttendanceDTO> updateAttendance(@RequestBody AttendanceDTO attendanceDTO) {
		Date attendanceDate;

		try {
			// 날짜 포맷 처리
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String dateString = sdf.format(attendanceDTO.getAttendanceDate()); // Date를 String으로 변환
			attendanceDate = sdf.parse(dateString); // 문자열을 Date로 변환
			attendanceDTO.setAttendanceDate(attendanceDate); // DTO에 날짜 설정
		} catch (ParseException e) {
			// 예외 처리: 잘못된 날짜 포맷
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); // 400 BAD REQUEST
		}

		// 출결 업데이트
		AttendanceDTO updatedAttendanceDTO = atts.updateAttendance(attendanceDTO);
		return ResponseEntity.status(201).body(atts.updateAttendance(attendanceDTO));
	}

	@DeleteMapping("/api/attendance-delete/{studentId}/{attendanceDate}")
	public ResponseEntity<String> deleteAttendance(@PathVariable("studentId") Integer studentId,
			@PathVariable("attendanceDate") String attendanceDateStr) {
		try {
			// 날짜 포맷 처리
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date attendanceDate = sdf.parse(attendanceDateStr);

			atts.deleteAttendance(studentId, attendanceDate);
			return ResponseEntity.ok("출결 삭제 성공");

		} catch (ParseException e) {
			// 예외 처리: 잘못된 날짜 포맷
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); // 400 BAD REQUEST
		}
	}
}
