package biz.letsweb.rest.one.mvc.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 *
 * @author tomasz
 */
public class NameValidator implements ConstraintValidator<ValidName, String>{

    @Override
    public void initialize(ValidName a) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext cvc) {
        return value == null || value.equals("tomasz");
    }
    
}
