package com.studyflow.entity;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Subject {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer subjectId;
	private String subjectName;

	@ManyToOne
	@JoinColumn(name = "teacher_id", nullable = false)
	private Teacher teacher;

	@OneToMany(mappedBy = "subject", cascade = CascadeType.ALL)
	private List<Homework> homework = new ArrayList<>();

	@OneToMany(mappedBy = "subject", cascade = CascadeType.ALL)
	private List<StudentSubject> studentSubjects = new ArrayList<>();

	@OneToMany(mappedBy = "subject", cascade = CascadeType.ALL)
	private List<TestScore> testScore = new ArrayList<>();

	public List<StudentSubject> getStudentSubjects() {
		return studentSubjects;
	}

	public void setStudentSubjects(List<StudentSubject> studentSubjects) {
		this.studentSubjects = studentSubjects;
	}

	public List<TestScore> getTestScore() {
		return testScore;
	}

	public void setTestScore(List<TestScore> testScore) {
		this.testScore = testScore;
	}

	public Integer getSubjectId() {
		return subjectId;
	}

	public List<Homework> getHomework() {
		return homework;
	}

	public void setHomework(List<Homework> homework) {
		this.homework = homework;
	}

	public void setSubjectId(Integer subjectId) {
		this.subjectId = subjectId;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

}
