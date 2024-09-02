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

package io.github.agache41.ormpipes.pipes.csv.csvFile;

import io.github.agache41.annotator.annotations.Extends;
import io.github.agache41.ormpipes.pipe.AnnotablePipe;
import io.github.agache41.ormpipes.pipe.DualPipe;
import io.github.agache41.ormpipes.pipes.base.othogonal.OrthogonalFile;
import io.github.agache41.ormpipes.pipes.base.othogonal.enums.Model;
import io.github.agache41.ormpipes.pipes.base.othogonal.enums.NamingMethod;
import io.github.agache41.ormpipes.pipes.base.othogonal.enums.PositionMethod;
import io.github.agache41.ormpipes.pipes.base.parser.Parser;

import java.lang.annotation.*;
import java.util.stream.Stream;

import static io.github.agache41.ormpipes.config.Constants.DEFAULT;

/**
 * <pre>
 * The interface Csv file.
 * </pre>
 */
@Repeatable(CSVFiles.class)
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Extends({DualPipe.class, OrthogonalFile.class})
public @interface CSVFile {

    /**
     * <pre>
     * The constant extension.
     * </pre>
     */
    String extension = ".csv";

    /**
     * <pre>
     * Header string [ ].
     * </pre>
     *
     * @return the string [ ]
     */
    String[] header() default {};

    /**
     * <pre>
     * Use first line as header boolean.
     * </pre>
     *
     * @return the boolean
     */
    boolean useFirstLineAsHeader() default true;

    /**
     * <pre>
     * Skip first n lines int.
     * </pre>
     *
     * @return the int
     */
    int skipFirstNLines() default 0;

    /**
     * <pre>
     * Naming method naming method.
     * </pre>
     *
     * @return the naming method
     */
    NamingMethod namingMethod() default NamingMethod.AccessorNames;

    /**
     * <pre>
     * Position method position method.
     * </pre>
     *
     * @return the position method
     */
    PositionMethod positionMethod() default PositionMethod.CSVFields;

    /**
     * <pre>
     * Model model.
     * </pre>
     *
     * @return the model
     */
    Model model() default Model.VariablePositions;

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
    Class<? extends AnnotablePipe<CSVFile, Stream<String[]>, Stream<?>>> read() default CSVFileReader.class;

    /**
     * <pre>
     * Write class.
     * </pre>
     *
     * @return the class
     */
    Class<? extends AnnotablePipe<CSVFile, Stream<?>, Stream<String[]>>> write() default CSVFileWriter.class;

    /**
     * <pre>
     * View string.
     * </pre>
     *
     * @return the string
     */
    String view() default DEFAULT;

    /**
     * <pre>
     * The type String stream parser.
     * </pre>
     *
     * @param <T> the type parameter
     */
    class StringStreamParser<T> extends Parser<T, String, Stream<T>> {
        /**
         * <pre>
         * Instantiates a new String stream parser.
         * </pre>
         *
         * @param clazz the clazz
         */
        public StringStreamParser(Class<T> clazz) {
            super(clazz, String.class);
        }

        /**
         * <pre>
         * Instantiates a new String stream parser.
         * </pre>
         *
         * @param clazz the clazz
         * @param view  the view
         */
        public StringStreamParser(Class<T> clazz,
                                  String view) {
            super(clazz, String.class, view);
        }

        /**
         * <pre>
         * Of class string stream parser.
         * </pre>
         *
         * @param <S>   the type parameter
         * @param clazz the clazz
         * @return the string stream parser
         */
        public static <S> StringStreamParser<S> ofClass(Class<S> clazz) {
            return new StringStreamParser<>(clazz);
        }

        /**
         * <pre>
         * Of class string stream parser.
         * </pre>
         *
         * @param <S>   the type parameter
         * @param clazz the clazz
         * @param view  the view
         * @return the string stream parser
         */
        public static <S> StringStreamParser<S> ofClass(Class<S> clazz,
                                                        String view) {
            return new StringStreamParser<>(clazz, view);
        }
    }

    /**
     * <pre>
     * The type String object parser.
     * </pre>
     *
     * @param <T> the type parameter
     */
    class StringObjectParser<T> extends Parser<T, String, T> {
        /**
         * <pre>
         * Instantiates a new String object parser.
         * </pre>
         *
         * @param clazz the clazz
         */
        public StringObjectParser(Class<T> clazz) {
            super(clazz, String.class);
        }

        /**
         * <pre>
         * Instantiates a new String object parser.
         * </pre>
         *
         * @param clazz the clazz
         * @param view  the view
         */
        public StringObjectParser(Class<T> clazz,
                                  String view) {
            super(clazz, String.class, view);
        }

        /**
         * <pre>
         * Of class string object parser.
         * </pre>
         *
         * @param <S>   the type parameter
         * @param clazz the clazz
         * @return the string object parser
         */
        public static <S> StringObjectParser<S> ofClass(Class<S> clazz) {
            return new StringObjectParser<>(clazz);
        }

        /**
         * <pre>
         * Of class string object parser.
         * </pre>
         *
         * @param <S>   the type parameter
         * @param clazz the clazz
         * @param view  the view
         * @return the string object parser
         */
        public static <S> StringObjectParser<S> ofClass(Class<S> clazz,
                                                        String view) {
            return new StringObjectParser<>(clazz, view);
        }
    }
}