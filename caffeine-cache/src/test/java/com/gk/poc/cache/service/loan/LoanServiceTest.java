package com.gk.poc.cache.service.loan;

import com.gk.poc.cache.repository.loan.LoanRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class LoanServiceTest {

    @Autowired
    private LoanService service;

    @SpyBean
    private LoanRepository repository;
    @Test
    public void testCache() {
        service.findById(1L);
        service.findById(1L);

        verify(repository, times(1)).findById(1L);
    }
}