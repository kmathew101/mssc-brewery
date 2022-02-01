package guru.springframework.msscbrewery.validations;

import javax.validation.*;
import javax.validation.constraints.NotNull;


/**
 * This example shows how to use field-level constraints. The validation is performed on Validator#validate(..) call.
 * This call returns a set of ConstraintViolation which can be used to access validation messages and other validation info.
 */
public class BeanFieldValidationExample {
    private static final Validator validator;

    static {
        Configuration<?> config = Validation.byDefaultProvider().configure();
        ValidatorFactory factory = config.buildValidatorFactory();
        validator = factory.getValidator();
        factory.close();
    }

    private static class MyBean {
        @NotNull
        private String str;

        public String getStr () {
            return str;
        }

        public void setStr (String str) {
            this.str = str;
        }
    }

    public static void main (String[] args) {
        MyBean myBean = new MyBean();
        validator.validate(myBean).stream()
                .forEach(BeanFieldValidationExample::printError);
    }

    private static void printError (ConstraintViolation<MyBean> violation) {
        System.out.println(violation.getPropertyPath()
                + " " + violation.getMessage());
    }
}
