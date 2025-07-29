package com.sof.sof_financeiro.api.v1.exception.model;

import lombok.*;

import java.util.List;

/**
 * Created By : Berkson Ximenes
 * Date : 28/07/2025
 **/

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiErrors {
    @Singular
    private List<ApiError> errors;
}