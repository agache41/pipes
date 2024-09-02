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

package io.github.agache41.ormpipes.pipe.exception;

/**
 * <pre>
 * The type Pipe exception.
 * </pre>
 */
public class PipeException extends RuntimeException {
    /**
     * <pre>
     * Instantiates a new Pipe exception.
     * </pre>
     */
    public PipeException() {
    }

    /**
     * <pre>
     * Instantiates a new Pipe exception.
     * </pre>
     *
     * @param message the message
     */
    public PipeException(String message) {
        super(message);
    }

    /**
     * <pre>
     * Instantiates a new Pipe exception.
     * </pre>
     *
     * @param message the message
     * @param cause   the cause
     */
    public PipeException(String message,
                         Throwable cause) {
        super(message, cause);
    }

    /**
     * <pre>
     * Instantiates a new Pipe exception.
     * </pre>
     *
     * @param cause the cause
     */
    public PipeException(Throwable cause) {
        super(cause);
    }

    /**
     * <pre>
     * Instantiates a new Pipe exception.
     * </pre>
     *
     * @param message            the message
     * @param cause              the cause
     * @param enableSuppression  the enable suppression
     * @param writableStackTrace the writable stack trace
     */
    public PipeException(String message,
                         Throwable cause,
                         boolean enableSuppression,
                         boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
