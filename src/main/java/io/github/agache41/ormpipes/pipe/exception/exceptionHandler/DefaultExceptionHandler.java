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
