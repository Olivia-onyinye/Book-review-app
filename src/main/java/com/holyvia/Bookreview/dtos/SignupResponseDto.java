package com.holyvia.Bookreview.dtos;

import com.holyvia.Bookreview.enums.Gender;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SignupResponseDto {
    private String firstName;
    private String lastName;
    private String email;
    private Gender gender;
    private String phoneNumber;
    private Boolean verificationStatus;
}
