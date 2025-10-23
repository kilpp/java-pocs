package usecase;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@AllArgsConstructor
public class CaseService implements Callable<Wrapper> {

    private PersonRepository personRepository;
    private AccountRepository accountRepository;
    private final ExecutorService executorService = Executors.newFixedThreadPool(5);


    public Wrapper call() throws ExecutionException, InterruptedException {
        CompletableFuture<List<Account>> cfAccount = CompletableFuture.supplyAsync(() -> accountRepository.find(), executorService);
        CompletableFuture<List<Person>> cfPerson = CompletableFuture.supplyAsync(() -> personRepository.find(), executorService);
        return CompletableFuture.allOf(cfAccount, cfPerson).thenApply(a ->
                Wrapper.builder()
                        .account(cfAccount.join().get(0))
                        .person(cfPerson.join().get(0)).build()

        ).get();
    }

}
