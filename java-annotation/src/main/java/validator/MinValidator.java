package validator;

public class MinValidator implements Validation {

    public MinValidator() {
    }

    @Override
    public boolean validate(Object value, Object defaultValue) {
        return value != null && (Integer) value > (Integer) defaultValue;
    }
}
