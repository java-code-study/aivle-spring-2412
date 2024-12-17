package ac.kyungnam.book.valid;

import ac.kyungnam.book.domain.Book;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;

public class StatusValidator implements ConstraintValidator<ValidStatus, String> {
    @Override
    public void initialize(ValidStatus constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return Arrays.stream(Book.Status.values())
                .anyMatch(status -> status.name().equals(value));
    }
}
