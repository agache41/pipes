package io.github.agache41.ormpipes.pipes.temporal.typeSqlDate;

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

import java.sql.Date;

@Execution(ExecutionMode.CONCURRENT)
public class SqlDateFormatTest extends BaseFormatTest {
    @Test
    @Execution(ExecutionMode.CONCURRENT)
    void test() throws Throwable {
        this.doRegressionTest(SqlDateFormatAnnotation.class);
    }

    @Execution(ExecutionMode.CONCURRENT)
    @ParameterizedTest(name = "\"{0}\" => \"{1}{2}\" " + "<[simple={3},nullSafe={4},blankSafe={5},noException={6}]>" + "<[format=\"{7}\",languageTag=\"{8}\"]>")
    @CsvFileSource(delimiter = ';', resources = "/io/github/agache41/ormpipes/pipes/temporal/typeSqlDate/SqlDateFormatTestSmall.csv", numLinesToSkip = 1, nullValues = {
            BaseTestConfigFor.NULL_KEY})
    void csvTest(@AggregateWith(SqlDateFormatAnnotation.class) SqlDateFormatAnnotation baseConfigAnnotation) {
        this.doTest(baseConfigAnnotation);
    }

    public static class SqlDateFormatAnnotation extends BaseNumericTestConfigFor<SqlDateFormatAnnotation> implements TypeSqlDate.New {
        public SqlDateFormatAnnotation() {
            super();
        }

        public Class<TypeSqlDate.New> annotationType() {
            return TypeSqlDate.New.class;
        }

        public Class<? extends AnnotablePipe<TypeSqlDate.New, String, Date>> read() {
            return SqlDatePipes.ParseSqlDate.class;
        }

        public Class<? extends AnnotablePipe<TypeSqlDate.New, Date, String>> write() {
            return SqlDatePipes.SqlDateToString.class;
        }

        /**
* {@inheritDoc}
*/
@Override
        public SqlDateFormatAnnotation get() {
            return new SqlDateFormatAnnotation();
        }
    }
}