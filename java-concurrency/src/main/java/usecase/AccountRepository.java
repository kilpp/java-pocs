package usecase;

import java.util.Collections;
import java.util.List;

public class AccountRepository {

    public List<Account> find() {
        return Collections.singletonList(new Account());
    }
}
