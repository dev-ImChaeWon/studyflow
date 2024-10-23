package com.studyflow.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.studyflow.dto.HomeworkDTO;
import com.studyflow.dto.StudentDTO;
import com.studyflow.entity.Homework;
import com.studyflow.entity.Student;
import com.studyflow.entity.Subject;
import com.studyflow.repository.HomeworkRepository;
import com.studyflow.repository.StudentRepository;
import com.studyflow.repository.TeacherRepository;
import com.studyflow.response.PageResponse;

@Service
public class StudentService {

	TeacherRepository tear;
	StudentRepository stur;
	HomeworkRepository homr;

	@Autowired
	public StudentService(TeacherRepository tear, StudentRepository stur, HomeworkRepository homr) {
		this.tear = tear;
		this.stur = stur;
		this.homr = homr;
	}

	// id로 해당 학생 전체 숙제 목록
	public StudentDTO getHomeworkById(int id) {
		Optional<Student> res = stur.findById(id);
		StudentDTO studto = new StudentDTO();
		if (res.isPresent()) {
			Student tmp = res.get();

			studto.setStudentId(tmp.getStudentId());
			studto.setStudentName(tmp.getStudentName());

			List<HomeworkDTO> homework = new ArrayList<>();
			for (Homework hm : tmp.getHomework()) {
				HomeworkDTO homdto = new HomeworkDTO();
				homdto.setHomeworkId(hm.getHomeworkId());

				// Subject 초기화
				if (hm.getSubject() != null) {
					Subject subject = new Subject();
					subject.setSubjectId(hm.getSubject().getSubjectId());
					homdto.setSubject(subject);
				}

				homdto.setHomeworkPage(hm.getHomeworkPage());
				homdto.setHomeworkDatetime(hm.getHomeworkDatetime());
				homdto.setCompletedPage(hm.getCompletedPage());
				homdto.setComment(hm.getComment());
				homdto.setCompleteDatetime(hm.getCompleteDatetime());

				homework.add(homdto);
			}
			studto.setHomework(homework);
			return studto;
		}
		return null; // Optional<>.isPresent()가 false 일때
	}

	// 학생 목록 조회 API
//	public List<StudentDTO> getAllStudent() {
//
//		List<Student> li = stur.findAll();
//		List<StudentDTO> res = new ArrayList<>();
//
//		for (Student s : li) {
//			StudentDTO studto = new StudentDTO();
//			studto.setStudentId(s.getStudentId());
//			studto.setStudentName(s.getStudentName());
//
//			res.add(studto);
//		}
//		return res;
//	}
	public PageResponse<StudentDTO> getStudent(int page, int size, LocalDateTime date, String teacherName, String isEnd,
			String studentName) {
		String findByTeacherPattern = "%" + teacherName + "%";
		String findByStudentPattern = "%" + studentName + "%";

		Sort s = Sort.by(Sort.Order.asc("studentId"));
		PageRequest pr = PageRequest.of(page - 1, size, s);

		Page<Student> res = null;
		if (findByTeacherPattern == null || findByTeacherPattern.equals("")) { // 선생님 이름이 param에 없을 때
			if (findByStudentPattern == null || findByStudentPattern.equals("")) { // 선생님 이름이 param에 없으면서 학생이름도 param에
																					// 없을 때
				// 선생님 이름 X, 학생 이름 X
				res = stur.findAllBy(pr);
			} else {
				// 선생님 이름 X, 학생 이름 O
				res = stur.findAllByStudentName(findByStudentPattern, pr);
			}
		} else { // 선생님 이름이 param에 있을 때
			if (findByStudentPattern == null || findByStudentPattern.equals("")) {
				// 선생님 이름 O, 학생 이름 X
//				res = stur.findAllStudentByTeacher(findByTeacherPattern, pr);
			} else {
				// 선생님 이름 O, 학생 이름 O
//				res = stur.findAllStudentByTeacherAndStudent(findByTeacherPattern, findByStudentPattern, pr);
			}
		}

		// res.getContent() -> List<Student> -> List<StudentDTO>
		// Student 를 DTO로 바꾸고 PageResponse에 담아서 Controller 로 return
		List<StudentDTO> li = new ArrayList<>();
		for (Student stu : res.getContent()) {
			StudentDTO studto = new StudentDTO();
			studto.setStudentId(stu.getStudentId());
			studto.setStudentName(stu.getStudentName());

			List<HomeworkDTO> hwList = new ArrayList<>();
			for (Homework hw : stu.getHomework()) {
				HomeworkDTO hwdto = new HomeworkDTO();
				hwdto.setHomeworkId(hw.getHomeworkId());
				hwdto.setSubject(hw.getSubject());
				hwdto.setHomeworkPage(hw.getHomeworkPage());
				hwdto.setCompletedPage(hw.getCompletedPage());
				hwdto.setComment(hw.getComment());
				hwList.add(hwdto);
			}

			studto.setHomework(hwList);
			li.add(studto);
		}

		PageResponse<StudentDTO> studentPage = new PageResponse<>();
		studentPage.setList(li);
		studentPage.setCurrentPage(page);
		studentPage.setHasNext(page < res.getTotalPages());
		studentPage.setHasPrevieous(page > 1);
		studentPage.setTotalElements(res.getTotalElements());
		studentPage.setTotalPages(res.getTotalPages());
		studentPage.setDate(date.now());

		return studentPage;
	}
}
