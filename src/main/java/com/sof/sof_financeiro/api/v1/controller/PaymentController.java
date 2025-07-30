package com.sof.sof_financeiro.api.v1.controller;

import com.sof.sof_financeiro.api.v1.model.PaymentDto;
import com.sof.sof_financeiro.services.PaymentService;
import com.sof.sof_financeiro.validations.annotations.CheckPaymentValue;
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
@RequestMapping(value = "/api/payment")
@Validated
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PaymentDto getPayment(@PathVariable Long id) {
        return paymentService.getById(id).orElseThrow();
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public PaymentDto save(@RequestBody @CheckPaymentValue @Valid PaymentDto paymentDto) {
        return paymentService.save(paymentDto);
    }

    @DeleteMapping(value = "{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        paymentService.delete(id);
    }

    @GetMapping(value = "/bycommitment/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Page<PaymentDto> getPaymentsByCommitment(@PathVariable Long id,
                                        @RequestParam(value = "pag", defaultValue = "0") int pag,
                                        @RequestParam(value = "pgsize", defaultValue = "10") int pgSize,
                                        @RequestParam(value = "ord", defaultValue = "paymentDate") String ord,
                                        @RequestParam(value = "dir", defaultValue = "DESC") String dir) {
        PageRequest pageRequest = PageRequest.of(pag, pgSize, Sort.Direction.valueOf(dir.toUpperCase()), ord);
        return paymentService.getPaymentsByCommitment(id, pageRequest);
    }
}
