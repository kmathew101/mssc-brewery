package guru.springframework.msscbrewery.validations;

import javax.validation.*;
import javax.validation.constraintvalidation.SupportedValidationTarget;
import javax.validation.constraintvalidation.ValidationTarget;
import javax.validation.executable.ExecutableValidator;
import java.lang.annotation.*;
import java.lang.reflect.Constructor;
import java.time.LocalDate;
import java.util.Set;

/**
 * This examples shows how to create cross-parameter constraint on constructors.
 */
public class CrossParameterConstructorExample {

    //Using cross param constraint on the example bean constructor
    public static class TradeHistory {
        private final LocalDate startDate;
        private final LocalDate endDate;

        @DateRangeParams
        public TradeHistory (LocalDate startDate, LocalDate endDate) {
            this.startDate = startDate;
            this.endDate = endDate;
        }

        public LocalDate getStartDate () {
            return startDate;
        }

        public LocalDate getEndDate () {
            return endDate;
        }
    }

    //the constraint definition
    @Target({ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Constraint(validatedBy = DateRangeValidator.class)
    @Documented
    public static @interface DateRangeParams {
        String message() default "'start date' must be less than 'end date'. " +
                "Found: 'start date'=${validatedValue[0]}, " +
                "'end date'=${validatedValue[1]}";

        Class<?>[] groups() default {};

        Class<? extends Payload>[] payload() default {};
    }

    //the validator implementation
    @SupportedValidationTarget(ValidationTarget.PARAMETERS)
    public static class DateRangeValidator implements
            ConstraintValidator<DateRangeParams, Object[]> {
        @Override
        public void initialize (DateRangeParams constraintAnnotation) {
        }

        @Override
        public boolean isValid (Object[] value, ConstraintValidatorContext context) {
            if (value == null || value.length != 2 ||
                    !(value[0] instanceof LocalDate) ||
                    !(value[1] instanceof LocalDate)) {
                return false;
            }

            return ((LocalDate) value[0]).isBefore((LocalDate) value[1]);
        }
    }

    //performing validation
    public static void main (String[] args) throws NoSuchMethodException {
        LocalDate startDate = LocalDate.of(2021, 8, 10);
        LocalDate endDate = LocalDate.of(2021, 7, 1);

        TradeHistory tradeHistory = new TradeHistory(startDate, endDate);

        Constructor<TradeHistory> constructor = TradeHistory.class.getConstructor(LocalDate
                .class, LocalDate.class);

        Validator validator = getValidator();
        ExecutableValidator executableValidator = validator.forExecutables();
        Set<ConstraintViolation<TradeHistory>> constraintViolations =
                executableValidator.validateConstructorParameters(constructor,
                        new Object[]{startDate, endDate});

        if (constraintViolations.size() > 0) {
            constraintViolations.stream().forEach(
                    CrossParameterConstructorExample::printError);
        } else {
            //proceed using order
            System.out.println(tradeHistory);
        }
    }

    private static Validator getValidator(){
        Configuration<?> config = Validation.byDefaultProvider().configure();
        ValidatorFactory factory = config.buildValidatorFactory();
        Validator validator = factory.getValidator();
        factory.close();
        return validator;
    }

    private static void printError (ConstraintViolation<TradeHistory> violation) {
        System.out.println(violation.getPropertyPath() + " " + violation.getMessage());
    }


}
