package com.orm.pipes.typeObject;

import com.orm.functional.StrongType;
import com.orm.functional.ThrowingFunction;
import com.orm.pipe.AnnotablePipe;
import com.orm.pipes.typeString.TypeString;

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
