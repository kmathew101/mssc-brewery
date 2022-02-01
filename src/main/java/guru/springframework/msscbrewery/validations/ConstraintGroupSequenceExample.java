package guru.springframework.msscbrewery.validations;

import javax.validation.*;
import java.util.Set;

/**
 * @GroupSequence example
 * This example shows how to use @GroupSequence to validate groups in order, one by one.
 * We are using same bean and groups from last example.
 */

public class ConstraintGroupSequenceExample {
    private static final Validator validator;

    static {
        Configuration<?> config = Validation.byDefaultProvider().configure();
        ValidatorFactory factory = config.buildValidatorFactory();
        validator = factory.getValidator();
        factory.close();
    }

    public static void main (String[] args) {
        User user = new User();

        Set<ConstraintViolation<User>> constraintViolations =
                validator.validate(user, GroupSequenceForUser.class);

        if (constraintViolations.size() > 0) {
            constraintViolations.stream().forEach(
                    ConstraintGroupSequenceExample::printError);
        } else {
            //proceed using user object
            System.out.println(user);
        }
    }

    private static void printError (ConstraintViolation<User> violation) {
        System.out.println(violation.getPropertyPath() + " " + violation.getMessage());
    }
}