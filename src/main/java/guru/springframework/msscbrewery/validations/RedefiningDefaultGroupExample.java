package guru.springframework.msscbrewery.validations;

import javax.validation.*;
import java.util.Set;

public class RedefiningDefaultGroupExample {
    private static final Validator validator;

    static {
        Configuration<?> config = Validation.byDefaultProvider().configure();
        ValidatorFactory factory = config.buildValidatorFactory();
        validator = factory.getValidator();
        factory.close();
    }

    public static void main (String[] args) {
        User2 user = new User2();

        Set<ConstraintViolation<User2>> constraintViolations =
                validator.validate(user);

        if (constraintViolations.size() > 0) {
            constraintViolations.stream().forEach(
                    RedefiningDefaultGroupExample::printError);
        } else {
            //proceed using user object
            System.out.println(user);
        }
    }

    private static void printError (ConstraintViolation<User2> violation) {
        System.out.println(violation.getPropertyPath() + " " + violation.getMessage());
    }
}
