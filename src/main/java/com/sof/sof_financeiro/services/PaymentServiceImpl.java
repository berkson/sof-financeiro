package com.sof.sof_financeiro.services;

import com.sof.sof_financeiro.domain.Payment;
import com.sof.sof_financeiro.mappers.PaymentMapper;
import com.sof.sof_financeiro.model.PaymentDto;
import com.sof.sof_financeiro.repository.PaymentRepository;
import com.sof.sof_financeiro.util.NumberGeneratorUtil;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Created By : Berkson Ximenes
 * Date : 28/07/2025
 **/
@Service
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;


    public PaymentServiceImpl(PaymentRepository paymentRepository, PaymentMapper paymentMapper) {
        this.paymentRepository = paymentRepository;
        this.paymentMapper = paymentMapper;
    }

    @Override
    public PaymentDto save(PaymentDto entity) {
        Payment payment = paymentMapper.paymentDtoToPayment(entity);
        if (payment.getId() == null) {
            Payment lastPayment = paymentRepository.findTopByOrderByIdDesc();
            String lastNumber = lastPayment != null ? lastPayment.getPaymentNumber() : "";
            payment.setPaymentNumber(NumberGeneratorUtil.getNextNumber("NP", lastNumber));
        }
        Payment savedPayment = paymentRepository.save(payment);
        return paymentMapper.paymentToPaymentDto(savedPayment);
    }

    @Override
    public Optional<PaymentDto> getById(Long id) {
        return paymentRepository.findById(id)
                .map(paymentMapper::paymentToPaymentDto);
    }

    @Override
    public void delete(Long id) {
        paymentRepository.deleteById(id);
    }

}
