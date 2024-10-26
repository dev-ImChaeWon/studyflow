package com.studyflow.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.studyflow.dto.AttendanceDTO;
import com.studyflow.dto.HomeworkDTO;
import com.studyflow.dto.StudentDTO;
import com.studyflow.entity.Attendance;
import com.studyflow.entity.Homework;
import com.studyflow.entity.Student;
import com.studyflow.entity.Subject;
import com.studyflow.repository.AttendanceRepository;
import com.studyflow.repository.HomeworkRepository;
import com.studyflow.repository.StudentRepository;
import com.studyflow.repository.TeacherRepository;
import com.studyflow.response.PageResponse;

@Service
public class StudentService {

//	private static final Logger logger = LoggerFactory.getLogger(StudentService.class);

	TeacherRepository tear;
	StudentRepository stur;
	HomeworkRepository homr;
	AttendanceRepository attr;

	@Autowired
	public StudentService(TeacherRepository tear, StudentRepository stur, HomeworkRepository homr,
			AttendanceRepository attr) {
		this.tear = tear;
		this.stur = stur;
		this.homr = homr;
		this.attr = attr;
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

	// 출결 여부 조회 API
	public List<AttendanceDTO> getIsAttendance() {
		List<Attendance> attli = attr.findAll();
		List<AttendanceDTO> attres = new ArrayList<>();
		List<Student> stuli = stur.findAll();

		for (Attendance a : attli) {
			AttendanceDTO attdto = new AttendanceDTO();
			attdto.setStudentId(a.getStudent().getStudentId());
			attdto.setIsAttend(a.getIsAttend());

			for (Student s : stuli) {
				StudentDTO studto = new StudentDTO();
				studto.setStudentName(s.getStudentName());

				attdto.setStudent(studto);
			}
			attres.add(attdto);
		}

		return attres;
	}

	// 개선안
	public void getStudent2(int page, int size, Date date, String teacherName, String homeworkStatus,
			String studentName) {
		// 페이지 --> 학생을 조회하면 homework는 딸려온다
		// 학생조회할때 특정 날짜로, 완료여부 학생 조회하는 api

	}

	public PageResponse<StudentDTO> getStudent(int page, int size, Date date, String teacherName, String homeworkStatus,
			String studentName) {
		String findByTeacherPattern = (teacherName != null && !teacherName.trim().isEmpty() && teacherName != "")
				? "%" + teacherName.trim() + "%"
				: null;
		String findByStudentPattern = (studentName != null && !studentName.trim().isEmpty() && studentName != "")
				? "%" + studentName.trim() + "%"
				: null;

		// 로그 출력
//		logger.info("Received teacherName: {}", teacherName);
//	    logger.info("findByTeacherPattern: {}", findByTeacherPattern);
//	    logger.info("Received studentName: {}", studentName);
//	    logger.info("findByStudentPattern: {}", findByStudentPattern);

		Sort s = Sort.by(Sort.Order.asc("studentId"));
		PageRequest pr = PageRequest.of(page - 1, size, s);

		Page<Student> res = null;

		if (findByTeacherPattern == null) { // teacherName 이 없을 때
			if (findByStudentPattern == null) {
				// 선생님 이름 X, 학생 이름 X
				res = stur.findAllBy(pr);
			} else {
				// 선생님 이름 X, 학생 이름 O
//				logger.info("Searching for students with name pattern: {}", findByStudentPattern);
				res = stur.findAllByStudentName(findByStudentPattern, pr);
			}
		} else { // teacherName 이 있을 때
			if (findByStudentPattern == null) {
				// 선생님 이름 O, 학생 이름 X
//				logger.info("Searching for students taught by teacher: {}", findByTeacherPattern);
				res = stur.findAllStudentByTeacher(findByTeacherPattern, pr);
			} else {
				// 선생님 이름 O, 학생 이름 O
//				logger.info("Searching for students taught by teacher: {} and with name pattern: {}", findByTeacherPattern, findByStudentPattern);
				res = stur.findAllStudentByTeacherAndStudent(findByTeacherPattern, findByStudentPattern, pr);
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
				Subject subject = new Subject();
				subject.setSubjectName(hw.getSubject().getSubjectName());
				hwdto.setSubject(subject);
				hwdto.setHomeworkPage(hw.getHomeworkPage());
				hwdto.setCompletedPage(hw.getCompletedPage());
				hwdto.setComment(hw.getComment());
				hwList.add(hwdto);
			}

			studto.setHomework(hwList);
			li.add(studto);
		}

		if (homeworkStatus != null && homeworkStatus.equals("complete")) {
			List<StudentDTO> completeLi = new ArrayList<>();

			for (StudentDTO studentDTO : li) {
				List<HomeworkDTO> completeHomework = new ArrayList<>();

				for (HomeworkDTO homework : studentDTO.getHomework()) {
					if (homework.getHomeworkPage() == homework.getCompletedPage()) {
						completeHomework.add(homework);
					}
				}

				// Homework 중에 완전히 완료된 항목이 있을 경우 해당 학생 추가
				if (!completeHomework.isEmpty()) {
					StudentDTO completedStudent = new StudentDTO();
					completedStudent.setStudentId(studentDTO.getStudentId());
					completedStudent.setStudentName(studentDTO.getStudentName());
					completedStudent.setHomework(completeHomework);
					completeLi.add(completedStudent);
				}
			}

			PageResponse<StudentDTO> studentPage = new PageResponse<>();
			studentPage.setList(completeLi);
			studentPage.setCurrentPage(page);
			studentPage.setHasNext(page < res.getTotalPages());
			studentPage.setHasPrevieous(page > 1);
			studentPage.setTotalElements(res.getTotalElements());
			studentPage.setTotalPages(res.getTotalPages());
//			studentPage.setDate();

			return studentPage;

		} else if (homeworkStatus != null && homeworkStatus.equals("not-complete")) {
			List<StudentDTO> notCompleteLi = new ArrayList<>();

			for (StudentDTO studentDTO : li) {
				List<HomeworkDTO> notCompleteHomework = new ArrayList<>();

				for (HomeworkDTO homework : studentDTO.getHomework()) {
					if (homework.getHomeworkPage() > homework.getCompletedPage()) {
						notCompleteHomework.add(homework);
					}
				}

				// 완료되지 않은 Homework가 있는 경우만 추가
				if (!notCompleteHomework.isEmpty()) {
					StudentDTO notCompletedStudent = new StudentDTO();
					notCompletedStudent.setStudentId(studentDTO.getStudentId());
					notCompletedStudent.setStudentName(studentDTO.getStudentName());
					notCompletedStudent.setHomework(notCompleteHomework);
					notCompleteLi.add(notCompletedStudent);
				}
			}

			// 전체 갯수
			PageResponse<StudentDTO> studentPage = new PageResponse<>();
			studentPage.setList(notCompleteLi);
			studentPage.setCurrentPage(page);
			studentPage.setHasNext(page < res.getTotalPages());
			studentPage.setHasPrevieous(page > 1);
			studentPage.setTotalElements(res.getTotalElements());
			studentPage.setTotalPages(res.getTotalPages());

			return studentPage;

		} else if (homeworkStatus != null && homeworkStatus.equals("no-homework")) {
			List<StudentDTO> noHomeworkLi = new ArrayList<>();

			for (StudentDTO studentDTO : li) {
				List<HomeworkDTO> homeworkList = studentDTO.getHomework();
				if (homeworkList == null || homeworkList.isEmpty()) {
					noHomeworkLi.add(studentDTO);
				}
			}

			PageResponse<StudentDTO> studentPage = new PageResponse<>();
			studentPage.setList(noHomeworkLi);
			studentPage.setCurrentPage(page);
			studentPage.setHasNext(page < res.getTotalPages());
			studentPage.setHasPrevieous(page > 1);
			studentPage.setTotalElements(res.getTotalElements());
			studentPage.setTotalPages(res.getTotalPages());

			return studentPage;

		}

		PageResponse<StudentDTO> studentPage = new PageResponse<>();
		studentPage.setList(li);
		studentPage.setCurrentPage(page);
		studentPage.setHasNext(page < res.getTotalPages());
		studentPage.setHasPrevieous(page > 1);
		studentPage.setTotalElements(res.getTotalElements());
		studentPage.setTotalPages(res.getTotalPages());
//		studentPage.setDate();

		return studentPage;
	}
}
