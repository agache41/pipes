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

package io.github.agache41.ormpipes.pipes.spreadSheet.base;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;

/**
 * <pre>
 * The enum Line.
 * </pre>
 */
public enum Line {
    /**
     * <pre>
     * The Even lines.
     * </pre>
     */
    EVEN_LINES(ab -> {
        ab.set(!ab.get());
        return ab.get();
    }),
    /**
     * <pre>
     * Odd lines line.
     * </pre>
     */
    ODD_LINES(ab -> {
        ab.set(!ab.get());
        return !ab.get();
    }),
    /**
     * <pre>
     * All lines line.
     * </pre>
     */
    ALL_LINES(ab -> true);

    private final Function<AtomicBoolean, Boolean> toggle;

    Line(Function<AtomicBoolean, Boolean> toggle) {
        this.toggle = toggle;
    }

    /**
     * <pre>
     * Gets toggle.
     * </pre>
     *
     * @return the toggle
     */
    public Function<AtomicBoolean, Boolean> getToggle() {
        return this.toggle;
    }
}
