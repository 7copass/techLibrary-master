package com.techLead.library.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.techLead.library.model.LibraryRoles;
import com.techLead.library.model.LibraryUser;
import com.techLead.library.repositories.LibraryRolesRepository;
import com.techLead.library.repositories.UserRepository;
import com.techLead.library.service.exceptions.ObjectNotFounException;

@Service
@Transactional
public class LibraryServiceImpl {

	@Autowired
	UserRepository userRepository;

	@Autowired
	LibraryRolesRepository rolesRepository;

	@Autowired
	PasswordEncoder encoder;

	public LibraryUser createUser(LibraryUser user) {
		LibraryRoles roles = rolesRepository.findById(2L).get();
		user.addRole(roles);
		user.setPassword(encoder.encode(user.getPassword()));
		return userRepository.save(user);
	}

	public LibraryUser findById(Long id) {
		Optional<LibraryUser> user = userRepository.findById(id);
		return user.orElseThrow(() -> new ObjectNotFounException(
				"Object not found! Id: " + id + ", Type: " + LibraryUser.class.getName()));
	}

	public List<LibraryUser> findAll() {

		return userRepository.findAll();
	}

}
