package com.orm.pipes.numeric.typeInteger;

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

@Execution(ExecutionMode.CONCURRENT)
public class IntegerFormatTest extends BaseFormatTest {
    @Test
    @Execution(ExecutionMode.CONCURRENT)
    void test() throws Throwable {
        this.doRegressionTest(IntegerFormatTest.IntegerFormatAnnotation.class);
    }

    @Execution(ExecutionMode.CONCURRENT)
    @ParameterizedTest(name = "\"{0}\" => \"{1}{2}\" " + "<[simple={3},nullSafe={4},blankSafe={5},noException={6}]>" + "<[format=\"{7}\",languageTag=\"{8}\"]>")
    @CsvFileSource(delimiter = ';', resources = "/com/orm/pipes/numeric/typeInteger/IntegerFormatTestSmall.csv", numLinesToSkip = 1, nullValues = {
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