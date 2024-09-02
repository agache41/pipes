/*
 *    Copyright 2022-2023  Alexandru Agache
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package io.github.agache41.ormpipes.pipes.temporal.typeTimestamp;

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

import java.sql.Timestamp;

@Execution(ExecutionMode.CONCURRENT)
public class TimestampFormatTest extends BaseFormatTest {
    @Test
    @Execution(ExecutionMode.CONCURRENT)
    void test() throws Throwable {
        this.doRegressionTest(TimestampFormatAnnotation.class);
    }

    @Execution(ExecutionMode.CONCURRENT)
    @ParameterizedTest(name = "\"{0}\" => \"{1}{2}\" " + "<[simple={3},nullSafe={4},blankSafe={5},noException={6}]>" + "<[format=\"{7}\",languageTag=\"{8}\"]>")
    @CsvFileSource(delimiter = ';', resources = "/io/github/agache41/ormpipes/pipes/temporal/typeTimestamp/TimestampFormatTestSmall.csv", numLinesToSkip = 1, nullValues = {
            BaseTestConfigFor.NULL_KEY})
    void csvTest(@AggregateWith(TimestampFormatAnnotation.class) TimestampFormatAnnotation baseConfigAnnotation) {
        this.doTest(baseConfigAnnotation);
    }

    public static class TimestampFormatAnnotation extends BaseNumericTestConfigFor<TimestampFormatAnnotation> implements TypeTimestamp.New {
        public TimestampFormatAnnotation() {
            super();
        }

        public Class<TypeTimestamp.New> annotationType() {
            return TypeTimestamp.New.class;
        }

        public Class<? extends AnnotablePipe<TypeTimestamp.New, String, Timestamp>> read() {
            return TimestampPipes.ParseTimestamp.class;
        }

        public Class<? extends AnnotablePipe<TypeTimestamp.New, Timestamp, String>> write() {
            return TimestampPipes.TimestampToString.class;
        }

        /**
* {@inheritDoc}
*/
@Override
        public TimestampFormatAnnotation get() {
            return new TimestampFormatAnnotation();
        }
    }
}