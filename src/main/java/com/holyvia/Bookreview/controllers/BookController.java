package com.holyvia.Bookreview.controllers;

import com.holyvia.Bookreview.entities.Book;
import com.holyvia.Bookreview.services.serviceImpl.BookServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class BookController {
    private final BookServiceImpl bookService;

    @PostMapping("/admin/book/save")
    public ResponseEntity<Book> saveBookByIsbn(@Valid @RequestParam String title) {
        Book book = bookService.saveBook(title);
        return new ResponseEntity<>(book, HttpStatus.OK);
    }
}
