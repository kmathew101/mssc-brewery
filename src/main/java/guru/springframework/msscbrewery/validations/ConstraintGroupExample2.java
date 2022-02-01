package guru.springframework.msscbrewery.validations;

import javax.validation.*;
import javax.validation.groups.Default;
import java.util.*;
import java.util.stream.Collectors;


/**
 * This examples shows how to validate a group of constraints using 'group' element of constraint annotations to
 * specify a group and then calling Validator#validate(T object, Class<?> groups) method.
 * Here we are creating a group:
 */

public class ConstraintGroupExample2 {
    private static final Validator validator;

    static {
        Configuration<?> config = Validation.byDefaultProvider().configure();
        ValidatorFactory factory = config.buildValidatorFactory();
        validator = factory.getValidator();
        factory.close();
    }

    public static void main(String[] args) {
        User user = new User();
        user.setFirstName("Jennifer");
        //  user.setLastName("Wilson");

        Set<ConstraintViolation<User>> constraintViolations =
                validator.validate(user, GroupUserName.class,
                        GroupAddress.class, Default.class);

        if (constraintViolations.size() > 0) {
            Map<String, List<ConstraintViolation<User>>> groupViolationsMap =
                    constraintViolations.stream().collect(
                            Collectors.groupingBy(v ->
                                    v.getConstraintDescriptor().getGroups().iterator()
                                            .next().getSimpleName(), TreeMap::new, Collectors.toList()));

            groupViolationsMap.forEach((k, v) -> {
                System.out.printf("%n-- Group: %s --%n", k);
                v.stream().sorted(Comparator.comparing(o -> o.getPropertyPath().toString()))
                        .forEach(ConstraintGroupExample2::printError);

            });
        } else {
            //proceed using user object
            System.out.println(user);
        }
    }

    private static void printError(ConstraintViolation<User> violation) {
        System.out.println(violation.getPropertyPath() + " " + violation.getMessage());
    }
}