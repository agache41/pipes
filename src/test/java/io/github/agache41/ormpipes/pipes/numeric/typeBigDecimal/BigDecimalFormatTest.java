package io.github.agache41.ormpipes.pipes.numeric.typeBigDecimal;

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

import java.math.BigDecimal;

@Execution(ExecutionMode.CONCURRENT)
public class BigDecimalFormatTest extends BaseFormatTest {
    @Test
    @Execution(ExecutionMode.CONCURRENT)
    void test() throws Throwable {
        this.doRegressionTest(BigDecimalFormatTest.BigDecimalFormatAnnotation.class);
    }

    @Execution(ExecutionMode.CONCURRENT)
    @ParameterizedTest(name = "\"{0}\" => \"{1}{2}\" " + "<[simple={3},nullSafe={4},blankSafe={5},noException={6}]>" + "<[format=\"{7}\",languageTag=\"{8}\"]>")
    @CsvFileSource(delimiter = ';', resources = "/io/github/agache41/ormpipes/pipes/numeric/typeBigDecimal/BigDecimalFormatTestSmall.csv", numLinesToSkip = 1, nullValues = {
            BaseTestConfigFor.NULL_KEY})
    void csvTest(@AggregateWith(BigDecimalFormatTest.BigDecimalFormatAnnotation.class) BigDecimalFormatTest.BigDecimalFormatAnnotation baseConfigAnnotation) {
        this.doTest(baseConfigAnnotation);
    }


    public static class BigDecimalFormatAnnotation extends BaseNumericTestConfigFor<BigDecimalFormatAnnotation> implements TypeBigDecimal.New {
        public BigDecimalFormatAnnotation() {
            super();
        }

        public Class<TypeBigDecimal.New> annotationType() {
            return TypeBigDecimal.New.class;
        }

        public Class<? extends AnnotablePipe<TypeBigDecimal.New, String, BigDecimal>> read() {
            return BigDecimalPipes.ParseBigDecimal.class;
        }

        public Class<? extends AnnotablePipe<TypeBigDecimal.New, BigDecimal, String>> write() {
            return BigDecimalPipes.BigDecimalToString.class;
        }

        /**
* {@inheritDoc}
*/
@Override
        public BigDecimalFormatTest.BigDecimalFormatAnnotation get() {
            return new BigDecimalFormatTest.BigDecimalFormatAnnotation();
        }
    }
}