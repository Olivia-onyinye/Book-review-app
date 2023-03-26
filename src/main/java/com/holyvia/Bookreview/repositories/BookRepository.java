package com.holyvia.Bookreview.repositories;

import com.holyvia.Bookreview.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>{
    boolean existsByTitle (String title);
    Optional<Book> findByTitle (String title);
}
