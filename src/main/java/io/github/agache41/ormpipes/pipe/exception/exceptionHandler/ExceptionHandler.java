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
