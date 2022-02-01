package guru.springframework.msscbrewery.validations;

import javax.validation.*;
import javax.validation.constraints.NotNull;

/**
 * This example shows how to use property-level constraints.
 */
public class BeanPropertyValidationExample {
    private static final Validator validator;

    static {
        Configuration<?> config = Validation.byDefaultProvider().configure();
        ValidatorFactory factory = config.buildValidatorFactory();
        validator = factory.getValidator();
        factory.close();
    }

    private static class MyBean {
        private String str;

        @NotNull
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
                .forEach(BeanPropertyValidationExample::printError);
    }

    private static void printError (ConstraintViolation<MyBean> violation) {
        System.out.println(violation.getPropertyPath()
                + " " + violation.getMessage());
    }
}
