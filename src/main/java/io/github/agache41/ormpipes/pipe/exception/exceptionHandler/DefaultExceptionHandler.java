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

package io.github.agache41.ormpipes.pipe.exception.exceptionHandler;

import io.github.agache41.ormpipes.functional.WrappedException;
import io.github.agache41.ormpipes.pipe.AnnotablePipe;
import io.github.agache41.ormpipes.pipe.exception.PipeException;

import java.lang.annotation.Annotation;

/**
 * <pre>
 * Default exception handler.
 * </pre>
 */
public class DefaultExceptionHandler implements ExceptionHandler {
    /**
     * {@inheritDoc}
     */
    @Override
    public void handleException(Class<? extends Annotation> cfgClass,
                                Class<? extends AnnotablePipe> workingClass,
                                Throwable throwable,
                                Object inputValue) {
        if (throwable instanceof WrappedException) {
            throwable = throwable.getCause();
        }
        throwable.printStackTrace();
        AnnotablePipe.logger.errorf(" Pipe %s in Class %s threw %s:%s for input %s",
                                    cfgClass.getSimpleName(),
                                    workingClass.getSimpleName(),
                                    throwable.getClass()
                                             .getSimpleName(),
                                    throwable.getMessage(),
                                    inputValue);
        throw new PipeException(throwable);
    }
}
