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

package io.github.agache41.ormpipes.config;


/**
 * <pre>
 * The enum Settings.
 * </pre>
 */
public enum Settings {
    /**
     * <pre>
     * Numeric integer format settings.
     * </pre>
     */
    NUMERIC_INTEGER_FORMAT("pipes.integer.format"),
    /**
     * <pre>
     * Numeric floating point format settings.
     * </pre>
     */
    NUMERIC_FLOATING_POINT_FORMAT("pipes.floatingPoint.format"),
    /**
     * <pre>
     * Date time format settings.
     * </pre>
     */
    DATE_TIME_FORMAT("pipes.dateTime.format"),
    /**
     * <pre>
     * Language tag settings.
     * </pre>
     */
    LANGUAGE_TAG("pipes.languageTag"),
    /**
     * <pre>
     * Zone id settings.
     * </pre>
     */
    ZONE_ID("pipes.zone_Id");

    /**
     * <pre>
     * The constant DEFAULT.
     * </pre>
     */
    public static final String DEFAULT = Constants.DEFAULT;

    private final String key;

    Settings(String key) {
        this.key = key;
    }

    /**
     * <pre>
     * Gets key.
     * </pre>
     *
     * @return the key
     */
    public String getKey() {
        return this.key;
    }
}
