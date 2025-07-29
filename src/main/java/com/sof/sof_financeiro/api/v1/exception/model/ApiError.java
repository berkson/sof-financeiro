package com.sof.sof_financeiro.api.v1.exception.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.http.HttpStatusCode;

import java.util.List;

/**
 * Created By : Berkson Ximenes
 * Date : 28/07/2025
 **/

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class ApiError {
    private int status;
    private String message;
    private String path;
    private String traceId;
    private List<String> details;

    public ApiError(HttpStatusCode status, String message, String path) {
        super();
        this.status = status.value();
        this.message = message;
        this.path = path;
        this.traceId = traceId;
    }

    public ApiError(HttpStatusCode status, String message, String path, List<String> details) {
        super();
        this.status = status.value();
        this.message = message;
        this.path = path;
        this.traceId = traceId;
        this.details = details;
    }
}
