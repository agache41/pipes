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

package io.github.agache41.ormpipes.pipes.typeString;

import io.github.agache41.annotator.annotator.Annotator;
import io.github.agache41.ormpipes.config.Constants;
import io.github.agache41.ormpipes.functional.StrongType;
import io.github.agache41.ormpipes.functional.ThrowingConsumer;
import io.github.agache41.ormpipes.functional.ThrowingFunction;
import io.github.agache41.ormpipes.functional.WrappedException;
import io.github.agache41.ormpipes.pipe.AnnotablePipe;
import io.github.agache41.ormpipes.pipes.typeObject.TypeObject;
import org.apache.commons.compress.utils.Sets;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <pre>
 * The type String pipes.
 * </pre>
 */
public class StringPipes {
    /**
     * <pre>
     * The type To uppercase.
     * </pre>
     */
    public static class ToUppercase extends StringPipe<TypeString.toUpperCase> implements AnnotablePipe<TypeString.toUpperCase, String, String> {
        /**
         * {@inheritDoc}
         */
        @Override
        public void configure(TypeString.toUpperCase cfg) {
            super.configure(cfg);
            this.function = String::toUpperCase;
        }
    }

    /**
     * <pre>
     * The type To lower case.
     * </pre>
     */
    public static class ToLowerCase extends StringPipe<TypeString.toLowerCase> implements AnnotablePipe<TypeString.toLowerCase, String, String> {
        /**
         * {@inheritDoc}
         */
        @Override
        public void configure(TypeString.toLowerCase cfg) {
            super.configure(cfg);
            this.function = String::toLowerCase;
        }
    }

    /**
     * <pre>
     * The type Trim.
     * </pre>
     */
    public static class Trim extends StringPipe<TypeString.trim> implements AnnotablePipe<TypeString.trim, String, String> {
        /**
         * {@inheritDoc}
         */
        @Override
        public void configure(TypeString.trim cfg) {
            super.configure(cfg);
            this.function = String::trim;
        }
    }

    /**
     * <pre>
     * The type Substring.
     * </pre>
     */
    public static class Substring extends StringPipe<TypeString.substring> implements AnnotablePipe<TypeString.substring, String, String> {

        /**
         * {@inheritDoc}
         */
        @Override
        public void configure(TypeString.substring cfg) {
            super.configure(cfg);
            final int beginIndex = cfg.beginIndex();
            final int endIndex = cfg.endIndex();
            if (endIndex == TypeString.substring.END_OF_INPUT) {
                this.function = string -> string.substring(beginIndex);
            } else {
                this.function = string -> string.substring(beginIndex, endIndex);
            }
        }
    }

    /**
     * <pre>
     * The type Replace.
     * </pre>
     */
    public static class Replace extends StringPipe<TypeString.replace> implements AnnotablePipe<TypeString.replace, String, String> {
        /**
         * {@inheritDoc}
         */
        @Override
        public void configure(TypeString.replace cfg) {
            super.configure(cfg);
            final Pattern pattern = Pattern.compile(cfg.value(), Pattern.LITERAL);
            final String replacement = cfg.with();
            this.function = input -> pattern.matcher(input)
                                            .replaceAll(Matcher.quoteReplacement(replacement));
        }
    }

    /**
     * <pre>
     * The type Replace all.
     * </pre>
     */
    public static class ReplaceAll extends StringPipe<TypeString.replaceAll> implements AnnotablePipe<TypeString.replaceAll, String, String> {
        /**
         * {@inheritDoc}
         */
        @Override
        public void configure(TypeString.replaceAll cfg) {
            super.configure(cfg);
            final Pattern pattern = Pattern.compile(cfg.value(), Pattern.LITERAL);
            final String replacement = cfg.with();
            this.function = input -> pattern.matcher(input)
                                            .replaceAll(replacement);
        }
    }

    /**
     * <pre>
     * The type Quote.
     * </pre>
     */
    public static class Quote extends StringPipe<TypeString.quoted> implements AnnotablePipe<TypeString.quoted, String, String> {
        /**
         * {@inheritDoc}
         */
        @Override
        public void configure(TypeString.quoted cfg) {
            super.configure(cfg);
            final char quote = cfg.value();
            this.function = input -> quote + input.replace("\"", "\"\"") + quote;
        }
    }

    /**
     * <pre>
     * The type Un quote.
     * </pre>
     */
    public static class UnQuote extends StringPipe<TypeString.quoted> implements AnnotablePipe<TypeString.quoted, String, String> {
        /**
         * {@inheritDoc}
         */
        @Override
        public void configure(TypeString.quoted cfg) {
            super.configure(cfg);
            final char quote = cfg.value();
            this.function = input -> {
                int last = input.length() - 1;
                if (last < 1) {
                    return input;
                }
                if ("".equals(input) || "\"\"".equals(input)) {
                    return "";
                }
                if (input.charAt(0) == quote && input.charAt(last) == quote) {
                    input = input.substring(1, last);
                }
                return input.replace("\"\"", "\"");
            };

        }
    }

    /**
     * <pre>
     * The type From nullable string.
     * </pre>
     */
    public static class FromNullableString extends StringPipe<TypeString.nullable> implements AnnotablePipe<TypeString.nullable, String, String> {

        /**
         * {@inheritDoc}
         */
        @Override
        public void configure(TypeString.nullable cfg) {
            super.configure(cfg);
            final String nullRepresentation = cfg.value();
            this.function = string -> nullRepresentation.equals(string) ? null : string;
        }
    }

    /**
     * <pre>
     * The type To nullable string.
     * </pre>
     */
    public static class ToNullableString extends StringPipe<TypeString.nullable> implements AnnotablePipe<TypeString.nullable, String, String> {
        /**
         * {@inheritDoc}
         */
        @Override
        public void configure(TypeString.nullable cfg) {
            super.configure(cfg);
            final String nullRepresentation = cfg.value();
            this.function = string -> string == null ? nullRepresentation : string;
        }
    }

    /**
     * <pre>
     * The type Value of.
     * </pre>
     */
    public static class ValueOf implements AnnotablePipe<TypeString.value, Object, String> {

        private String value;

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

        /**
         * {@inheritDoc}
         */
        @Override
        public void configure(TypeString.value cfg) {
            this.value = cfg.value();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public ThrowingFunction<Object, String> function() {
            return object -> this.value;
        }
    }

    /**
     * <pre>
     * The type String pipe.
     * </pre>
     *
     * @param <A> the type parameter
     */
    public static class StringPipe<A extends Annotation> implements AnnotablePipe<A, String, String> {

        /**
         * <pre>
         * The Function.
         * </pre>
         */
        protected ThrowingFunction<String, String> function;
        private boolean nullSafe;

        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getInputType() {
            return TypeString.strongType;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getOutputType() {
            return TypeString.strongType;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void configure(A cfg) {
            this.nullSafe = Annotator.of(cfg)
                                     .getAccessor("nullSafe")
                                     .getAs(cfg, Boolean.class, Boolean.FALSE);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public ThrowingFunction<String, String> function() {
            if (this.nullSafe) {
                return ThrowingFunction.nullSafe(this.function);
            }
            return this.function;
        }
    }

    /**
     * <pre>
     * The type Read lines pipe.
     * </pre>
     */
    public static class ReadLinesPipe implements AnnotablePipe<TypeString.IOStreamLines, Reader, Stream<String>> {
        private ThrowingFunction<Reader, Stream<String>> function;

        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getInputType() {
            return StrongType.of(Reader.class);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void configure(TypeString.IOStreamLines cfg) {
            final int bufferSize = cfg.bufferSizeKB() == 0 ? 8192 : cfg.bufferSizeKB() * Constants.KB;
            this.function = reader -> new AutoClosingBufferedReader(reader, bufferSize).lines();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public ThrowingFunction<Reader, Stream<String>> function() {
            return this.function;
        }
    }

    /**
     * <pre>
     * The type Write lines pipe.
     * </pre>
     */
    public static class WriteLinesPipe implements AnnotablePipe<TypeString.IOStreamLines, Stream<String>, ThrowingConsumer<Writer>> {
        private String separator;
        private int bufferSize;

        /**
         * {@inheritDoc}
         */
        @Override
        public void configure(TypeString.IOStreamLines cfg) {
            this.bufferSize = cfg.bufferSizeKB() == 0 ? 8192 : cfg.bufferSizeKB() * Constants.KB;
            this.separator = cfg.separator();

        }

        /**
         * {@inheritDoc}
         */
        @Override
        public ThrowingFunction<Stream<String>, ThrowingConsumer<Writer>> function() {
            return stringStream -> writer -> {
                try (BufferedWriter bufferedWriter = new BufferedWriter(writer, this.bufferSize)) {
                    stringStream.forEach(ThrowingConsumer.wrap((String str) -> {
                        bufferedWriter.write(str);
                        bufferedWriter.write(this.separator);
                    }));
                } catch (IOException | WrappedException e) {
                    this.handleException(TypeString.IOStreamLines.class, WriteLinesPipe.class, e, stringStream);
                }
            };
        }
    }

    /**
     * <pre>
     * The type Join array.
     * </pre>
     */
    public static class JoinArray implements AnnotablePipe<TypeString.Array, String[], String> {
        private final AtomicInteger lineLenght = new AtomicInteger(80);
        private ThrowingFunction<String[], String> function;

        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getInputType() {
            return StrongType.of(String[].class);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getOutputType() {
            return TypeString.strongType;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void configure(TypeString.Array cfg) {
            final String separator = cfg.separator();
            this.function = tokens -> {
                StringBuilder sb = new StringBuilder(this.lineLenght.get());
                boolean appendSeparator = false;
                for (String token : tokens) {
                    if (appendSeparator) {
                        sb.append(separator);
                    } else {
                        appendSeparator = true;
                    }
                    sb.append(token);
                }
                if (sb.length() > this.lineLenght.get()) {
                    this.lineLenght.set(sb.length());
                }
                return sb.toString();
            };
            this.function = this.function.nullSafe(cfg.nullSafe());
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public ThrowingFunction<String[], String> function() {
            return this.function;
        }
    }

    /**
     * <pre>
     * The type Split array.
     * </pre>
     */
    public static class SplitArray implements AnnotablePipe<TypeString.Array, String, String[]> {
        private ThrowingFunction<String, String[]> function;

        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getInputType() {
            return TypeString.strongType;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getOutputType() {
            return StrongType.of(String[].class);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void configure(TypeString.Array cfg) {
            final String separator = cfg.separator();
            this.function = line -> line.split(separator, -1);
            this.function = this.function.nullSafe(cfg.nullSafe());
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public ThrowingFunction<String, String[]> function() {
            return this.function;
        }
    }


    /**
     * <pre>
     * The type Join list.
     * </pre>
     */
    public static class JoinList implements AnnotablePipe<TypeString.List, Collection<String>, String> {
        private final AtomicInteger lineLenght = new AtomicInteger(80);
        private ThrowingFunction<Collection<String>, String> function;

        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getInputType() {
            return StrongType.of(Collection.class)
                             .parameterizedWith(TypeString.strongType);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getOutputType() {
            return TypeString.strongType;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void configure(TypeString.List cfg) {
            final String separator = cfg.separator();
            this.function = tokens -> {
                StringBuilder sb = new StringBuilder(this.lineLenght.get());
                boolean appendSeparator = false;
                for (String token : tokens) {
                    if (appendSeparator) {
                        sb.append(separator);
                    } else {
                        appendSeparator = true;
                    }
                    sb.append(token);
                }
                if (sb.length() > this.lineLenght.get()) {
                    this.lineLenght.set(sb.length());
                }
                return sb.toString();
            };
            this.function = this.function.nullSafe(cfg.nullSafe());
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public ThrowingFunction<Collection<String>, String> function() {
            return this.function;
        }
    }

    /**
     * <pre>
     * The type Split list.
     * </pre>
     */
    public static class SplitList implements AnnotablePipe<TypeString.List, String, List<String>> {
        private ThrowingFunction<String, List<String>> function;

        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getInputType() {
            return TypeString.strongType;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getOutputType() {
            return StrongType.of(List.class)
                             .parameterizedWith(TypeString.strongType);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void configure(TypeString.List cfg) {
            final String separator = cfg.separator();
            this.function = line -> Arrays.asList(line.split(separator, -1));
            this.function = this.function.nullSafe(cfg.nullSafe());
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public ThrowingFunction<String, List<String>> function() {
            return this.function;
        }
    }

    /**
     * <pre>
     * The type Join set.
     * </pre>
     */
    public static class JoinSet implements AnnotablePipe<TypeString.Set, Collection<String>, String> {
        private final AtomicInteger lineLenght = new AtomicInteger(80);
        private ThrowingFunction<Collection<String>, String> function;

        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getInputType() {
            return StrongType.of(Collection.class)
                             .parameterizedWith(TypeString.strongType);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getOutputType() {
            return TypeString.strongType;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void configure(TypeString.Set cfg) {
            final String separator = cfg.separator();
            this.function = tokens -> {
                StringBuilder sb = new StringBuilder(this.lineLenght.get());
                boolean appendSeparator = false;
                for (String token : tokens) {
                    if (appendSeparator) {
                        sb.append(separator);
                    } else {
                        appendSeparator = true;
                    }
                    sb.append(token);
                }
                if (sb.length() > this.lineLenght.get()) {
                    this.lineLenght.set(sb.length());
                }
                return sb.toString();
            };
            this.function = this.function.nullSafe(cfg.nullSafe());
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public ThrowingFunction<Collection<String>, String> function() {
            return this.function;
        }
    }

    /**
     * <pre>
     * The type Split set.
     * </pre>
     */
    public static class SplitSet implements AnnotablePipe<TypeString.Set, String, Set<String>> {
        private ThrowingFunction<String, Set<String>> function;

        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getInputType() {
            return TypeString.strongType;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getOutputType() {
            return StrongType.of(Set.class)
                             .parameterizedWith(TypeString.strongType);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void configure(TypeString.Set cfg) {
            final String separator = cfg.separator();
            this.function = line -> Sets.newHashSet(line.split(separator, -1));
            this.function = this.function.nullSafe(cfg.nullSafe());
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public ThrowingFunction<String, Set<String>> function() {
            return this.function;
        }
    }

    /**
     * <pre>
     * The type Join stream.
     * </pre>
     */
    public static class JoinStream implements AnnotablePipe<TypeString.Stream, java.util.stream.Stream<String>, String> {
        private ThrowingFunction<java.util.stream.Stream<String>, String> function;

        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getInputType() {
            return StrongType.of(Stream.class)
                             .parameterizedWith(TypeString.strongType);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getOutputType() {
            return TypeString.strongType;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void configure(TypeString.Stream cfg) {
            final String separator = cfg.separator();
            this.function = stream -> stream.collect(Collectors.joining(separator));
            this.function = this.function.nullSafe(cfg.nullSafe());
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public ThrowingFunction<java.util.stream.Stream<String>, String> function() {
            return this.function;
        }
    }

    /**
     * <pre>
     * The type Split stream.
     * </pre>
     */
    public static class SplitStream implements AnnotablePipe<TypeString.Stream, String, Stream<String>> {
        private ThrowingFunction<String, Stream<String>> function;

        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getInputType() {
            return TypeString.strongType;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getOutputType() {
            return StrongType.of(Stream.class)
                             .parameterizedWith(TypeString.strongType);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void configure(TypeString.Stream cfg) {
            final String separator = cfg.separator();
            this.function = line -> Stream.of(line.split(separator, -1));
            this.function = this.function.nullSafe(cfg.nullSafe());
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public ThrowingFunction<String, Stream<String>> function() {
            return this.function;
        }
    }

    /**
     * <pre>
     * The type Read cell value.
     * </pre>
     */
    public static class ReadCellValue implements AnnotablePipe<TypeString.cellValue, Cell, String> {
        private ThrowingFunction<Cell, String> function;

        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getInputType() {
            return StrongType.of(Cell.class);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getOutputType() {
            return TypeString.strongType;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void configure(TypeString.cellValue cfg) {
            this.function = cell -> {
                if (cfg.nullSafe() && cell.getCellType() == CellType.BLANK) {
                    return null;
                }
                return cell.getStringCellValue();
            };
            this.function = this.function.nullSafe(cfg.nullSafe());
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public ThrowingFunction<Cell, String> function() {
            return this.function;
        }
    }


    /**
     * <pre>
     * The type Write cell value.
     * </pre>
     */
    public static class WriteCellValue implements AnnotablePipe<TypeString.cellValue, String, ThrowingConsumer<Cell>> {
        private ThrowingFunction<String, ThrowingConsumer<Cell>> function;

        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getInputType() {
            return TypeString.strongType;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void configure(TypeString.cellValue cfg) {
            if (cfg.nullSafe()) {
                this.function = string -> string == null ? cell -> cell.setBlank() : cell -> cell.setCellValue(string);
            } else {
                this.function = string -> cell -> cell.setCellValue(string);
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public ThrowingFunction<String, ThrowingConsumer<Cell>> function() {
            return this.function;
        }
    }
}
