package com.students.demoapi;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

@SpringBootApplication
public class DemoapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoapiApplication.class, args);
	}

	@Bean
	CommandLineRunner runner(StudentRepository repository, MongoTemplate mongotemplate) {
		return args -> {
			Address address = new Address(
					country:"England",
					city:"Sunderland",
					postCode:"PK16"
					);
			
			Student student = new Student(
			       firstName:"Ellie",
			       lastName:"Schoner",
			       email:"elschoner@gmail.com",
			       Gender.FEMALE,
			       address,
			       List.of("Computer Science", "Maths"),
			       BigDecimal.TEN,
			       LocalDateTime.now()
			       );
      
			
	    // usingMongoTemplateAndQuery(repository, mongotemplate, student);
	    
		repository.findStudentByEmail(email)
		        .ifPresentOrElse(s -> {
		        	System.out.println(s + " already exits"); 	
		        }, ()->{
			    	System.out.println("Inserting student " + student);
			    	repository.insert(student);	
		        });
	    
	 };
	
  }

	private void usingMongoTemplateAndQuery(StudentRepository repository, MongoTemplate mongotemplate,
			Student student) {
		Query query = new Query();
	    query.addCriteria(Criteria.where("email").is(email));
	    
	    List<Student> students = mongotemplate.find(query, Student.class);
	    
	    
	    if (students.size() > 1) {
	    	throw new IllegalStateException("found many students with email " + email);
	    }
	    
	    if (students.isEmpty()) {
	    	System.out.println("Inserting student " + student);
	    	repository.insert(student);	
	    } else {
	    	System.out.println(student + " already exits");
	    }
	}
	
	
}
