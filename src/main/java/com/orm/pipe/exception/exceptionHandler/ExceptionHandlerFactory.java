package com.orm.pipe.exception.exceptionHandler;

import com.orm.pipe.AnnotablePipe;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

public class ExceptionHandlerFactory {

    private static final Map<Class<? extends Annotation>, ExceptionHandler> pipeExceptionHandlerMap = new HashMap<>();

    private static final Map<Class<?>, ExceptionHandler> classExceptionHandlerMap = new HashMap<>();

    private static final ExceptionHandler defaultExceptionHandler = new DefaultExceptionHandler();

    public static ExceptionHandler exceptionHandler(Class<? extends Annotation> cfgClass,
                                                    Class<? extends AnnotablePipe> workingClass) {
        if (pipeExceptionHandlerMap.containsKey(cfgClass)) {
            return pipeExceptionHandlerMap.get(cfgClass);
        }
        if (classExceptionHandlerMap.containsKey(workingClass))
            return classExceptionHandlerMap.get(workingClass);
        return defaultExceptionHandler;
    }

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

    public void addExceptionHandler(Class<?> clazz,
                                    ExceptionHandler exceptionHandler) {
        if (clazz.isAnnotation())
            pipeExceptionHandlerMap.put((Class<? extends Annotation>) clazz,
                    exceptionHandler);
        else if (AnnotablePipe.class.isAssignableFrom(clazz)) {
            classExceptionHandlerMap.put(clazz,
                    exceptionHandler);
        } else throw new IllegalArgumentException(clazz.getSimpleName() + " must be Annotation or AnnotablePipe");

    }
}

