package guru.springframework.msscbrewery.validations;

import javax.validation.*;
import java.util.Set;

/**
 * Not specifying any group, so javax.validation.groups.Default will be used.
 * see also ConstraintGroupExample1 and ConstraintGroupExample2
 */


public class ConstraintGroupExample3 {
    private static final Validator validator;

    static {
        Configuration<?> config = Validation.byDefaultProvider().configure();
        ValidatorFactory factory = config.buildValidatorFactory();
        validator = factory.getValidator();
        factory.close();
    }

    public static void main (String[] args) {
        User user = new User();
        user.setFirstName("Jennifer");
        //  user.setLastName("Wilson");

        Set<ConstraintViolation<User>> constraintViolations =
                validator.validate(user);

        if (constraintViolations.size() > 0) {
            constraintViolations.stream().forEach(
                    ConstraintGroupExample3::printError);
        } else {
            //proceed using user object
            System.out.println(user);
        }
    }

    private static void printError (ConstraintViolation<User> violation) {
        System.out.println(violation.getPropertyPath() + " " + violation.getMessage());
    }
}