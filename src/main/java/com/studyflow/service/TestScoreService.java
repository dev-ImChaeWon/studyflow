package com.studyflow.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
import com.studyflow.response.PageResponse;

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
	
	// 테스트 점수 조회 메서드
	public PageResponse<TestScoreDTO> getTestScore(int page, int size, int studentId, int subjectId, Date weeklyTestDate) {
	    // 기본 정렬 조건 설정
	    PageRequest pr = PageRequest.of(page - 1, size, Sort.by(Sort.Order.asc("student.studentId")));
	    
	    // weeklyTestDate 기본값 설정
	    if (weeklyTestDate == null) {
	        weeklyTestDate = new Date();
	    }

	    Page<TestScore> res;
	    
	    if (studentId == 0 && subjectId == 0) {
	        res = testr.findByWeeklyTestDate(weeklyTestDate, pr);
	    } else if (studentId == 0) {
	        res = testr.findByWeeklyTestDateAndSubjectId(weeklyTestDate, subjectId, pr);
	    } else if (subjectId == 0) {
	        res = testr.findByWeeklyTestDateAndStudentId(weeklyTestDate, studentId, pr);
	    } else {
	        res = testr.findByWeeklyTestDateAndStudentIdAndSubjectId(weeklyTestDate, studentId, subjectId, pr);
	    }

	    List<TestScoreDTO> li = res.stream()
	        .map(testScore -> {
	            TestScoreDTO dto = new TestScoreDTO();
	            dto.setId(testScore.getId());
	            dto.setScore(testScore.getScore());
	            dto.setWeeklyTestDate(testScore.getWeeklyTestDate());
	            dto.setStudent(convertToStudentDTO(testScore.getStudent()));
	            dto.setSubject(convertToSubjectDTO(testScore.getSubject()));
	            return dto;
	        })
	        .toList();

	    // PageResponse 생성 및 반환
	    PageResponse<TestScoreDTO> testScorePage = new PageResponse<>();
	    testScorePage.setList(li);
	    testScorePage.setCurrentPage(page);
	    testScorePage.setHasNext(page < res.getTotalPages());
	    testScorePage.setHasPrevieous(page > 1);
	    testScorePage.setTotalElements(res.getTotalElements());
	    testScorePage.setTotalPages(res.getTotalPages());
	    
	    return testScorePage;
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

	// 테스트 점수 수정 메서드
	public TestScoreDTO updateTestScore(int studentId, int subjectId, TestScoreDTO ts) {
		Optional<TestScore> optTS = testr.findByStudent_studentIdAndSubject_subjectId(studentId, subjectId);
		if (!optTS.isPresent()) {
			return null;
		}

		TestScore testScoreEntity = optTS.get();
		Student student = new Student();
		Subject subject = new Subject();

		student.setStudentId(studentId);
		subject.setSubjectId(subjectId);
		testScoreEntity.setStudent(student);
		testScoreEntity.setSubject(subject);

		if (ts.getScore() != null) {
			testScoreEntity.setScore(ts.getScore());
		}

		TestScore resEntity = testr.save(testScoreEntity);

		TestScoreDTO resdto = new TestScoreDTO();
		StudentDTO studto = new StudentDTO();
		SubjectDTO subdto = new SubjectDTO();
		resdto.setId(resEntity.getId());
		resdto.setScore(resEntity.getScore());
		studto.setStudentId(resEntity.getStudent().getStudentId());
		studto.setStudentName(resEntity.getStudent().getStudentName());
		subdto.setSubjectId(resEntity.getSubject().getSubjectId());
		subdto.setSubjectName(resEntity.getSubject().getSubjectName());
		resdto.setStudent(studto);
		resdto.setSubject(subdto);

		return resdto;
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
