package com.techLead.library.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AliasFor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.techLead.library.dtos.BookDto;
import com.techLead.library.model.Book;
import com.techLead.library.repositories.LibraryRolesRepository;
import com.techLead.library.service.BookServiceImpl;
import com.techLead.library.service.LibraryServiceImpl;

@CrossOrigin(origins = "http://localhost:4200/")
@RestController
@RequestMapping("/books")
public class BookController {

	@Autowired
	BookServiceImpl bookServiceImpl; 

	@Autowired
	LibraryServiceImpl libraryServiceImpl;

	@Autowired
	LibraryRolesRepository rolesRepository;
	
	
	@GetMapping("/")
	public String login(){
		return "authenticated successfully" ;
	}

	@PostMapping("/register-book")
	public ResponseEntity<Book> createBook(@RequestBody @Valid BookDto book) {
		Book newBook = new Book();
		BeanUtils.copyProperties(book, newBook);
		return ResponseEntity.status(HttpStatus.CREATED).body(bookServiceImpl.save(newBook));

	}

	@GetMapping("/all-books")
	public ResponseEntity<List<Book>> allBooks() {
		return ResponseEntity.status(HttpStatus.OK).body(bookServiceImpl.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Book> detailBook(@PathVariable Long id) {
		return ResponseEntity.status(HttpStatus.OK).body(bookServiceImpl.findById(id));

	}

	@PutMapping("update/{idBook}")
	public ResponseEntity<?> updateBook(@PathVariable Long idBook, @RequestBody BookDto bookDto) {
		System.out.println("Finding book with ID: " + idBook);
		Book bookEdit = bookServiceImpl.findById(idBook);
		System.out.println("Updating book with ID: " + bookEdit.getId());
		BeanUtils.copyProperties(bookDto, bookEdit, "id");
		System.out.println("Updating book with ID: " + bookEdit.getId() + bookEdit.getName());
		return ResponseEntity.status(HttpStatus.OK).body(bookServiceImpl.updateBook(bookEdit));
	}

	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	@DeleteMapping("/delete/{idBook}")
	public void deleteBook(@PathVariable Long idBook) {
		Book bookDelete = bookServiceImpl.findById(idBook);
		bookServiceImpl.delete(bookDelete);
	}

}
