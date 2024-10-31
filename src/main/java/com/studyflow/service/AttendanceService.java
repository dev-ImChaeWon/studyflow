package com.studyflow.service;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.studyflow.dto.AttendanceDTO;
import com.studyflow.entity.Attendance;
import com.studyflow.entity.AttendanceId;
import com.studyflow.entity.Student;
import com.studyflow.repository.AttendanceRepository;
import com.studyflow.repository.StudentRepository;

import jakarta.transaction.Transactional;

@Service
public class AttendanceService {
	AttendanceRepository attr;
	StudentRepository stur;
	
	@Autowired
	public AttendanceService(AttendanceRepository attr, StudentRepository stur) {
		this.attr = attr;
		this.stur = stur;
	}
	
	// 출결 수정 및 생성 메서드
	@Transactional
	public AttendanceDTO updateAttendance(AttendanceDTO attendanceDTO) {
        Integer studentId = attendanceDTO.getStudentId();
        Date attendanceDate = attendanceDTO.getAttendanceDate();

        // AttendanceId를 통해 Attendance 엔티티를 찾음
        Optional<Attendance> optionalAttendance = attr.findById(studentId, attendanceDate);
        Attendance attendance;

    	// 학생 정보 조회
        Optional<Student> optionalStudent = stur.findById(studentId);
        if (!optionalStudent.isPresent()) {
//            throw new RuntimeException("Student not found with ID: " + studentId);
        	return null;
        }
        
        if (optionalAttendance.isPresent()) {
            // 존재할 경우 업데이트
            attendance = optionalAttendance.get();
            attendance.setIsAttend(attendanceDTO.getIsAttend());
        } else {
            // 존재하지 않을 경우 새로운 Attendance 엔티티 생성
            attendance = new Attendance();
            attendance.setId(new AttendanceId());
            attendance.setIsAttend(attendanceDTO.getIsAttend());
            
            attendance.getId().setAttendanceDate(attendanceDate);
            
        }
        
        // 학생 객체 설정
        Student student = optionalStudent.get(); // null이 아니므로 안전하게 사용할 수 있음
        attendance.setStudent(student);

        // Attendance 엔티티 저장
        attr.save(attendance);

        // DTO로 변환하여 반환
        attendanceDTO.setAttendanceDate(attendance.getAttendanceDate());
        return attendanceDTO;
    }

//	// 출결 삭제 메서드
//	@Transactional
//	public void deleteAttendance(int id, Date date) {
//		// id가 존재하는 attendanceId인지 검사
//		Optional<Attendance> opAttendance = attr.findById(id, date);
//		if(!opAttendance.isPresent()) {
//			//해당 attendanceId가 존재하지 않음.
//			return;
//		}
//		
//		AttendanceId attendanceId = new AttendanceId();
//        attendanceId.setStudentId(id);
//        attendanceId.setAttendanceDate(date);
//
//        attr.deleteById(attendanceId);
//	}
	
	@Transactional
    public void deleteAttendance(int studentId, Date attendanceDate) {
        // AttendanceId 생성
        AttendanceId attendanceId = new AttendanceId();
        attendanceId.setStudentId(studentId);
        attendanceId.setAttendanceDate(attendanceDate);

        // 해당 출결 정보가 존재하는지 검사
        Optional<Attendance> opAttendance = attr.findById(attendanceId);
        if (!opAttendance.isPresent()) {
            return;
        }

        // 출결 정보 삭제
        attr.deleteById(attendanceId);
    }
}
