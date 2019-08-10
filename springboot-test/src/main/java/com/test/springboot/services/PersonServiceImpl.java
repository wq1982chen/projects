package com.test.springboot.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.springboot.dao.PersonRepository;
import com.test.springboot.dao.PersonService;
import com.test.springboot.dao.model.Person;

@Service
public class PersonServiceImpl implements PersonService {

	@Autowired
	private PersonRepository personRepository;
	
	@Override
	public Person savePerson(Person person) {
		return personRepository.save(person);
	}

	@Override
	public Person getPerson(Long id) {
		System.out.println("To find Person Through MySQL...");
		return personRepository.findPersonById(id);
	}

}
