package com.orm.pipes.numeric.typeLong;

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
public class LongFormatTest extends BaseFormatTest {
    @Test
    @Execution(ExecutionMode.CONCURRENT)
    void test() throws Throwable {
        this.doRegressionTest(LongFormatAnnotation.class);
    }

    @Execution(ExecutionMode.CONCURRENT)
    @ParameterizedTest(name = "\"{0}\" => \"{1}{2}\" " + "<[simple={3},nullSafe={4},blankSafe={5},noException={6}]>" + "<[format=\"{7}\",languageTag=\"{8}\"]>")
    @CsvFileSource(delimiter = ';', resources = "/com/orm/pipes/numeric/typeLong/LongFormatTestSmall.csv", numLinesToSkip = 1, nullValues = {BaseTestConfigFor.NULL_KEY})
    void csvTest(@AggregateWith(LongFormatAnnotation.class) LongFormatAnnotation baseConfigAnnotation) {
        this.doTest(baseConfigAnnotation);
    }

    public static class LongFormatAnnotation extends BaseNumericTestConfigFor<LongFormatAnnotation> implements TypeLong.New {
        public LongFormatAnnotation() {
            super();
        }

        public Class<TypeLong.New> annotationType() {
            return TypeLong.New.class;
        }

        public Class<? extends AnnotablePipe<TypeLong.New, String, Long>> read() {
            return LongPipes.ParseLong.class;
        }

        public Class<? extends AnnotablePipe<TypeLong.New, Long, String>> write() {
            return LongPipes.LongToString.class;
        }

        @Override
        public LongFormatAnnotation get() {
            return new LongFormatAnnotation();
        }
    }
}