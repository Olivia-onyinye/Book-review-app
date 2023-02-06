package com.holyvia.Bookreview.exceptions;

import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponseApi {
    private String message;
    private String debugMessage;
    private HttpStatus status;
}
