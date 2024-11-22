package com.studyflow.service;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.studyflow.dto.AttendanceDTO;
import com.studyflow.dto.HomeworkDTO;
import com.studyflow.dto.StudentDTO;
import com.studyflow.dto.StudentSubjectDTO;
import com.studyflow.dto.SubjectDTO;
import com.studyflow.entity.Attendance;
import com.studyflow.entity.Homework;
import com.studyflow.entity.Student;
import com.studyflow.entity.StudentSubject;
import com.studyflow.entity.Subject;
import com.studyflow.repository.AttendanceRepository;
import com.studyflow.repository.HomeworkRepository;
import com.studyflow.repository.StudentRepository;
import com.studyflow.repository.StudentSubjectRepository;
import com.studyflow.repository.SubjectRepository;
import com.studyflow.repository.TeacherRepository;
import com.studyflow.response.PageResponse;

@Service
public class StudentService {

//	private static final Logger logger = LoggerFactory.getLogger(StudentService.class);

	TeacherRepository tear;
	StudentRepository stur;
	HomeworkRepository homr;
	AttendanceRepository attr;
	SubjectRepository subr;
	StudentSubjectRepository stusubr;

	@Autowired
	public StudentService(TeacherRepository tear, StudentRepository stur, HomeworkRepository homr,
			AttendanceRepository attr, SubjectRepository subr, StudentSubjectRepository stusubr) {
		this.tear = tear;
		this.stur = stur;
		this.homr = homr;
		this.attr = attr;
		this.subr = subr;
		this.stusubr = stusubr;

	}

	// id와 date로 해당 날짜와 해당 학생 숙제 정보 조회
	public StudentDTO getHomeworkByIdAndDate(int id, LocalDate date) {
		Optional<Student> res = stur.findById(id);

		StudentDTO studto = new StudentDTO();

		if (res.isPresent()) {
			Student tmp = res.get();

			studto.setStudentId(tmp.getStudentId());
			studto.setStudentName(tmp.getStudentName());

			List<SubjectDTO> subject = new ArrayList<>();
			for (Subject s : stusubr.findAllSubjectsByStudentId(tmp.getStudentId())) {
				SubjectDTO subdto = new SubjectDTO();
				subdto.setSubjectId(s.getSubjectId());
				subdto.setSubjectName(s.getSubjectName());

				List<HomeworkDTO> homework = new ArrayList<>();
				for (Homework hm : tmp.getHomework()) {
					if (hm.getHomeworkDatetime().toLocalDate().isEqual(date)
							&& hm.getSubject().getSubjectId().equals(s.getSubjectId())) {
						HomeworkDTO homdto = new HomeworkDTO();
						homdto.setHomeworkId(hm.getHomeworkId());
						homdto.setHomeworkPage(hm.getHomeworkPage());
						homdto.setHomeworkDatetime(hm.getHomeworkDatetime());
						homdto.setCompletedPage(hm.getCompletedPage());
						homdto.setComment(hm.getComment());
						homdto.setCompleteDatetime(hm.getCompleteDatetime());

						homework.add(homdto);
					}
					subdto.setHomework(homework);
				}
				subject.add(subdto);
			}
			studto.setSubjects(subject);
			return studto;
		}

		System.out.println("확인");
		return null;
	}

	// 날짜별 출결 여부 조회 API (수정)
	public List<AttendanceDTO> getAttendanceByDate2(Date attendanceDate) {
		List<Student> allStudents = stur.findAll();
		List<Attendance> attli = attr.findByAttendanceDate(attendanceDate);
		List<AttendanceDTO> attres = new ArrayList<>();

		for (Student s : allStudents) {
			Optional<Attendance> optAttendance = attr.findById(s.getStudentId(), attendanceDate);

			AttendanceDTO attdto = new AttendanceDTO();
			attdto.setStudentId(s.getStudentId());
			attdto.setAttendanceDate(attendanceDate);

			if (optAttendance.isPresent()) {
				// 해당 날짜에 해당 학생의 출결이 등록된 경우
				Attendance a = optAttendance.get();
				attdto.setIsAttend(a.getIsAttend());

				StudentDTO studto = new StudentDTO();
				studto.setStudentName(s.getStudentName());
				attdto.setStudent(studto);
			} else {
				// 출석 체크를 하지 않은 상태
				attdto.setIsAttend(null); // 출석하지 않았다고 설정
				StudentDTO studto = new StudentDTO();
				studto.setStudentName(s.getStudentName());
				attdto.setStudent(studto);
			}

			attres.add(attdto);
		}

		return attres;
	}

//	// 날짜별 출결 여부 조회 API
//	public List<AttendanceDTO> getAttendanceByDate(Date attendanceDate) {
//		List<Attendance> all = attr.findAll();
//		List<Attendance> attli = attr.findByAttendanceDate(attendanceDate);
//		List<AttendanceDTO> attres = new ArrayList<>();
//
//		for (Attendance a : attli) {
//			AttendanceDTO attdto = new AttendanceDTO();
//			attdto.setStudentId(a.getStudent().getStudentId());
//			attdto.setIsAttend(a.getIsAttend());
//			attdto.setAttendanceDate(a.getAttendanceDate());
//
//			StudentDTO studto = new StudentDTO();
//			studto.setStudentName(a.getStudent().getStudentName());
//			attdto.setStudent(studto);
//
//			attres.add(attdto);
//
//		}
//
//		if (attendanceDate != null) {
//			for (Attendance a : all) {
//				if (attendanceDate.compareTo(a.getId().getAttendanceDate()) != 0) {
//					AttendanceDTO attdto = new AttendanceDTO();
//					attdto.setStudentId(a.getStudent().getStudentId());
//
//					StudentDTO studto = new StudentDTO();
//					studto.setStudentName(a.getStudent().getStudentName());
//					attdto.setStudent(studto);
//
//					attres.add(attdto);
//
//				}
//			}
//		}
//
//		return attres;
//	}

//	public List<AttendanceDTO> getIsAttendance() {
//		List<Attendance> attli = attr.findAll();
//		List<AttendanceDTO> attres = new ArrayList<>();
////		List<StudentDTO> stures = new ArrayList<>();
//
////			for (Student s : stuli) {
////				StudentDTO studto = new StudentDTO();
////				studto.setStudentId(s.getStudentId());
////				studto.setStudentName(s.getStudentName());
////				
////				stures.add(studto);
////				
////				for (Attendance a : attli) {
////					AttendanceDTO attdto = new AttendanceDTO();
////					attdto.setIsAttend(a.getIsAttend());
////					
////					attres.add(attdto);
////				}
////			}
//
//		for (Attendance a : attli) {
//			AttendanceDTO attdto = new AttendanceDTO();
//			attdto.setStudentId(a.getStudent().getStudentId());
//			attdto.setIsAttend(a.getIsAttend());
//
//			StudentDTO studto = new StudentDTO();
//			studto.setStudentName(a.getStudent().getStudentName());
//
//			attdto.setStudent(studto);
//			attres.add(attdto);
//		}
//		return attres;
//	}

	// id로 해당 학생 전체 숙제 목록을 가져오는 메소드
	public StudentDTO getHomeworkById(int id) {
		Optional<Student> res = stur.findById(id); // 학생 ID로 학생 정보를 검색
		StudentDTO studto = new StudentDTO(); // StudentDTO 객체 생성
		if (res.isPresent()) { // 학생이 존재하는 경우
			Student tmp = res.get(); // 학생 정보 가져오기

			// 학생 ID와 이름 설정
			studto.setStudentId(tmp.getStudentId());
			studto.setStudentName(tmp.getStudentName());

			List<SubjectDTO> subjects = new ArrayList<>(); // 과목 목록 초기화

			// 학생의 숙제 목록을 반복
			for (Homework hm : tmp.getHomework()) {
				// 현재 숙제가 속한 과목을 확인
				Subject subjectData = hm.getSubject(); // 숙제가 속한 과목 가져오기
				if (subjectData != null) { // 과목이 존재하는 경우
					// 과목 DTO 찾기 또는 생성
					SubjectDTO subjectDto = null;
					for (SubjectDTO existingSubject : subjects) {
						if (existingSubject.getSubjectId().equals(subjectData.getSubjectId())) {
							subjectDto = existingSubject; // 이미 존재하는 과목 DTO
							break; // 과목을 찾았으니 반복 종료
						}
					}
					// 과목 DTO가 없으면 새로 생성
					if (subjectDto == null) {
						subjectDto = new SubjectDTO();
						subjectDto.setSubjectId(subjectData.getSubjectId());
						subjectDto.setSubjectName(subjectData.getSubjectName());
						subjectDto.setHomework(new ArrayList<>()); // 숙제 목록 초기화
//						subjects.add(subjectDto); // 새로운 과목 추가
					}
					// Subject 초기화
					if (hm.getSubject() != null) {
						SubjectDTO subject = new SubjectDTO();
						subject.setSubjectId(hm.getSubject().getSubjectId());
					}

					// 숙제 DTO 생성
					HomeworkDTO homdto = new HomeworkDTO();
					homdto.setHomeworkId(hm.getHomeworkId()); // 숙제 ID 설정
					homdto.setHomeworkPage(hm.getHomeworkPage()); // 숙제 페이지 수 설정
					homdto.setHomeworkDatetime(hm.getHomeworkDatetime()); // 숙제 제출 날짜 설정
					homdto.setCompletedPage(hm.getCompletedPage()); // 완료된 페이지 수 설정
					homdto.setComment(hm.getComment()); // 코멘트 설정
					homdto.setCompleteDatetime(hm.getCompleteDatetime()); // 숙제 완료 날짜 설정

					// 과목 DTO에 숙제 추가
					subjectDto.getHomework().add(homdto); // 과목 DTO에 숙제 추가
					subjects.add(subjectDto);
				}
			}

			studto.setSubjects(subjects); // 과목 목록 설정
			return studto; // 완성된 StudentDTO 반환
		}
		return null; // 학생 정보가 없는 경우 null 반환
	}

//	// id로 해당 학생 전체 숙제 목록을 가져오는 메소드
//	public StudentDTO getHomeworkById(int id) {
//	    Optional<Student> res = stur.findById(id); // 학생 ID로 학생 정보를 검색
//	    StudentDTO studto = new StudentDTO(); // StudentDTO 객체 생성
//	    if (res.isPresent()) { // 학생이 존재하는 경우
//	        Student tmp = res.get(); // 학생 정보 가져오기
//
//	        // 학생 ID와 이름 설정
//	        studto.setStudentId(tmp.getStudentId());
//	        studto.setStudentName(tmp.getStudentName());
//	        
//	        List<SubjectDTO> subjects = new ArrayList<>(); // 과목 목록 초기화
//
//	        // 학생의 숙제 목록을 반복
//	        for (Homework hm : tmp.getHomework()) {
//	            // 현재 숙제가 속한 과목을 확인
//	            Subject subjectData = hm.getSubject(); // 숙제가 속한 과목 가져오기
//	            if (subjectData != null) { // 과목이 존재하는 경우
//	                // 해당 과목이 리스트에 존재하는지 확인
//	                SubjectDTO subjectDto = findOrCreateSubject(subjects, subjectData); // 과목 DTO 얻기
//
//	                // 숙제 DTO 생성
//	                HomeworkDTO homdto = new HomeworkDTO();
//	                homdto.setHomeworkId(hm.getHomeworkId()); // 숙제 ID 설정
//	                homdto.setSubject(null); // 과목 정보는 null로 설정
//	                homdto.setStudent(null); // 학생 정보는 null로 설정
//	                homdto.setHomeworkPage(hm.getHomeworkPage()); // 숙제 페이지 수 설정
//	                homdto.setHomeworkDatetime(hm.getHomeworkDatetime()); // 숙제 제출 날짜 설정
//	                homdto.setCompletedPage(hm.getCompletedPage()); // 완료된 페이지 수 설정
//	                homdto.setComment(hm.getComment()); // 코멘트 설정
//	                homdto.setCompleteDatetime(hm.getCompleteDatetime()); // 숙제 완료 날짜 설정
//
//	                // 과목 DTO에 숙제 추가
//	                if (subjectDto.getHomework() == null) {
//	                    subjectDto.setHomework(new ArrayList<>()); // 숙제 목록 초기화
//	                }
//	                subjectDto.getHomework().add(homdto); // 과목 DTO에 숙제 추가
//	            }
//	        }
//
//	        // 과목 목록을 StudentDTO에 설정
//	        studto.setSubjects(subjects); // 과목 목록 설정
//	        return studto; // 완성된 StudentDTO 반환
//	        
//	    }
//	    return null; // 학생 정보가 없는 경우 null 반환
//	    
//	}
//	
//	private SubjectDTO findOrCreateSubject(List<SubjectDTO> subject, Subject subjectData) {
//		// 과목이 이미 존재하는지 확인
//	    for (SubjectDTO subjectDto : subject) {
//	        if (subjectDto.getSubjectId().equals(subjectData.getSubjectId())) {
//	            return subjectDto; // 존재하는 과목 DTO 반환
//	        }
//	    }
//	    // 과목이 존재하지 않는 경우 새로 생성
//	    SubjectDTO newSubjectDto = new SubjectDTO();
//	    newSubjectDto.setSubjectId(subjectData.getSubjectId());
//	    newSubjectDto.setSubjectName(subjectData.getSubjectName());
//	    newSubjectDto.setTeacher(null); // 교사 정보는 null로 설정
//	    newSubjectDto.setHomework(new ArrayList<>()); // 숙제 목록 초기화
//	    subject.add(newSubjectDto); // 새로운 과목 추가
//	    return newSubjectDto; // 새로 생성한 과목 DTO 반환
//	}

//	// id로 해당 학생 전체 숙제 목록
//	public StudentDTO getHomeworkById(int id) {
//		Optional<Student> res = stur.findById(id);
//		StudentDTO studto = new StudentDTO();
//		if (res.isPresent()) {
//			Student tmp = res.get();
//
//			studto.setStudentId(tmp.getStudentId());
//			studto.setStudentName(tmp.getStudentName());
//
//			List<HomeworkDTO> homework = new ArrayList<>();
//			for (Homework hm : tmp.getHomework()) {
//				HomeworkDTO homdto = new HomeworkDTO();
//				homdto.setHomeworkId(hm.getHomeworkId());
//
//				// Subject 초기화
//				if (hm.getSubject() != null) {
//					Subject subject = new Subject();
//					subject.setSubjectId(hm.getSubject().getSubjectId());
//					homdto.setSubject(subject);
//				}
//
//				homdto.setHomeworkPage(hm.getHomeworkPage());
//				homdto.setHomeworkDatetime(hm.getHomeworkDatetime());
//				homdto.setCompletedPage(hm.getCompletedPage());
//				homdto.setComment(hm.getComment());
//				homdto.setCompleteDatetime(hm.getCompleteDatetime());
//
//				homework.add(homdto);
//			}
//			studto.setHomework(homework);
//			return studto;
//		}
//		return null; // Optional<>.isPresent()가 false 일때
//	}

	// 개선안
	public PageResponse<StudentDTO> getStudent2(int page, int size, LocalDate date, String teacherId,
			String homeworkStatus, String studentName) {
		// 페이지 --> 학생을 조회하면 homework는 딸려온다
		// 학생조회할때 특정 날짜로, 완료여부 학생 조회하는 api
		Sort s = Sort.by(Sort.Order.asc("studentId"));
		PageRequest pr = PageRequest.of(page - 1, size, s);
		String studentNamePattern = null;
		Page<Student> res = null;
		if (date == null) {
			date = LocalDate.now();
		}

		if (studentName == null) {
			studentNamePattern = "%%";
		} else {
			studentNamePattern = "%" + studentName + "%";
		}

		if (teacherId == null || teacherId.equals("all")) {
			if (homeworkStatus == null) {

				// teacherId X, 모든 parameter가 null
				res = stur.findAllStudentWithoutTeacher(LocalDateTime.of(date, LocalTime.of(0, 0)),
						LocalDateTime.of(date.plusDays(1), LocalTime.of(0, 0)), studentNamePattern, pr);
			} else if (homeworkStatus.equals("not-complete")) {
				// teacherId X, 숙제 안 끝남
				res = stur.findNotCompletedStudentWithoutTeacher(LocalDateTime.of(date, LocalTime.of(0, 0)),
						LocalDateTime.of(date.plusDays(1), LocalTime.of(0, 0)), studentNamePattern, pr);
			} else if (homeworkStatus.equals("no-homework")) {
				// teacherId X, 숙제 없음
				res = stur.findNoHomeworkStudentWithoutTeacher(LocalDateTime.of(date, LocalTime.of(0, 0)),
						LocalDateTime.of(date.plusDays(1), LocalTime.of(0, 0)), studentNamePattern, pr);
			} else if (homeworkStatus.equals("complete")) {
				// teacherId X, 숙제 끝남
				res = stur.findCompletedStudentWithoutTeacher(LocalDateTime.of(date, LocalTime.of(0, 0)),
						LocalDateTime.of(date.plusDays(1), LocalTime.of(0, 0)), studentNamePattern, pr);
			}
		} else {
			if (homeworkStatus == null) {
				// teacherId O, 나머지 모든 parameter가 null
				res = stur.findAllStudentWithTeacher(teacherId, LocalDateTime.of(date, LocalTime.of(0, 0)),
						LocalDateTime.of(date.plusDays(1), LocalTime.of(0, 0)), studentNamePattern, pr);
			} else if (homeworkStatus.equals("not-complete")) {
				// teacherId O, 숙제 안 끝남
				res = stur.findNotCompletedStudentWithTeacher(teacherId, LocalDateTime.of(date, LocalTime.of(0, 0)),
						LocalDateTime.of(date.plusDays(1), LocalTime.of(0, 0)), studentNamePattern, pr);
			} else if (homeworkStatus.equals("no-homework")) {
				// teacherId O, 숙제 없음
				res = stur.findNoHomeworkStudentWithTeacher(teacherId, LocalDateTime.of(date, LocalTime.of(0, 0)),
						LocalDateTime.of(date.plusDays(1), LocalTime.of(0, 0)), studentNamePattern, pr);
			} else if (homeworkStatus.equals("complete")) {
				// teacherId O, 숙제 끝남
				res = stur.findCompletedStudentWithTeacher(teacherId, LocalDateTime.of(date, LocalTime.of(0, 0)),
						LocalDateTime.of(date.plusDays(1), LocalTime.of(0, 0)), studentNamePattern, pr);
			}
		}

//		System.out.println("=========조회 결과=========");
//		System.out.println(res.getTotalElements());
//		System.out.println(res.getTotalPages());
//		for (Student tmp : res.getContent()) {
//			System.out.println(tmp.getStudentId() + tmp.getStudentName());
//		}

		List<StudentDTO> li = new ArrayList<>();
		for (Student stu : res.getContent()) {
			StudentDTO studto = new StudentDTO();
			studto.setStudentId(stu.getStudentId());
			studto.setStudentName(stu.getStudentName());

			List<SubjectDTO> subList = new ArrayList<>();
			for (Subject subject : stusubr.findAllSubjectsByStudentId(stu.getStudentId())) {
				SubjectDTO subdto = new SubjectDTO();
				subdto.setSubjectId(subject.getSubjectId());
				subdto.setSubjectName(subject.getSubjectName());

				List<HomeworkDTO> hwList = new ArrayList<>();
				for (Homework hw : stu.getHomework()) {
					if (hw.getHomeworkDatetime().toLocalDate().isEqual(date)) {
						if (subject.getSubjectId() == hw.getSubject().getSubjectId()) {
							HomeworkDTO hwdto = new HomeworkDTO();
							hwdto.setHomeworkId(hw.getHomeworkId());
							hwdto.setHomeworkPage(hw.getHomeworkPage());
							hwdto.setCompletedPage(hw.getCompletedPage());
							hwdto.setComment(hw.getComment());
							hwList.add(hwdto);
						}
					}
				}

				subdto.setHomework(hwList);
				subList.add(subdto);
			}

			studto.setSubjects(subList);
			li.add(studto);
		}

		PageResponse<StudentDTO> studentPage = new PageResponse<>();
		studentPage.setList(li);
		studentPage.setCurrentPage(page);
		studentPage.setHasNext(page < res.getTotalPages());
		studentPage.setHasPrevieous(page > 1);
		studentPage.setTotalElements(res.getTotalElements());
		studentPage.setTotalPages(res.getTotalPages());

		return studentPage;
	}

	// 선생님 이름, 날짜, 숙제상태, 학생 이름으로 학생 가져오는 메소드 (개선안으로 대체됨)
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
//		res = stur.findAllByHomeworkDatetime(date, pr);

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
				SubjectDTO subject = new SubjectDTO();
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

	public StudentDTO createStudent(StudentDTO s) {
		Student studentEntity = new Student();
		studentEntity.setStudentName(s.getStudentName());

		Student resEntity = stur.save(studentEntity);
		StudentDTO resdto = new StudentDTO();
		resdto.setStudentId(resEntity.getStudentId());
		resdto.setStudentName(resEntity.getStudentName());

		return resdto;
	}

	// 과목으로 학생 리스트 가져오기
	public List<StudentDTO> getStudentsBySubjectName(String subjectName) {
	    // 과목을 이름으로 찾기
	    Optional<Subject> subjectOpt = subr.findBySubjectName(subjectName);

	    // 과목이 존재하면 해당 과목에 등록된 학생 리스트 가져오기
	    if (subjectOpt.isPresent()) {
	        Subject subject = subjectOpt.get();
	        List<StudentSubject> studentSubjects = stusubr.findBySubject_subjectId(subject.getSubjectId());

	        // 학생 정보를 담을 리스트
	        List<StudentDTO> studentDTOs = new ArrayList<>();

	        // 학생 목록을 조회하여 DTO에 맞게 변환
	        for (StudentSubject studentSubject : studentSubjects) {
	            Student student = studentSubject.getStudent();  // StudentSubject에서 학생 정보 가져오기
	            StudentDTO studentDTO = new StudentDTO();
	            studentDTO.setStudentId(student.getStudentId());
	            studentDTO.setStudentName(student.getStudentName());

	            // 학생이 등록한 과목들 가져오기
	            List<SubjectDTO> subjectDTOs = new ArrayList<>();
	            for(StudentSubject ss : studentSubjects) {
	            	SubjectDTO subdto = new SubjectDTO();
	            	subdto.setSubjectId(ss.getSubject().getSubjectId());
	            	subdto.setSubjectName(ss.getSubject().getSubjectName());
	            	
	            	subjectDTOs.add(subdto);
	            }

	            studentDTO.setSubjects(subjectDTOs);
	            studentDTOs.add(studentDTO);
	        }

	        return studentDTOs;
	    } else {
	        // 과목이 없으면 모든 학생 정보 가져오기
	        List<Student> students = stur.findAll();
	        List<StudentDTO> studentDTOs = new ArrayList<>();

	        for (Student student : students) {
	            StudentDTO studentDTO = new StudentDTO();
	            studentDTO.setStudentId(student.getStudentId());
	            studentDTO.setStudentName(student.getStudentName());

	            // 학생이 등록한 과목들 가져오기
	            List<Subject> subjects = stusubr.findAllSubjectsByStudentId(student.getStudentId());
	            List<SubjectDTO> subjectDTOs = subjects.stream()
	                .map(subj -> {
	                    SubjectDTO subjectDTO = new SubjectDTO();
	                    subjectDTO.setSubjectId(subj.getSubjectId());
	                    subjectDTO.setSubjectName(subj.getSubjectName());
	                    return subjectDTO;
	                }).collect(Collectors.toList());

	            studentDTO.setSubjects(subjectDTOs);
	            studentDTOs.add(studentDTO);
	        }

	        return studentDTOs;
	    }
	}

	// 학생 수정 메서드
	public StudentDTO updateStudent(int studentId, StudentDTO s) {
		Optional<Student> optS = stur.findById(studentId);
		if (!optS.isPresent()) {
			return null;
		}

		Student studentEntity = optS.get();

		studentEntity.setStudentId(studentId);

		if (s.getStudentName() != null) {
			studentEntity.setStudentName(s.getStudentName());
		}

		List<StudentSubject> stusubli = new ArrayList<>();

		return null;
	}

}