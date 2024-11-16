package com.studyflow.service;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.studyflow.dto.StudentDTO;
import com.studyflow.dto.SubjectDTO;
import com.studyflow.dto.TestScoreDTO;
import com.studyflow.entity.Student;
import com.studyflow.entity.Subject;
import com.studyflow.entity.TestScore;
import com.studyflow.repository.StudentRepository;
import com.studyflow.repository.SubjectRepository;
import com.studyflow.repository.TestScoreRepository;

@Service
public class TestScoreService {
	StudentRepository stur;
	SubjectRepository subr;
	TestScoreRepository testr;

	@Autowired
	public TestScoreService(StudentRepository stur, SubjectRepository subr, TestScoreRepository testr) {
		this.stur = stur;
		this.subr = subr;
		this.testr = testr;
	}

	// 테스트 점수 생성 메서드
	public TestScoreDTO createTestScore(TestScoreDTO testScoreDTO) {
		// TestScoreDTO에서 TestScore 엔티티로 변환
		TestScore testScoreEntity = new TestScore();
		testScoreEntity.setScore(testScoreDTO.getScore());
		// 날짜는 Date 타입의 현재 시각으로 설정
		Date today = new Date();
		testScoreEntity.setWeeklyTestDate(today);

		// 학생과 과목을 객체로 설정
		StudentDTO studentDTO = testScoreDTO.getStudent();
		SubjectDTO subjectDTO = testScoreDTO.getSubject();

		if (studentDTO != null && subjectDTO != null) {
			Student student = new Student();
			student.setStudentId(studentDTO.getStudentId());

			Subject subject = new Subject();
			subject.setSubjectId(subjectDTO.getSubjectId());

			testScoreEntity.setStudent(student);
			testScoreEntity.setSubject(subject);
		}

		// 데이터베이스에 저장
		TestScore savedTestScore = testr.save(testScoreEntity);

		// 저장된 TestScore 엔티티를 TestScoreDTO로 변환하여 반환
		TestScoreDTO resultDTO = new TestScoreDTO();
		resultDTO.setId(savedTestScore.getId());
		resultDTO.setScore(savedTestScore.getScore());
		resultDTO.setWeeklyTestDate(savedTestScore.getWeeklyTestDate());

		// student와 subject 정보도 변환해서 포함
		if (savedTestScore.getStudent() != null) {
			resultDTO.setStudent(convertToStudentDTO(savedTestScore.getStudent()));
		}
		if (savedTestScore.getSubject() != null) {
			resultDTO.setSubject(convertToSubjectDTO(savedTestScore.getSubject()));
		}

		return resultDTO;
	}

	// Student 엔티티를 StudentDTO로 변환
	private StudentDTO convertToStudentDTO(Student student) {
		StudentDTO studentDTO = new StudentDTO();
		studentDTO.setStudentId(student.getStudentId());
		studentDTO.setStudentName(student.getStudentName());
		return studentDTO;
	}

	// Subject 엔티티를 SubjectDTO로 변환
	private SubjectDTO convertToSubjectDTO(Subject subject) {
		SubjectDTO subjectDTO = new SubjectDTO();
		subjectDTO.setSubjectId(subject.getSubjectId());
		subjectDTO.setSubjectName(subject.getSubjectName());
		return subjectDTO;

	}
	
	
}
