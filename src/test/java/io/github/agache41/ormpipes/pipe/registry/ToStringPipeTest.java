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

package io.github.agache41.ormpipes.pipe.registry;


import io.github.agache41.ormpipes.functional.StrongType;
import io.github.agache41.ormpipes.functional.ThrowingFunction;
import io.github.agache41.ormpipes.pipe.AnnotablePipe;
import io.github.agache41.ormpipes.pipes.numeric.typeInteger.TypeInteger;
import io.github.agache41.ormpipes.pipes.typeString.TypeString;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import java.util.Objects;

@Execution(ExecutionMode.CONCURRENT)
public class ToStringPipeTest implements AnnotablePipe<ToStringAnnotTest, Integer, String> {
    /**
* {@inheritDoc}
*/
@Override
    public void configure(ToStringAnnotTest cfg) {

    }

    /**
* {@inheritDoc}
*/
@Override
    public ThrowingFunction<Integer, String> function() {
        return Objects::toString;
    }

    /**
* {@inheritDoc}
*/
@Override
    public StrongType getInputType() {
        return TypeInteger.strongType;
    }

    /**
* {@inheritDoc}
*/
@Override
    public StrongType getOutputType() {
        return TypeString.strongType;
    }
}
