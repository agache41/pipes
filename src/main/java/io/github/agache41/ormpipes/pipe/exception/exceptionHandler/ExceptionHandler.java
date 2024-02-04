package io.github.agache41.ormpipes.pipe.exception.exceptionHandler;

import io.github.agache41.ormpipes.pipe.AnnotablePipe;

import java.lang.annotation.Annotation;

public interface ExceptionHandler {

    void handleException(Class<? extends Annotation> cfgClass,
                         Class<? extends AnnotablePipe> workingClass,
                         Throwable throwable,
                         Object inputValue);
}
