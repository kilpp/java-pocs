import org.junit.jupiter.api.Test;
import constraint.Constraint;
import validator.Validator;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ValidatorTest {

    @Test
    void validate() {
        Account account = new Account();
        account.setNumber(333);
        User user = new User();
        List<Constraint> accountConstraints = Validator.validate(account);
        List<Constraint> userConstraints = Validator.validate(user);
        assertNotNull(accountConstraints);
        assertNotNull(userConstraints);
        assertEquals(1, accountConstraints.size());
        assertEquals(2, userConstraints.size());
    }
}