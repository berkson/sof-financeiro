package com.sof.sof_financeiro.services;

import com.sof.sof_financeiro.api.v1.model.PaymentDto;
import com.sof.sof_financeiro.domain.Commitment;
import com.sof.sof_financeiro.domain.Expense;
import com.sof.sof_financeiro.domain.Payment;
import com.sof.sof_financeiro.mappers.PaymentMapper;
import com.sof.sof_financeiro.repository.CommitmentRepository;
import com.sof.sof_financeiro.repository.PaymentRepository;
import com.sof.sof_financeiro.util.NumberGeneratorUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Created By : Berkson Ximenes
 * Date : 28/07/2025
 **/
@Service
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;
    private final SetExpenseStatusService setExpenseStatusService;
    private final CommitmentRepository commitmentRepository;


    public PaymentServiceImpl(PaymentRepository paymentRepository, PaymentMapper paymentMapper,
                              SetExpenseStatusService setExpenseStatusService, CommitmentRepository commitmentRepository) {
        this.paymentRepository = paymentRepository;
        this.paymentMapper = paymentMapper;
        this.setExpenseStatusService = setExpenseStatusService;
        this.commitmentRepository = commitmentRepository;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public PaymentDto save(PaymentDto entity) {
        Payment payment = paymentMapper.paymentDtoToPayment(entity);
        if (payment.getId() == null) {
            Payment lastPayment = paymentRepository.findTopByOrderByIdDesc();
            String lastNumber = lastPayment != null ? lastPayment.getPaymentNumber() : "";
            payment.setPaymentNumber(NumberGeneratorUtil.getNextNumber("NP", lastNumber));
        }
        Commitment commitment = commitmentRepository.findById(payment.getCommitment().getId()).orElseThrow();
        payment.setCommitment(commitment);
        Payment savedPayment = paymentRepository.save(payment);
        setExpenseStatusService.checkAndSetStatus(commitment.getExpense());
        return paymentMapper.paymentToPaymentDto(savedPayment);
    }

    @Override
    public Optional<PaymentDto> getById(Long id) {
        return paymentRepository.findById(id)
                .map(paymentMapper::paymentToPaymentDto);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void delete(Long id) {
        Payment payment = paymentRepository.findById(id).orElseThrow();
        Expense expense = commitmentRepository.findById(payment.getCommitment().getId()).orElseThrow().getExpense();
        paymentRepository.deleteById(id);
        setExpenseStatusService.checkAndSetStatus(expense);
    }

    @Override
    public boolean existsByCommitmentId(Long id) {
        return paymentRepository.existsPaymentByCommitment_Id(id);
    }

    @Override
    public Page<PaymentDto> getPaymentsByCommitment(Long id, PageRequest pageRequest) {
        return paymentRepository.findPaymentsByCommitment_Id(id, pageRequest)
                .map(paymentMapper::paymentToPaymentDto);
    }
}
