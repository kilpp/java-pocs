package usecase;

import java.util.Collections;
import java.util.List;

public class PersonRepository {

    public List<Person> find() {
        return Collections.singletonList(new Person());
    }
}
