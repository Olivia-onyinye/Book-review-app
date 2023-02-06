package com.holyvia.Bookreview.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "book_tbl")
public class Book extends BaseEntity{

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

    @JsonIgnore
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Review> reviews;


}
