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

package io.github.agache41.ormpipes.pipes.temporal.typeLocalDateTime;

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

import java.time.LocalDateTime;

@Execution(ExecutionMode.CONCURRENT)
public class LocalDateTimeFormatTest extends BaseFormatTest {
    @Test
    @Execution(ExecutionMode.CONCURRENT)
    void test() throws Throwable {
        this.doRegressionTest(LocalDateTimeFormatAnnotation.class);
    }

    @Execution(ExecutionMode.CONCURRENT)
    @ParameterizedTest(name = "\"{0}\" => \"{1}{2}{3}\" <[simple={4},nullSafe={5},blankSafe={6},noException={7}]><[format=\"{8}\",languageTag=\"{9}\"]>")
    @CsvFileSource(resources = "/io/github/agache41/ormpipes/pipes/temporal/typeLocalDateTime/LocalDateTimeFormatTestSmall.csv", delimiter = ';', numLinesToSkip = 1, nullValues =
            {BaseTestConfigFor.NULL_KEY})
    void csvTest(@AggregateWith(LocalDateTimeFormatAnnotation.class) LocalDateTimeFormatAnnotation baseConfigAnnotation) {
        this.doTest(baseConfigAnnotation);
    }

    public static class LocalDateTimeFormatAnnotation extends BaseNumericTestConfigFor<LocalDateTimeFormatAnnotation> implements TypeLocalDateTime.New {
        public LocalDateTimeFormatAnnotation() {
            super();
        }

        public Class<TypeLocalDateTime.New> annotationType() {
            return TypeLocalDateTime.New.class;
        }

        public Class<? extends AnnotablePipe<TypeLocalDateTime.New, String, LocalDateTime>> read() {
            return LocalDateTimePipes.ParseLocalDateTime.class;
        }

        public Class<? extends AnnotablePipe<TypeLocalDateTime.New, LocalDateTime, String>> write() {
            return LocalDateTimePipes.LocalDateTimeToString.class;
        }

        /**
* {@inheritDoc}
*/
@Override
        public LocalDateTimeFormatAnnotation get() {
            return new LocalDateTimeFormatAnnotation();
        }
    }
}