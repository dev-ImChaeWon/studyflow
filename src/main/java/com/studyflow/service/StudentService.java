package com.studyflow.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.studyflow.dto.HomeworkDTO;
import com.studyflow.dto.StudentDTO;
import com.studyflow.entity.Homework;
import com.studyflow.entity.Student;
import com.studyflow.repository.HomeworkRepository;
import com.studyflow.repository.StudentRepository;

@Service
public class StudentService {

	StudentRepository stur;
	HomeworkRepository homr;

	@Autowired
	public StudentService(StudentRepository stur, HomeworkRepository homr) {
		this.stur = stur;
		this.homr = homr;
	}

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
				homdto.getStudent().setStudentName(hm.getStudent().getStudentName());
				homdto.getSubject().setSubjectName(hm.getSubject().getSubjectName());
				homdto.setHomeworkPage(hm.getHomeworkPage());
				homdto.setCompletedPage(hm.getCompletedPage());
				homdto.setComment(hm.getComment());
			}
			studto.setHomework(homework);

		}
		return studto;
	}
}
