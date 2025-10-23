package usecase;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
    public void shouldCall() throws ExecutionException, InterruptedException {
        when(personRepository.find()).thenReturn(Collections.singletonList(new Person()));
        when(accountRepository.find()).thenReturn(Collections.singletonList(new Account()));
        Wrapper wrap = service.call();
        System.out.println(wrap);
        assertEquals(wrap, Wrapper.builder().account(new Account()).person(new Person()).build());
    }


}