package com.orm.pipes.temporal.typeLocalDate;

import com.orm.pipe.AnnotablePipe;
import com.orm.pipes.numeric.BaseFormatTest;
import com.orm.pipes.numeric.BaseNumericTestConfigFor;
import com.orm.pipes.numeric.BaseTestConfigFor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.AggregateWith;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.time.LocalDate;

@Execution(ExecutionMode.CONCURRENT)
public class LocalDateFormatTest extends BaseFormatTest {
    @Test
    @Execution(ExecutionMode.CONCURRENT)
    void test() throws Throwable {
        this.doRegressionTest(LocalDateFormatAnnotation.class);
    }

    @Execution(ExecutionMode.CONCURRENT)
    @ParameterizedTest(name = "\"{0}\" => \"{1}{2}\" " + "<[simple={3},nullSafe={4},blankSafe={5},noException={6}]>" + "<[format=\"{7}\",languageTag=\"{8}\"]>")
    @CsvFileSource(delimiter = ';', resources = "/com/orm/pipes/temporal/typeLocalDate/LocalDateFormatTestSmall.csv", numLinesToSkip = 1, nullValues = {
            BaseTestConfigFor.NULL_KEY})
    void csvTest(@AggregateWith(LocalDateFormatAnnotation.class) LocalDateFormatAnnotation baseConfigAnnotation) {
        this.doTest(baseConfigAnnotation);
    }

    public static class LocalDateFormatAnnotation extends BaseNumericTestConfigFor<LocalDateFormatAnnotation> implements TypeLocalDate.New {
        public LocalDateFormatAnnotation() {
            super();
        }

        public Class<TypeLocalDate.New> annotationType() {
            return TypeLocalDate.New.class;
        }

        public Class<? extends AnnotablePipe<TypeLocalDate.New, String, LocalDate>> read() {
            return LocalDatePipes.ParseLocalDate.class;
        }

        public Class<? extends AnnotablePipe<TypeLocalDate.New, LocalDate, String>> write() {
            return LocalDatePipes.LocalDateToString.class;
        }

        @Override
        public LocalDateFormatAnnotation get() {
            return new LocalDateFormatAnnotation();
        }
    }
}