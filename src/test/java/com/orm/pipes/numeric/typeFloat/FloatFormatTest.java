package com.orm.pipes.numeric.typeFloat;

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
public class FloatFormatTest extends BaseFormatTest {
    @Test
    @Execution(ExecutionMode.CONCURRENT)
    void test() throws Throwable {
        this.doRegressionTest(FloatFormatTest.FloatFormatAnnotation.class);
    }

    @Execution(ExecutionMode.CONCURRENT)
    @ParameterizedTest(name = "\"{0}\" => \"{1}{2}\" " + "<[simple={3},nullSafe={4},blankSafe={5},noException={6}]>" + "<[format=\"{7}\",languageTag=\"{8}\"]>")
    @CsvFileSource(delimiter = ';', resources = "/com/orm/pipes/numeric/typeFloat/FloatFormatTestSmall.csv", numLinesToSkip = 1, nullValues = {
            BaseTestConfigFor.NULL_KEY})
    void csvTest(@AggregateWith(FloatFormatTest.FloatFormatAnnotation.class) FloatFormatTest.FloatFormatAnnotation baseConfigAnnotation) {
        this.doTest(baseConfigAnnotation);
    }


    public static class FloatFormatAnnotation extends BaseNumericTestConfigFor<FloatFormatAnnotation> implements TypeFloat.New {
        public FloatFormatAnnotation() {
            super();
        }

        public Class<TypeFloat.New> annotationType() {
            return TypeFloat.New.class;
        }

        public Class<? extends AnnotablePipe<TypeFloat.New, String, Float>> read() {
            return FloatPipes.ParseFloat.class;
        }

        public Class<? extends AnnotablePipe<TypeFloat.New, Float, String>> write() {
            return FloatPipes.FloatToString.class;
        }

        @Override
        public FloatFormatTest.FloatFormatAnnotation get() {
            return new FloatFormatTest.FloatFormatAnnotation();
        }
    }
}