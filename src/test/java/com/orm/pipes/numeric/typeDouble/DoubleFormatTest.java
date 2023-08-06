package com.orm.pipes.numeric.typeDouble;

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
public class DoubleFormatTest extends BaseFormatTest {
    @Test
    @Execution(ExecutionMode.CONCURRENT)
    void test() throws Throwable {
        this.doRegressionTest(DoubleFormatTest.DoubleFormatAnnotation.class);
    }

    @Execution(ExecutionMode.CONCURRENT)
    @ParameterizedTest(name = "\"{0}\" => \"{1}{2}\" " + "<[simple={3},nullSafe={4},blankSafe={5},noException={6}]>" + "<[format=\"{7}\",languageTag=\"{8}\"]>")
    @CsvFileSource(delimiter = ';', resources = "/com/orm/pipes/numeric/typeDouble/DoubleFormatTestSmall.csv", numLinesToSkip = 1, nullValues = {
            BaseTestConfigFor.NULL_KEY})
    void csvTest(@AggregateWith(DoubleFormatTest.DoubleFormatAnnotation.class) DoubleFormatTest.DoubleFormatAnnotation baseConfigAnnotation) {
        this.doTest(baseConfigAnnotation);
    }


    public static class DoubleFormatAnnotation extends BaseNumericTestConfigFor<DoubleFormatAnnotation> implements TypeDouble.New {
        public DoubleFormatAnnotation() {
            super();
        }

        public Class<TypeDouble.New> annotationType() {
            return TypeDouble.New.class;
        }

        public Class<? extends AnnotablePipe<TypeDouble.New, String, Double>> read() {
            return DoublePipes.ParseDouble.class;
        }

        public Class<? extends AnnotablePipe<TypeDouble.New, Double, String>> write() {
            return DoublePipes.DoubleToString.class;
        }

        @Override
        public DoubleFormatTest.DoubleFormatAnnotation get() {
            return new DoubleFormatTest.DoubleFormatAnnotation();
        }
    }
}