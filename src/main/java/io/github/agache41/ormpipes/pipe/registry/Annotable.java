/*
 *    Copyright 2022-2023  Alexandru Agache
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package io.github.agache41.ormpipes.pipe.registry;

import io.github.agache41.annotator.Helper;
import io.github.agache41.annotator.accessor.Accessor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public interface Annotable<A extends Annotation> {
    void configure(A cfg);

    default void configure(A cfg,
                           Class<?> onClass,
                           Field onField,
                           Method onMethod,
                           Accessor<?> accessor,
                           String operation) {
        this.configure(cfg);
    }

    default String getAnnotationTypeName() {
        return Helper.getActualTypeNameForGenericInterfaceByIndex(this.getClass(), Annotable.class, 0);
    }
}
