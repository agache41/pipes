package com.orm.pipe.exception.exceptionHandler;

import com.orm.functional.WrappedException;
import com.orm.pipe.AnnotablePipe;
import com.orm.pipe.exception.PipeException;

import java.lang.annotation.Annotation;

public class DefaultExceptionHandler implements ExceptionHandler {
    @Override
    public void handleException(Class<? extends Annotation> cfgClass,
                                Class<? extends AnnotablePipe> workingClass,
                                Throwable throwable,
                                Object inputValue) {
        if (throwable instanceof WrappedException) {
            throwable = throwable.getCause();
        }
        throwable.printStackTrace();
        AnnotablePipe.logger.error(" Pipe {} in Class {} threw {}:{} for input {}",
                cfgClass.getSimpleName(),
                workingClass.getSimpleName(),
                throwable.getClass()
                         .getSimpleName(),
                throwable.getMessage(),
                inputValue);
        throw new PipeException(throwable);
    }
}
