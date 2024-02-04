package io.github.agache41.ormpipes.pipes.typeObject;

import io.github.agache41.ormpipes.functional.StrongType;
import io.github.agache41.ormpipes.functional.ThrowingFunction;
import io.github.agache41.ormpipes.pipe.AnnotablePipe;
import io.github.agache41.ormpipes.pipes.typeString.TypeString;

public class ObjectPipes {

    public static class ToString implements AnnotablePipe<TypeObject.toString, Object, String> {
        private boolean nullSafe;

        @Override
        public void configure(TypeObject.toString cfg) {
            this.nullSafe = cfg.nullSafe();
        }

        @Override
        public ThrowingFunction<Object, String> function() {
            if (this.nullSafe)
                return ThrowingFunction.nullSafe(Object::toString);
            return Object::toString;
        }

        @Override
        public StrongType getInputType() {
            return TypeObject.strongType;
        }

        @Override
        public StrongType getOutputType() {
            return TypeString.strongType;
        }
    }
}
