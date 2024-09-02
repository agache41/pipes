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

import java.util.Arrays;
import java.util.Objects;


public class Key {
    private final Object[] keys;
    private final int hashCode;

    public Key(Object... keys) {
        this.keys = keys;
        this.hashCode = Objects.hash(this.keys);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        Key key = (Key) o;
        if (this.keys.length != key.keys.length) {
            return false;
        }
        for (int index = 0; index < this.keys.length; index++) {
            if (!Objects.equals(this.keys[index], key.keys[index])) {
                return false;
            }
        }
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return this.hashCode;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "Key{" +
               "keys=" + Arrays.toString(this.keys) +
               ", hashCode=" + this.hashCode +
               '}';
    }
}
