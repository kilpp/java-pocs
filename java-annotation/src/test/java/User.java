import annotations.CustomValidator;
import annotations.Min;
import validator.MinValidator;
import validator.NotNullValidador;

import java.util.Objects;

public class User {

    @Min(value = 15, validator = MinValidator.class)
    @CustomValidator(validator = NotNullValidador.class)
    private Integer age;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return Objects.equals(age, user.age);
    }

    @Override
    public int hashCode() {
        return age != null ? age.hashCode() : 0;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "age=" + age +
                '}';
    }
}
