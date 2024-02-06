package io.github.agache41.ormpipes.pipes.numeric.typeBigInteger;

import io.github.agache41.ormpipes.pipe.AnnotablePipe;
import io.github.agache41.ormpipes.pipes.numeric.BaseFormatTest;
import io.github.agache41.ormpipes.pipes.numeric.BaseNumericTestConfigFor;
import io.github.agache41.ormpipes.pipes.numeric.BaseTestConfigFor;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.AggregateWith;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.math.BigInteger;

@Execution(ExecutionMode.CONCURRENT)
public class BigIntegerFormatTest extends BaseFormatTest {
    @Execution(ExecutionMode.CONCURRENT)
    @ParameterizedTest(name = "\"{0}\" => \"{1}{2}\" " + "<[simple={3},nullSafe={4},blankSafe={5},noException={6}]>" + "<[format=\"{7}\",languageTag=\"{8}\"]>")
    @CsvFileSource(delimiter = ';', resources = "/io/github/agache41/ormpipes/pipes/numeric/typeBigInteger/BigIntegerFormatTestSmall.csv", numLinesToSkip = 1, nullValues = {
            BaseTestConfigFor.NULL_KEY})
    void csvTest(@AggregateWith(BigIntegerFormatAnnotation.class) BigIntegerFormatAnnotation baseConfigAnnotation) {
        this.doTest(baseConfigAnnotation);
    }


    public static class BigIntegerFormatAnnotation extends BaseNumericTestConfigFor<BigIntegerFormatAnnotation> implements TypeBigInteger.New {
        public BigIntegerFormatAnnotation() {
            super();
        }

        public Class<TypeBigInteger.New> annotationType() {
            return TypeBigInteger.New.class;
        }

        public Class<? extends AnnotablePipe<TypeBigInteger.New, String, BigInteger>> read() {
            return BigIntegerPipes.ParseBigInteger.class;
        }

        public Class<? extends AnnotablePipe<TypeBigInteger.New, BigInteger, String>> write() {
            return BigIntegerPipes.BigIntegerToString.class;
        }

        /**
* {@inheritDoc}
*/
@Override
        public BigIntegerFormatAnnotation get() {
            return new BigIntegerFormatAnnotation();
        }
    }
}