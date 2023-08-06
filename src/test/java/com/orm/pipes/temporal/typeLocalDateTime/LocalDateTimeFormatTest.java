package com.orm.pipes.temporal.typeLocalDateTime;

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

import java.time.LocalDateTime;

@Execution(ExecutionMode.CONCURRENT)
public class LocalDateTimeFormatTest extends BaseFormatTest {
    @Test
    @Execution(ExecutionMode.CONCURRENT)
    void test() throws Throwable {
        this.doRegressionTest(LocalDateTimeFormatAnnotation.class);
    }

    @Execution(ExecutionMode.CONCURRENT)
    @ParameterizedTest(name = "\"{0}\" => \"{1}{2}{3}\" <[simple={4},nullSafe={5},blankSafe={6},noException={7}]><[format=\"{8}\",languageTag=\"{9}\"]>")
    @CsvFileSource(resources = "/com/orm/pipes/temporal/typeLocalDateTime/LocalDateTimeFormatTestSmall.csv", delimiter = ';', numLinesToSkip = 1, nullValues =
            {BaseTestConfigFor.NULL_KEY})
    void csvTest(@AggregateWith(LocalDateTimeFormatAnnotation.class) LocalDateTimeFormatAnnotation baseConfigAnnotation) {
        this.doTest(baseConfigAnnotation);
    }

    public static class LocalDateTimeFormatAnnotation extends BaseNumericTestConfigFor<LocalDateTimeFormatAnnotation> implements TypeLocalDateTime.New {
        public LocalDateTimeFormatAnnotation() {
            super();
        }

        public Class<TypeLocalDateTime.New> annotationType() {
            return TypeLocalDateTime.New.class;
        }

        public Class<? extends AnnotablePipe<TypeLocalDateTime.New, String, LocalDateTime>> read() {
            return LocalDateTimePipes.ParseLocalDateTime.class;
        }

        public Class<? extends AnnotablePipe<TypeLocalDateTime.New, LocalDateTime, String>> write() {
            return LocalDateTimePipes.LocalDateTimeToString.class;
        }

        @Override
        public LocalDateTimeFormatAnnotation get() {
            return new LocalDateTimeFormatAnnotation();
        }
    }
}