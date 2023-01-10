package com.techLead.library.service;

import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.techLead.library.model.Book;
import com.techLead.library.repositories.BooksRepository;
import com.techLead.library.repositories.LibraryRolesRepository;
import com.techLead.library.service.exceptions.UnauthorizeException;

@Service
@Transactional
public class BookServiceImpl {

	@Autowired
	BooksRepository booksRepository;

	@Autowired
	LibraryRolesRepository rolesRepository;

	public Book save(Book book) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Object userLogged = auth.getPrincipal();
		String username = "";
		if (userLogged instanceof UserDetails) {
			username = ((UserDetails) userLogged).getUsername();
		}
		book.setRegistrationDate(LocalDateTime.now());
		book.setCreatedBy(username);
		return booksRepository.save(book);
	}

	public Book findById(Long id) {
		return booksRepository.findById(id).orElseThrow(
				() -> new ObjectNotFoundException(Book.class, String.format("Entity not found with id: ", id)));

	}

	public List<Book> findAll() {
		return booksRepository.findAll();

	}

	public Book updateBook(Book book) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Object userLogged = auth.getPrincipal();
		String username = "";
		if (userLogged instanceof UserDetails) {
			username = ((UserDetails) userLogged).getUsername();
		}
		if (!book.getCreatedBy().equals(username)
				&& !auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {

			throw new UnauthorizeException("you do not have authorization to update this item");
		}
		return booksRepository.save(book);
	}

	public void delete(Book book) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Object userLogged = auth.getPrincipal();
		String username = "";
		if (userLogged instanceof UserDetails) {
			username = ((UserDetails) userLogged).getUsername();
		}

		if (!book.getCreatedBy().equalsIgnoreCase(username)
				&& !auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
			throw new AccessDeniedException("you do not have authorization to delete this item");
		}
		booksRepository.delete(book);

	}

}
