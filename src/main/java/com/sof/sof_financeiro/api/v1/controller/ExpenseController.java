package com.sof.sof_financeiro.api.v1.controller;

import com.sof.sof_financeiro.api.v1.model.ExpenseDto;
import com.sof.sof_financeiro.enums.ExpenseStatus;
import com.sof.sof_financeiro.enums.ExpenseType;
import com.sof.sof_financeiro.services.ExpenseService;
import com.sof.sof_financeiro.validations.annotations.ExpenseCanBeExcluded;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ExpenseDto getPayment(@PathVariable Long id) {
        return expenseService.getById(id).orElseThrow();
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ExpenseDto save(@RequestBody @Valid ExpenseDto expense) {
        return expenseService.save(expense);
    }

    @DeleteMapping(value = "{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable @ExpenseCanBeExcluded Long id) {
        expenseService.delete(id);
    }

    @GetMapping(value = "/all")
    @ResponseStatus(HttpStatus.OK)
    public Page<ExpenseDto> getPaymentsByCommitment(@RequestParam(value = "type", required = false) ExpenseType type,
                                                    @RequestParam(value = "status", required = false) ExpenseStatus status,
                                                    @RequestParam(value = "pag", defaultValue = "0") int pag,
                                                    @RequestParam(value = "pgsize", defaultValue = "10") int pgSize,
                                                    @RequestParam(value = "ord", defaultValue = "protocolDate") String ord,
                                                    @RequestParam(value = "dir", defaultValue = "DESC") String dir) {
        PageRequest pageRequest = PageRequest.of(pag, pgSize, Sort.Direction.valueOf(dir.toUpperCase()), ord);
        return expenseService.getExpensesFiltered(type, status, pageRequest);
    }
}
