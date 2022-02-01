package guru.springframework.msscbrewery.validations;

import javax.validation.*;
import java.util.Set;

/**
 * This examples shows how to validate a group of constraints using 'group' element of constraint annotations to
 * specify a group and then calling Validator#validate(T object, Class<?> groups) method.
 * Here we are creating a group:
 */

public class ConstraintGroupExample {
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
                validator.validate(user, GroupUserName.class);

        if (constraintViolations.size() > 0) {
            constraintViolations.stream().forEach(
                    ConstraintGroupExample::printError);
        } else {
            //proceed using user object
            System.out.println(user);
        }
    }

    private static void printError (ConstraintViolation<User> violation) {
        System.out.println(violation.getPropertyPath() + " " + violation.getMessage());
    }
}
