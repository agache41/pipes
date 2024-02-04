package io.github.agache41.ormpipes.pipe.exception.exceptionHandler;

import io.github.agache41.ormpipes.functional.WrappedException;
import io.github.agache41.ormpipes.pipe.AnnotablePipe;
import io.github.agache41.ormpipes.pipe.exception.PipeException;

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
