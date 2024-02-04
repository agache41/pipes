package io.github.agache41.ormpipes.pipes.numeric.typeInteger;

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

@Execution(ExecutionMode.CONCURRENT)
public class IntegerFormatTest extends BaseFormatTest {
    @Test
    @Execution(ExecutionMode.CONCURRENT)
    void test() throws Throwable {
        this.doRegressionTest(IntegerFormatTest.IntegerFormatAnnotation.class);
    }

    @Execution(ExecutionMode.CONCURRENT)
    @ParameterizedTest(name = "\"{0}\" => \"{1}{2}\" " + "<[simple={3},nullSafe={4},blankSafe={5},noException={6}]>" + "<[format=\"{7}\",languageTag=\"{8}\"]>")
    @CsvFileSource(delimiter = ';', resources = "/io/github/agache41/ormpipes/pipes/numeric/typeInteger/IntegerFormatTestSmall.csv", numLinesToSkip = 1, nullValues = {
            BaseTestConfigFor.NULL_KEY})
    void csvTest(@AggregateWith(IntegerFormatTest.IntegerFormatAnnotation.class) IntegerFormatTest.IntegerFormatAnnotation baseConfigAnnotation) {
        this.doTest(baseConfigAnnotation);
    }


    public static class IntegerFormatAnnotation extends BaseNumericTestConfigFor<IntegerFormatAnnotation> implements TypeInteger.New {
        public IntegerFormatAnnotation() {
            super();
        }

        public Class<TypeInteger.New> annotationType() {
            return TypeInteger.New.class;
        }

        public Class<? extends AnnotablePipe<TypeInteger.New, String, Integer>> read() {
            return IntegerPipes.ParseInteger.class;
        }

        public Class<? extends AnnotablePipe<TypeInteger.New, Integer, String>> write() {
            return IntegerPipes.IntegerToString.class;
        }

        @Override
        public IntegerFormatTest.IntegerFormatAnnotation get() {
            return new IntegerFormatTest.IntegerFormatAnnotation();
        }
    }
}