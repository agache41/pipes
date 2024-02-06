package io.github.agache41.ormpipes.pipes.spreadSheet.base;

import io.github.agache41.ormpipes.functional.StrongType;
import io.github.agache41.ormpipes.functional.ThrowingFunction;
import io.github.agache41.ormpipes.pipe.AnnotablePipe;
import org.apache.poi.ss.usermodel.Cell;

import java.lang.annotation.Annotation;

/**
 * <pre>
 * The type Identity cell.
 * </pre>
 */
public class IdentityCell implements AnnotablePipe<Annotation, Cell, Cell> {
    /**
     * {@inheritDoc}
     */
    @Override
    public StrongType getOutputType() {
        return StrongType.of(Cell.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StrongType getInputType() {
        return StrongType.of(Cell.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void configure(Annotation cfg) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ThrowingFunction<Cell, Cell> function() {
        return ThrowingFunction.identity();
    }
}
