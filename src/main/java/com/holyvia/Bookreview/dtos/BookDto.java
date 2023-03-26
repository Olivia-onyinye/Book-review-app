package com.holyvia.Bookreview.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.holyvia.Bookreview.entities.Author;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Builder
@ToString
public class BookDto {
    @NotNull(message = "Title cannot be missing or empty")
    @Column(nullable = false, length = 100)
    private String title;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;

    @NotNull(message = "Description cannot be missing or empty")
    @Column(nullable = false, length = 300)
    private String description;

    private String publication_date;

    private String coverImage;
    private String isbn;
}
