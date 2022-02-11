package com.students.demoapi;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class StudentService {
	
	private final StudentRepository studentRepository;

	public List<Student> getAllStudents(){
		return studentRepository.findAll();
	}
}
