package com.techLead.library.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techLead.library.dtos.LibraryUserDto;
import com.techLead.library.model.LibraryRoles;
import com.techLead.library.model.LibraryUser;
import com.techLead.library.repositories.LibraryRolesRepository;
import com.techLead.library.service.LibraryServiceImpl;

@CrossOrigin
@RestController
@RequestMapping("/library")
public class LibraryController {

	@Autowired
	LibraryServiceImpl libraryServiceImpl;

	@Autowired
	LibraryRolesRepository rolesRepository;

	

	@PostMapping("/create-user")
	public ResponseEntity<LibraryUser> createUser(@RequestBody @Valid LibraryUserDto user) {		
		LibraryUser newUser = new LibraryUser();
		BeanUtils.copyProperties(user, newUser);
		
		
		return ResponseEntity.status(HttpStatus.CREATED).body(libraryServiceImpl.createUser(newUser));

	}

	@GetMapping("/all") 
	public List<LibraryUser> allUser() {
		return libraryServiceImpl.findAll();
	}

	

}
