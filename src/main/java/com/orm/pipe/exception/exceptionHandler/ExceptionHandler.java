package com.orm.pipe.exception.exceptionHandler;

import com.orm.pipe.AnnotablePipe;

import java.lang.annotation.Annotation;

public interface ExceptionHandler {

    void handleException(Class<? extends Annotation> cfgClass,
                         Class<? extends AnnotablePipe> workingClass,
                         Throwable throwable,
                         Object inputValue);
}
