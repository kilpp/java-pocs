package validator;

import validator.Validation;

public class NotNullValidador implements Validation {

    public NotNullValidador() {
    }

    @Override
    public boolean validate(Object value, Object defaultValue) {
        return value != null;
    }
}
