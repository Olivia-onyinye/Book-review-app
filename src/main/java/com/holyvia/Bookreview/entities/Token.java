package com.holyvia.Bookreview.entities;

import com.holyvia.Bookreview.enums.TokenStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "token_tbl")
@Getter
@Setter
public class Token extends BaseEntity{
    @Column(length = 500)
    private String token;

    @Enumerated(EnumType.STRING)
    private TokenStatus tokenStatus;

    @OneToOne()
    @JoinColumn(name = "person_tbl_id")
    private User user;
}
