package com.gk.poc.cache.repository.loan;

import com.gk.poc.cache.service.loan.Loan;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;


@Component
@Slf4j
@EnableCaching
public class LoanRepository {

    @Cacheable(cacheNames = "loan")
    public Loan findById(Long id) {
        log.info("return data");
        return Loan.builder().id(id).amount(new BigDecimal("1200")).build();
    }
}
