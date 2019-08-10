package com.test.springboot.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.springboot.dao.model.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {
	
	Person findPersonById(Long id);
}

