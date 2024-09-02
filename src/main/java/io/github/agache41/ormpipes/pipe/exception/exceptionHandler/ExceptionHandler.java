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

import io.github.agache41.ormpipes.pipe.AnnotablePipe;

import java.lang.annotation.Annotation;

/**
 * <pre>
 * The interface Exception handler.
 * </pre>
 */
public interface ExceptionHandler {

    /**
     * <pre>
     * Handle exception.
     * </pre>
     *
     * @param cfgClass     the cfg class
     * @param workingClass the working class
     * @param throwable    the throwable
     * @param inputValue   the input value
     */
    void handleException(Class<? extends Annotation> cfgClass,
                         Class<? extends AnnotablePipe> workingClass,
                         Throwable throwable,
                         Object inputValue);
}
