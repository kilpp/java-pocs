package com.gk.poc.cache.service.loan;

import com.gk.poc.cache.repository.loan.LoanRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@Slf4j
@RequiredArgsConstructor
public class LoanService {

    private final LoanRepository repository;


    public Loan findById(Long id) {
        return repository.findById(id);
    }
}
