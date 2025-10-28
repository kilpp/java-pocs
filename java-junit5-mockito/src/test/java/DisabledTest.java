import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

@Disabled("BUG")
public class DisabledTest {

    @Disabled("BUG")
    @Test
    void bugged() {}
}
