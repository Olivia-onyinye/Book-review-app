package com.holyvia.Bookreview.services.serviceImpl;

import com.holyvia.Bookreview.entities.Author;
import com.holyvia.Bookreview.entities.Book;
import com.holyvia.Bookreview.exceptions.BookNotFoundException;
import com.holyvia.Bookreview.repositories.AuthorRepository;
import com.holyvia.Bookreview.repositories.BookRepository;
import com.holyvia.Bookreview.services.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
//    private final BookRepository bookRepository;
//    private final OpenLibraryApiClient openLibraryApiClient;
//    @Override
//    public ApiResponse<Book> saveBook(BookDto bookDto) {
//        ApiResponse<String> bookFromApi = openLibraryApiClient.searchBooks(bookDto.getTitle());
//        if (bookRepository.existsByTitle(bookDto.getTitle()))
//            throw new AlreadyExistsException("Kindly save another book as this one already exists");
//        Book book = new Book();
//                BeanUtils.copyProperties(bookFromApi, book);
//        bookRepository.save(book);
//        return new ApiResponse<>("Book saved successfully", true, book);
//    }
//}
//@Service
    private final RestTemplate restTemplate;
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    @Override
    public Book saveBook(String title) {
        Book optionalBook = bookRepository.findByTitle(title).get();
        String url = "https://openlibrary.org/search.json?q=";
        Book openLibraryBook = restTemplate.getForObject(url, Book.class);
//        if (openLibraryBook == null || openLibraryBook.getTitle() == null || openLibraryBook.getAuthor() == null) {
//            throw new BookNotFoundException("Sorry Book not found");
//        }
        Book book = new Book();
        assert openLibraryBook != null;
        BeanUtils.copyProperties(openLibraryBook, book);

        Author author = new Author();
        BeanUtils.copyProperties(book.getAuthor(), author);
        book.setAuthor(author);
        authorRepository.save(author);
        return bookRepository.save(book);
    }

}
