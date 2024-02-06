package io.github.agache41.ormpipes.pipes.temporal.typeLocalTime;

import io.github.agache41.ormpipes.pipe.AnnotablePipe;

import io.github.agache41.ormpipes.pipes.numeric.BaseFormatTest;
import io.github.agache41.ormpipes.pipes.numeric.BaseNumericTestConfigFor;
import io.github.agache41.ormpipes.pipes.numeric.BaseTestConfigFor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.AggregateWith;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.time.LocalTime;

@Execution(ExecutionMode.CONCURRENT)
public class LocalTimeFormatTest extends BaseFormatTest {
    @Test
    @Execution(ExecutionMode.CONCURRENT)
    void test() throws Throwable {
        this.doRegressionTest(LocalTimeFormatAnnotation.class);
    }

    @Execution(ExecutionMode.CONCURRENT)
    @ParameterizedTest(name = "\"{0}\" => \"{1}{2}{3}\" <[simple={4},nullSafe={5},blankSafe={6},noException={7}]><[format=\"{8}\",languageTag=\"{9}\"]>")
    @CsvFileSource(resources = "/io/github/agache41/ormpipes/pipes/temporal/typeLocalTime/LocalTimeFormatTestSmall.csv", delimiter = ';', numLinesToSkip = 1, nullValues =
            {BaseTestConfigFor.NULL_KEY})
    void csvTest(@AggregateWith(LocalTimeFormatAnnotation.class) LocalTimeFormatAnnotation baseConfigAnnotation) {
        this.doTest(baseConfigAnnotation);
    }

    public static class LocalTimeFormatAnnotation extends BaseNumericTestConfigFor<LocalTimeFormatAnnotation> implements TypeLocalTime.New {
        public LocalTimeFormatAnnotation() {
            super();
        }

        public Class<TypeLocalTime.New> annotationType() {
            return TypeLocalTime.New.class;
        }

        public Class<? extends AnnotablePipe<TypeLocalTime.New, String, LocalTime>> read() {
            return LocalTimePipes.ParseLocalTime.class;
        }

        public Class<? extends AnnotablePipe<TypeLocalTime.New, LocalTime, String>> write() {
            return LocalTimePipes.LocalTimeToString.class;
        }

        /**
* {@inheritDoc}
*/
@Override
        public LocalTimeFormatAnnotation get() {
            return new LocalTimeFormatAnnotation();
        }
    }
}