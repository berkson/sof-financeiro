package com.sof.sof_financeiro.api.v1.exception.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.List;

/**
 * Created By : Berkson Ximenes
 * Date : 28/07/2025
 **/

@Getter
@Setter
@NoArgsConstructor
public class ValidationError extends ApiError {
    public ValidationError(HttpStatus status, String message, String path, List<String> details) {
        super(status, message, path, details);
    }
}
