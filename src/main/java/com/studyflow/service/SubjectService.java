package com.studyflow.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.studyflow.dto.StudentDTO;
import com.studyflow.dto.StudentSubjectDTO;
import com.studyflow.dto.SubjectDTO;
import com.studyflow.dto.TeacherDTO;
import com.studyflow.entity.Student;
import com.studyflow.entity.StudentSubject;
import com.studyflow.entity.Subject;
import com.studyflow.entity.Teacher;
import com.studyflow.repository.BillManagementRepository;
import com.studyflow.repository.HomeworkRepository;
import com.studyflow.repository.StudentRepository;
import com.studyflow.repository.StudentSubjectRepository;
import com.studyflow.repository.SubjectRepository;
import com.studyflow.repository.TeacherRepository;

import jakarta.transaction.Transactional;

@Service
public class SubjectService {

	SubjectRepository subr;
	StudentRepository stur;
	TeacherRepository tear;
	HomeworkRepository homr;
	StudentSubjectRepository stusubr;
	BillManagementRepository bilr;

	@Autowired
	public SubjectService(SubjectRepository subr, StudentRepository stur, TeacherRepository tear,
			HomeworkRepository homr, StudentSubjectRepository stusubr, BillManagementRepository bilr) {
		this.subr = subr;
		this.stur = stur;
		this.tear = tear;
		this.homr = homr;
		this.stusubr = stusubr;
		this.bilr = bilr;
	}

	// id로 학생-과목 객체 가져오는 메서드
	public StudentSubjectDTO getStudentSubjectById(Integer id) {
		Optional<StudentSubject> optStudentSubject = stusubr.findById(id);

		if (!optStudentSubject.isPresent()) {
			return null;
		}

		StudentSubject studentSubject = optStudentSubject.get();
		Student student = stur.findById(studentSubject.getStudent().getStudentId()).get();
		Subject subject = subr.findById(studentSubject.getSubject().getSubjectId()).get();
		StudentSubjectDTO stusubdto = new StudentSubjectDTO();
		StudentDTO studto = new StudentDTO();
		SubjectDTO subdto = new SubjectDTO();
		studto.setStudentId(student.getStudentId());
		studto.setStudentName(student.getStudentName());
		subdto.setSubjectId(subject.getSubjectId());
		subdto.setSubjectName(subject.getSubjectName());
		stusubdto.setId(id);
		stusubdto.setStudent(studto);
		stusubdto.setSubject(subdto);

		return stusubdto;
	}

	// 학생 id, 과목 id로 학생-과목 객체 가져오는 메서드
	public StudentSubjectDTO getStudentSubjectByStudentIdAndSubjectId(Integer studentId, Integer subjectId) {
		Optional<StudentSubject> optStudentSubject = stusubr.findByStudent_studentIdAndSubject_subjectId(studentId,
				subjectId);

		if (!optStudentSubject.isPresent()) {
			return null;
		}

		StudentSubject studentSubject = optStudentSubject.get();
		StudentSubjectDTO stusubdto = new StudentSubjectDTO();

		stusubdto.setId(studentSubject.getId());
		stusubdto.setStudent(null);
		stusubdto.setSubject(null);

		return stusubdto;
	}

	// 과목 수정 메소드
	public SubjectDTO updateSubject(int subjectId, SubjectDTO s) {
		Optional<Subject> optS = subr.findById(subjectId);
		// 수정하려는 subjectName이 이미 존재한다면 null을 return
		Optional<Subject> optSubjectName = subr.findBySubjectName(s.getSubjectName());
		if (!optS.isPresent() || optSubjectName.isPresent()) {
			return null;
		}

		Subject subjectEntity = optS.get();

		subjectEntity.setSubjectId(subjectId);

		if (s.getSubjectName() != null && !s.getSubjectName().equals(subjectEntity.getSubjectName())) {
			subjectEntity.setSubjectName(s.getSubjectName());
		}

		if (s.getTeacher().getUserId() != null) {
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
			subdto.setSubjectId(s.getSubjectId());
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
		List<SubjectDTO> res = new ArrayList<>();

		for (Subject s : subjects) {
			SubjectDTO subdto = new SubjectDTO();
			subdto.setSubjectId(s.getSubjectId());
			subdto.setSubjectName(s.getSubjectName());
			subdto.setHomework(null);
			subdto.setTeacher(null);
			res.add(subdto);
		}

		return res;
	}

	// 과목 id로 해당 과목 삭제하는 메소드
	@Transactional
	public boolean deleteSubject(int subjectId) {
		Optional<Subject> optS = subr.findById(subjectId);
		if (!optS.isPresent()) {
			return false;
		}

		bilr.deleteByStudentSubject_Subject_subjectId(subjectId);
		stusubr.deleteBySubject_subjectId(subjectId);
		homr.deleteBySubject_subjectId(subjectId);
		subr.deleteById(subjectId);

		return true;
	}

	// 학생-과목 추가하는 메소드, 프론트에서 select 태그로 과목이름 받아올 것
	public StudentSubjectDTO createStudentSubject(StudentSubjectDTO ss) {
		// 프론트에서 받아온 subjectName으로 subjectId 찾기
		String subjectName = ss.getSubject().getSubjectName();
		Optional<Subject> optS = subr.findBySubjectName(subjectName);

		if (!optS.isPresent()) {
			throw new IllegalArgumentException("Subject with name '" + subjectName + "' does not exist.");
		}

		int subjectId = optS.get().getSubjectId();

		Optional<StudentSubject> optSS = stusubr
				.findByStudent_studentIdAndSubject_subjectId(ss.getStudent().getStudentId(), subjectId);
		if (optSS.isPresent()) {
			throw new IllegalArgumentException("Student is already enrolled in the subject: " + subjectName);
		}

		StudentSubject ssEntity = new StudentSubject();
		Student student = new Student();
		Subject subject = new Subject();
		student.setStudentId(ss.getStudent().getStudentId());
		subject.setSubjectId(subjectId);
		ssEntity.setStudent(student);
		ssEntity.setSubject(subject);

		StudentSubject resEntity = stusubr.save(ssEntity);
		StudentSubjectDTO resdto = new StudentSubjectDTO();
		StudentDTO studentdto = new StudentDTO();
		SubjectDTO subjectdto = new SubjectDTO();
		studentdto.setStudentId(resEntity.getStudent().getStudentId());
		subjectdto.setSubjectId(resEntity.getSubject().getSubjectId());
		resdto.setId(resEntity.getId());
		resdto.setStudent(studentdto);
		resdto.setSubject(subjectdto);

		return resdto;
	}
}
