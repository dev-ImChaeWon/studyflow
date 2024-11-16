package com.studyflow.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.studyflow.dto.SubjectDTO;
import com.studyflow.dto.TeacherDTO;
import com.studyflow.entity.Subject;
import com.studyflow.entity.Teacher;
import com.studyflow.repository.HomeworkRepository;
import com.studyflow.repository.SubjectRepository;
import com.studyflow.repository.TeacherRepository;

import jakarta.transaction.Transactional;

@Service
public class SubjectService {

	SubjectRepository subr;
	TeacherRepository tear;
	HomeworkRepository homr;

	@Autowired
	public SubjectService(SubjectRepository subr, TeacherRepository tear, HomeworkRepository homr) {
		this.subr = subr;
		this.tear = tear;
		this.homr = homr;
	}
	
	// 과목 수정 메소드
	public SubjectDTO updateSubject(int subjectId, SubjectDTO s) { 
		Optional<Subject> optS = subr.findById(subjectId);
		if(!optS.isPresent()) {
			return null;
		} 
		
		Subject subjectEntity = optS.get();
		
		subjectEntity.setSubjectId(subjectId);
		
		if(s.getSubjectName() != null) {
			subjectEntity.setSubjectName(s.getSubjectName());
		}
		
		if(s.getTeacher().getUserId() != null) {
			Teacher teacherId = new Teacher();
			teacherId.setUserId(s.getTeacher().getUserId());
			subjectEntity.setTeacher(teacherId);
		}
		
		Subject subEntity = subr.save(subjectEntity);
		SubjectDTO subdto = new SubjectDTO();
		subdto.setSubjectId(subEntity.getSubjectId());
		subdto.setSubjectName(subEntity.getSubjectName());
		
		return subdto;
	}

	// 전체 과목 리스트 가져오는 메소드
	@Transactional
	public List<SubjectDTO> getSubjectName() {
		List<Subject> li = subr.findAll();
		List<SubjectDTO> res = new ArrayList<>();

		for (Subject s : li) {
			SubjectDTO subdto = new SubjectDTO();
			subdto.setSubjectName(s.getSubjectName());
			res.add(subdto);
		}

		return res;
	}

	// 강좌 개설 시 과목 만드는 메소드
	public SubjectDTO createSubject(SubjectDTO s) {
		Subject subjectEntity = new Subject();
		Teacher teacher = new Teacher();
		teacher.setUserId(s.getTeacher().getUserId());
		subjectEntity.setSubjectName(s.getSubjectName());
		subjectEntity.setTeacher(teacher);
		
		Subject resEntity = subr.save(subjectEntity);
		SubjectDTO subdto = new SubjectDTO();
		TeacherDTO teadto = new TeacherDTO();
		subdto.setSubjectId(subjectEntity.getSubjectId());
		subdto.setSubjectName(subjectEntity.getSubjectName());
		teadto.setUserId(subjectEntity.getTeacher().getUserId());
		subdto.setTeacher(teadto);
		
		return subdto;
	}

	// 교사 id로 과목 가져오는 메소드
	public List<SubjectDTO> getSubjectByTeacherId(String userId) {
		List<Subject> li = subr.findAllByTeacher_UserId(userId);
		List<SubjectDTO> res = new ArrayList<>();

		for (Subject s : li) {
			SubjectDTO subdto = new SubjectDTO();
			subdto.setSubjectId(s.getSubjectId());
			subdto.setSubjectName(s.getSubjectName());

			res.add(subdto);
		}

		return res;
	}

	// 학생 id로 과목 가져오는 메소드
	public List<SubjectDTO> getSubjectsByStudentId(Integer studentId) {
		List<Subject> subjects = subr.findSubjectsByStudentId(studentId);
		return subjects.stream().map(subject -> {
			SubjectDTO dto = new SubjectDTO();
			dto.setSubjectId(subject.getSubjectId());
			dto.setSubjectName(subject.getSubjectName());
			return dto;
		}).collect(Collectors.toList());
	}
	
	// 과목 id로 해당 과목 삭제하는 메소드
	public boolean deleteSubject(int subjectId) {
		Optional<Subject> optS = subr.findById(subjectId);
		if(!optS.isPresent()) {
			return false;
		}
		
		homr.deleteBySubject_subjectId(subjectId);
		
		subr.deleteById(subjectId);
		
		return true;
	}

}
