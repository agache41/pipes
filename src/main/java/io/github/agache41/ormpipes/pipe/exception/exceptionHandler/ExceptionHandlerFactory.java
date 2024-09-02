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
import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 * The type Exception handler factory.
 * </pre>
 */
public class ExceptionHandlerFactory {

    private static final Map<Class<? extends Annotation>, ExceptionHandler> pipeExceptionHandlerMap = new HashMap<>();

    private static final Map<Class<?>, ExceptionHandler> classExceptionHandlerMap = new HashMap<>();

    private static final ExceptionHandler defaultExceptionHandler = new DefaultExceptionHandler();

    /**
     * <pre>
     * Exception handler exception handler.
     * </pre>
     *
     * @param cfgClass     the cfg class
     * @param workingClass the working class
     * @return the exception handler
     */
    public static ExceptionHandler exceptionHandler(Class<? extends Annotation> cfgClass,
                                                    Class<? extends AnnotablePipe> workingClass) {
        if (pipeExceptionHandlerMap.containsKey(cfgClass)) {
            return pipeExceptionHandlerMap.get(cfgClass);
        }
        if (classExceptionHandlerMap.containsKey(workingClass)) {
            return classExceptionHandlerMap.get(workingClass);
        }
        return defaultExceptionHandler;
    }

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
    public static void handleException(Class<? extends Annotation> cfgClass,
                                       Class<? extends AnnotablePipe> workingClass,
                                       Throwable throwable,
                                       Object inputValue) {
        exceptionHandler(cfgClass,
                         workingClass).handleException(cfgClass,
                                                       workingClass,
                                                       throwable,
                                                       inputValue);
    }

    /**
     * <pre>
     * Add exception handler.
     * </pre>
     *
     * @param clazz            the clazz
     * @param exceptionHandler the exception handler
     */
    public void addExceptionHandler(Class<?> clazz,
                                    ExceptionHandler exceptionHandler) {
        if (clazz.isAnnotation()) {
            pipeExceptionHandlerMap.put((Class<? extends Annotation>) clazz,
                                        exceptionHandler);
        } else if (AnnotablePipe.class.isAssignableFrom(clazz)) {
            classExceptionHandlerMap.put(clazz,
                                         exceptionHandler);
        } else {
            throw new IllegalArgumentException(clazz.getSimpleName() + " must be Annotation or AnnotablePipe");
        }

    }
}

