package biz.letsweb.teka.mongo.rest.validation;

import biz.letsweb.teka.mongo.mongodb.Hardware;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 *
 * @author tomasz
 */
public class HardwareValidator implements ConstraintValidator<ValidHardware, Hardware> {

    @Override
    public void initialize(ValidHardware a) {
    }

    /**
     *
     * @param value
     * @param cvc
     * @return
     */
    @Override
    public boolean isValid(Hardware value, ConstraintValidatorContext cvc) {
        boolean isValid = true;
        final String manufacturer = value.getManufacturer();
        if (manufacturer==null || manufacturer.isEmpty()) {
            isValid = false;
        }
        final String model = value.getModel();
        if (model==null || model.isEmpty()) {
            isValid = false;
        }
        return isValid;
    }

}
