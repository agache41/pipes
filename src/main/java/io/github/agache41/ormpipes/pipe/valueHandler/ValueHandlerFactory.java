package io.github.agache41.ormpipes.pipe.valueHandler;

import io.github.agache41.ormpipes.pipe.AnnotablePipe;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

public class ValueHandlerFactory {

    private static final Map<Class<? extends Annotation>, ValueHandler> pipeValueHandlerMap = new HashMap<>();

    private static final Map<Class<?>, ValueHandler> classValueHandlerMap = new HashMap<>();

    private static final ValueHandler defaultValueHandler = new DefaultValueHandler();

    public static <A extends Annotation, Output> ValueHandler valueHandler(Class<? extends Annotation> cfgClass,
                                                                           Class<? extends AnnotablePipe> workingClass) {
        if (pipeValueHandlerMap.containsKey(cfgClass)) {
            return pipeValueHandlerMap.get(cfgClass);
        }
        if (classValueHandlerMap.containsKey(workingClass))
            return classValueHandlerMap.get(workingClass);
        return defaultValueHandler;
    }

    public static <Output> Output handleValue(Class<? extends Annotation> cfgClass,
                                              Class<? extends AnnotablePipe> workingClass,
                                              Class<Output> outputClass,
                                              Object inputValue) {
        return valueHandler(cfgClass,
                workingClass).handleValue(outputClass,
                inputValue);
    }

    public void addValueHandler(Class<?> clazz,
                                ValueHandler valueHandler) {
        if (clazz.isAnnotation())
            pipeValueHandlerMap.put((Class<? extends Annotation>) clazz,
                    valueHandler);
        else if (AnnotablePipe.class.isAssignableFrom(clazz)) {
            classValueHandlerMap.put(clazz,
                    valueHandler);
        } else throw new IllegalArgumentException(clazz.getSimpleName() + " must be Annotation or AnnotablePipe");

    }
}

