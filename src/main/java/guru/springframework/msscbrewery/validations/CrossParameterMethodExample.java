package guru.springframework.msscbrewery.validations;

import javax.validation.*;
import javax.validation.constraintvalidation.SupportedValidationTarget;
import javax.validation.constraintvalidation.ValidationTarget;
import javax.validation.executable.ExecutableValidator;
import java.lang.annotation.*;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.Set;

/**
 * This examples shows how to create cross-parameter constraint on methods.
 */
public class CrossParameterMethodExample {

    //Using cross param constraint on the example bean method
    public static class TradeHistoryExecutor {

        @DateRangeParams
        public void showTradeHistory (LocalDate startDate, LocalDate endDate) {
            System.out.printf("processing trade history from %s to %s %n",
                    startDate, endDate);
        }
    }

    //The constraint definition
    @Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
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

    //The  validator implementation
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

        TradeHistoryExecutor tradeHistory = new TradeHistoryExecutor();
        Method theMethod = TradeHistoryExecutor.class.getDeclaredMethod
                ("showTradeHistory", LocalDate.class, LocalDate.class);

        Validator validator = getValidator();
        ExecutableValidator executableValidator = validator.forExecutables();
        Set<ConstraintViolation<TradeHistoryExecutor>> constraintViolations =
                executableValidator.validateParameters(
                        tradeHistory,
                        theMethod,
                        new Object[]{startDate, endDate});

        if (constraintViolations.size() > 0) {
            constraintViolations.stream().forEach(
                    CrossParameterMethodExample::printError);
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

    private static void printError (ConstraintViolation<TradeHistoryExecutor> violation) {
        System.out.println(violation.getPropertyPath() + " " + violation.getMessage());
    }
}