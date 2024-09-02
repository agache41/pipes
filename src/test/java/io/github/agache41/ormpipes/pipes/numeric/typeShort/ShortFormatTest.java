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

package io.github.agache41.ormpipes.pipes.numeric.typeShort;

import io.github.agache41.ormpipes.pipe.AnnotablePipe;
import io.github.agache41.ormpipes.pipes.numeric.BaseFormatTest;
import io.github.agache41.ormpipes.pipes.numeric.BaseNumericTestConfigFor;
import io.github.agache41.ormpipes.pipes.numeric.BaseTestConfigFor;
import io.github.agache41.ormpipes.pipes.numeric.typeShort.TypeShort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.AggregateWith;
import org.junit.jupiter.params.provider.CsvFileSource;


@Execution(ExecutionMode.CONCURRENT)
public class ShortFormatTest extends BaseFormatTest {
    @Test
    @Execution(ExecutionMode.CONCURRENT)
    void test() throws Throwable {
        this.doRegressionTest(ShortFormatAnnotation.class);
    }

    @Execution(ExecutionMode.CONCURRENT)
    @ParameterizedTest(name = "\"{0}\" => \"{1}{2}\" " + "<[simple={3},nullSafe={4},blankSafe={5},noException={6}]>" + "<[format=\"{7}\",languageTag=\"{8}\"]>")
    @CsvFileSource(delimiter = ';', resources = "/io/github/agache41/ormpipes/pipes/numeric/typeShort/ShortFormatTestSmall.csv", numLinesToSkip = 1, nullValues = {
            BaseTestConfigFor.NULL_KEY})
    void csvTest(@AggregateWith(ShortFormatAnnotation.class) ShortFormatAnnotation baseConfigAnnotation) {
        this.doTest(baseConfigAnnotation);
    }

    /**
* {@inheritDoc}
*/
@Override
    public boolean existsSmall() {
        return super.existsSmall();
    }

    public static class ShortFormatAnnotation extends BaseNumericTestConfigFor<ShortFormatAnnotation> implements TypeShort.New {
        public ShortFormatAnnotation() {
            super();
        }

        public Class<TypeShort.New> annotationType() {
            return TypeShort.New.class;
        }

        public Class<? extends AnnotablePipe<TypeShort.New, String, Short>> read() {
            return ShortPipes.ParseShort.class;
        }

        public Class<? extends AnnotablePipe<TypeShort.New, Short, String>> write() {
            return ShortPipes.ShortToString.class;
        }

        /**
* {@inheritDoc}
*/
@Override
        public ShortFormatAnnotation get() {
            return new ShortFormatAnnotation();
        }
    }
}