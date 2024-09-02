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

package io.github.agache41.ormpipes.pipes.encoding;

import io.github.agache41.annotator.annotations.Extends;
import io.github.agache41.ormpipes.pipe.AnnotablePipe;
import io.github.agache41.ormpipes.pipe.DualPipe;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.lang.annotation.*;

import static io.github.agache41.ormpipes.config.Constants.DEFAULT;


/**
 * <pre>
 * The interface Io encoding.
 * </pre>
 */
@Repeatable(IOEncodings.class)
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Extends(DualPipe.class)
public @interface IOEncoding {
    /**
     * <pre>
     * The interface Charset.
     * </pre>
     */
    @Repeatable(IOEncodings.Charsets.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends(DualPipe.class)
    @interface Charset {
        /**
         * <pre>
         * Value string.
         * </pre>
         *
         * @return the string
         */
        String value();

        /**
         * <pre>
         * Enabled on string [ ].
         * </pre>
         *
         * @return the string [ ]
         */
        String[] enabledOn() default {"read", "write"};

        /**
         * <pre>
         * Read class.
         * </pre>
         *
         * @return the class
         */
        Class<? extends AnnotablePipe<Charset, InputStream, Reader>> read() default EncodingPipes.CharsetDecoder.class;

        /**
         * <pre>
         * Write class.
         * </pre>
         *
         * @return the class
         */
        Class<? extends AnnotablePipe<Charset, OutputStream, Writer>> write() default EncodingPipes.CharsetEncoder.class;

        /**
         * <pre>
         * View string.
         * </pre>
         *
         * @return the string
         */
        String view() default DEFAULT;
    }

    /**
     * <pre>
     * The interface Bom.
     * </pre>
     */
    @Repeatable(IOEncodings.BOMs.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends(DualPipe.class)
    @interface BOM {
        /**
         * <pre>
         * Value encoding pipes . bom charset.
         * </pre>
         *
         * @return the encoding pipes . bom charset
         */
        EncodingPipes.BOMCharset value() default EncodingPipes.BOMCharset.UTF_8;

        /**
         * <pre>
         * Include bom boolean.
         * </pre>
         *
         * @return the boolean
         */
        boolean includeBOM() default false;

        /**
         * <pre>
         * Enabled on string [ ].
         * </pre>
         *
         * @return the string [ ]
         */
        String[] enabledOn() default {"read", "write"};

        /**
         * <pre>
         * Read class.
         * </pre>
         *
         * @return the class
         */
        Class<? extends AnnotablePipe<BOM, InputStream, Reader>> read() default EncodingPipes.BOMDecoder.class;

        /**
         * <pre>
         * Write class.
         * </pre>
         *
         * @return the class
         */
        Class<? extends AnnotablePipe<BOM, OutputStream, Writer>> write() default EncodingPipes.BOMEncoder.class;

        /**
         * <pre>
         * View string.
         * </pre>
         *
         * @return the string
         */
        String view() default DEFAULT;
    }

    /**
     * <pre>
     * The interface Automatic.
     * </pre>
     */
    @Repeatable(IOEncodings.Automatics.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends(DualPipe.class)
    @interface Automatic {
        /**
         * <pre>
         * Value string.
         * </pre>
         *
         * @return the string
         */
        String value() default "";

        /**
         * <pre>
         * Enabled on string [ ].
         * </pre>
         *
         * @return the string [ ]
         */
        String[] enabledOn() default {"read", "write"};

        //Class<? extends AnnotablePipe<Automatic, InputStream, Reader>> read() default EncodingPipes.AutomaticIcu4jDecoder.class;

        /**
         * <pre>
         * Read class.
         * </pre>
         *
         * @return the class
         */
        Class<? extends AnnotablePipe<Automatic, InputStream, Reader>> read() default EncodingPipes.AutomaticGuessencodingDecoder.class;

        /**
         * <pre>
         * Write class.
         * </pre>
         *
         * @return the class
         */
        Class<? extends AnnotablePipe<Automatic, OutputStream, Writer>> write() default EncodingPipes.AutomaticCharsetEncoder.class;

        /**
         * <pre>
         * View string.
         * </pre>
         *
         * @return the string
         */
        String view() default DEFAULT;
    }
}

