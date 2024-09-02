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

package io.github.agache41.ormpipes.pipe.valueHandler;

import io.github.agache41.ormpipes.pipe.AnnotablePipe;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 *  Value handler factory for value handlers.
 * </pre>
 */
public class ValueHandlerFactory {

    private static final Map<Class<? extends Annotation>, ValueHandler> pipeValueHandlerMap = new HashMap<>();

    private static final Map<Class<?>, ValueHandler> classValueHandlerMap = new HashMap<>();

    private static final ValueHandler defaultValueHandler = new DefaultValueHandler();

    /**
     * <pre>
     * Value handler value handler.
     * </pre>
     *
     * @param <A>          the type parameter
     * @param <Output>     the type parameter
     * @param cfgClass     the cfg class
     * @param workingClass the working class
     * @return the value handler
     */
    public static <A extends Annotation, Output> ValueHandler valueHandler(Class<? extends Annotation> cfgClass,
                                                                           Class<? extends AnnotablePipe> workingClass) {
        if (pipeValueHandlerMap.containsKey(cfgClass)) {
            return pipeValueHandlerMap.get(cfgClass);
        }
        if (classValueHandlerMap.containsKey(workingClass)) {
            return classValueHandlerMap.get(workingClass);
        }
        return defaultValueHandler;
    }

    /**
     * <pre>
     * Handle value output.
     * </pre>
     *
     * @param <Output>     the type parameter
     * @param cfgClass     the cfg class
     * @param workingClass the working class
     * @param outputClass  the output class
     * @param inputValue   the input value
     * @return the output
     */
    public static <Output> Output handleValue(Class<? extends Annotation> cfgClass,
                                              Class<? extends AnnotablePipe> workingClass,
                                              Class<Output> outputClass,
                                              Object inputValue) {
        return valueHandler(cfgClass,
                            workingClass).handleValue(outputClass,
                                                      inputValue);
    }

    /**
     * <pre>
     * Add value handler.
     * </pre>
     *
     * @param clazz        the clazz
     * @param valueHandler the value handler
     */
    public void addValueHandler(Class<?> clazz,
                                ValueHandler valueHandler) {
        if (clazz.isAnnotation()) {
            pipeValueHandlerMap.put((Class<? extends Annotation>) clazz,
                                    valueHandler);
        } else if (AnnotablePipe.class.isAssignableFrom(clazz)) {
            classValueHandlerMap.put(clazz,
                                     valueHandler);
        } else {
            throw new IllegalArgumentException(clazz.getSimpleName() + " must be Annotation or AnnotablePipe");
        }

    }
}

