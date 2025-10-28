package usecase;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CaseServiceTest {
    @InjectMocks
    private CaseService service;
    @Mock
    private PersonRepository personRepository;
    @Mock
    private AccountRepository accountRepository;
    @Spy
    private ExecutorService executorService;

    @Test
    void shouldCall() throws ExecutionException, InterruptedException {
        when(personRepository.find()).thenReturn(Collections.singletonList(new Person()));
        when(accountRepository.find()).thenReturn(Collections.singletonList(Account.builder().build()));
        Wrapper wrap = service.call();
        assertEquals(wrap, Wrapper.builder().account(Account.builder().build()).person(new Person()).build());
        verify(personRepository, times(1)).find();
        verify(accountRepository, times(1)).find();
    }



}