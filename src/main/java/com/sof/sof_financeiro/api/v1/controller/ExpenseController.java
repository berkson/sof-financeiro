package com.sof.sof_financeiro.api.v1.controller;

import com.sof.sof_financeiro.validations.annotations.ExpenseCanBeExcluded;
import com.sof.sof_financeiro.api.v1.model.ExpenseDto;
import com.sof.sof_financeiro.services.ExpenseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * Created By : Berkson Ximenes
 * Date : 28/07/2025
 **/

@RestController
@RequestMapping(value = "/api/expense")
@Validated
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ExpenseDto save(@RequestBody ExpenseDto expense) {
        return expenseService.save(expense);
    }

    @DeleteMapping(value = "{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable @ExpenseCanBeExcluded Long id) {
        expenseService.delete(id);
    }
}
