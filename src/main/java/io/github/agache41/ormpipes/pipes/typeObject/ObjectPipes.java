package io.github.agache41.ormpipes.pipes.typeObject;

import io.github.agache41.ormpipes.functional.StrongType;
import io.github.agache41.ormpipes.functional.ThrowingFunction;
import io.github.agache41.ormpipes.pipe.AnnotablePipe;
import io.github.agache41.ormpipes.pipes.typeString.TypeString;

/**
 * <pre>
 * The type Object pipes.
 * </pre>
 */
public class ObjectPipes {

    /**
     * <pre>
     * The type To string.
     * </pre>
     */
    public static class ToString implements AnnotablePipe<TypeObject.toString, Object, String> {
        private boolean nullSafe;

        /**
         * {@inheritDoc}
         */
        @Override
        public void configure(TypeObject.toString cfg) {
            this.nullSafe = cfg.nullSafe();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public ThrowingFunction<Object, String> function() {
            if (this.nullSafe) {
                return ThrowingFunction.nullSafe(Object::toString);
            }
            return Object::toString;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getInputType() {
            return TypeObject.strongType;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getOutputType() {
            return TypeString.strongType;
        }
    }
}
