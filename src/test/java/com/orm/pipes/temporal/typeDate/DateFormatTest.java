package com.orm.pipes.temporal.typeDate;

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

import java.util.Date;

@Execution(ExecutionMode.CONCURRENT)
public class DateFormatTest extends BaseFormatTest {
    @Test
    @Execution(ExecutionMode.CONCURRENT)
    void test() throws Throwable {
        this.doRegressionTest(DateFormatAnnotation.class);
    }

    @Execution(ExecutionMode.CONCURRENT)
    @ParameterizedTest(name = "\"{0}\" => \"{1}{2}\" " + "<[simple={3},nullSafe={4},blankSafe={5},noException={6}]>" + "<[format=\"{7}\",languageTag=\"{8}\"]>")
    @CsvFileSource(delimiter = ';', resources = "/com/orm/pipes/temporal/typeDate/DateFormatTestSmall.csv", numLinesToSkip = 1, nullValues = {
            BaseTestConfigFor.NULL_KEY})
    void csvTest(@AggregateWith(DateFormatAnnotation.class) DateFormatAnnotation baseConfigAnnotation) {
        this.doTest(baseConfigAnnotation);
    }

    public static class DateFormatAnnotation extends BaseNumericTestConfigFor<DateFormatAnnotation> implements TypeDate.New {
        public DateFormatAnnotation() {
            super();
        }

        public Class<TypeDate.New> annotationType() {
            return TypeDate.New.class;
        }

        public Class<? extends AnnotablePipe<TypeDate.New, String, Date>> read() {
            return DatePipes.ParseDate.class;
        }

        public Class<? extends AnnotablePipe<TypeDate.New, Date, String>> write() {
            return DatePipes.DateToString.class;
        }

        @Override
        public DateFormatAnnotation get() {
            return new DateFormatAnnotation();
        }
    }
}