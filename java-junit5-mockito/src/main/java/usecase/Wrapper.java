package usecase;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
public class Wrapper {
    private Person person;
    private Account account;
}
