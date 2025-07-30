package com.sof.sof_financeiro.api.v1.controller;

import com.sof.sof_financeiro.api.v1.model.CommitmentDto;
import com.sof.sof_financeiro.services.CommitmentService;
import com.sof.sof_financeiro.validations.annotations.CheckCommitmentValue;
import com.sof.sof_financeiro.validations.annotations.CommitmentCanBeExcluded;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * Created By : Berkson Ximenes
 * Date : 28/07/2025
 **/

@RestController
@RequestMapping(value = "/api/commitment")
@Validated
public class CommitmentController {
    private final CommitmentService commitmentService;

    public CommitmentController(CommitmentService commitmentService) {
        this.commitmentService = commitmentService;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public CommitmentDto save(@RequestBody @CheckCommitmentValue CommitmentDto commitmentDto) {
        return commitmentService.save(commitmentDto);
    }

    @DeleteMapping(value = "{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable @CommitmentCanBeExcluded Long id) {
        commitmentService.delete(id);
    }

}
