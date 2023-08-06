package com.orm.pipes.typeString;

import com.orm.annotations.Annotations;
import com.orm.functional.StrongType;
import com.orm.functional.ThrowingConsumer;
import com.orm.functional.ThrowingFunction;
import com.orm.functional.WrappedException;
import com.orm.pipe.AnnotablePipe;
import com.orm.pipes.typeObject.TypeObject;
import com.orm.reflection.annotator.Annotator;
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

public class StringPipes {
    public static class ToUppercase extends StringPipe<TypeString.toUpperCase> implements AnnotablePipe<TypeString.toUpperCase, String, String> {
        @Override
        public void configure(TypeString.toUpperCase cfg) {
            super.configure(cfg);
            this.function = String::toUpperCase;
        }
    }

    public static class ToLowerCase extends StringPipe<TypeString.toLowerCase> implements AnnotablePipe<TypeString.toLowerCase, String, String> {
        @Override
        public void configure(TypeString.toLowerCase cfg) {
            super.configure(cfg);
            this.function = String::toLowerCase;
        }
    }

    public static class Trim extends StringPipe<TypeString.trim> implements AnnotablePipe<TypeString.trim, String, String> {
        @Override
        public void configure(TypeString.trim cfg) {
            super.configure(cfg);
            this.function = String::trim;
        }
    }

    public static class Substring extends StringPipe<TypeString.substring> implements AnnotablePipe<TypeString.substring, String, String> {

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

    public static class Replace extends StringPipe<TypeString.replace> implements AnnotablePipe<TypeString.replace, String, String> {
        @Override
        public void configure(TypeString.replace cfg) {
            super.configure(cfg);
            final Pattern pattern = Pattern.compile(cfg.value(), Pattern.LITERAL);
            final String replacement = cfg.with();
            this.function = input -> pattern.matcher(input)
                                            .replaceAll(Matcher.quoteReplacement(replacement));
        }
    }

    public static class ReplaceAll extends StringPipe<TypeString.replaceAll> implements AnnotablePipe<TypeString.replaceAll, String, String> {
        @Override
        public void configure(TypeString.replaceAll cfg) {
            super.configure(cfg);
            final Pattern pattern = Pattern.compile(cfg.value(), Pattern.LITERAL);
            final String replacement = cfg.with();
            this.function = input -> pattern.matcher(input)
                                            .replaceAll(replacement);
        }
    }

    public static class Quote extends StringPipe<TypeString.quoted> implements AnnotablePipe<TypeString.quoted, String, String> {
        @Override
        public void configure(TypeString.quoted cfg) {
            super.configure(cfg);
            final char quote = cfg.value();
            this.function = input -> quote + input.replace("\"", "\"\"") + quote;
        }
    }

    public static class UnQuote extends StringPipe<TypeString.quoted> implements AnnotablePipe<TypeString.quoted, String, String> {
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
                if (input.charAt(0) == quote && input.charAt(last) == quote) input = input.substring(1, last);
                return input.replace("\"\"", "\"");
            };

        }
    }

    public static class FromNullableString extends StringPipe<TypeString.nullable> implements AnnotablePipe<TypeString.nullable, String, String> {

        @Override
        public void configure(TypeString.nullable cfg) {
            super.configure(cfg);
            final String nullRepresentation = cfg.value();
            this.function = string -> nullRepresentation.equals(string) ? null : string;
        }
    }

    public static class ToNullableString extends StringPipe<TypeString.nullable> implements AnnotablePipe<TypeString.nullable, String, String> {
        @Override
        public void configure(TypeString.nullable cfg) {
            super.configure(cfg);
            final String nullRepresentation = cfg.value();
            this.function = string -> string == null ? nullRepresentation : string;
        }
    }

    public static class ValueOf implements AnnotablePipe<TypeString.value, Object, String> {

        private String value;

        @Override
        public StrongType getInputType() {
            return TypeObject.strongType;
        }

        @Override
        public StrongType getOutputType() {
            return TypeString.strongType;
        }

        @Override
        public void configure(TypeString.value cfg) {
            this.value = cfg.value();
        }

        @Override
        public ThrowingFunction<Object, String> function() {
            return object -> this.value;
        }
    }

    public static class StringPipe<A extends Annotation> implements AnnotablePipe<A, String, String> {

        protected ThrowingFunction<String, String> function;
        private boolean nullSafe;

        @Override
        public StrongType getInputType() {
            return TypeString.strongType;
        }

        @Override
        public StrongType getOutputType() {
            return TypeString.strongType;
        }

        @Override
        public void configure(A cfg) {
            this.nullSafe = Annotator.of(cfg)
                                     .getAccessor("nullSafe")
                                     .getAs(cfg, Boolean.class, Boolean.FALSE);
        }

        @Override
        public ThrowingFunction<String, String> function() {
            if (this.nullSafe) return ThrowingFunction.nullSafe(this.function);
            return this.function;
        }
    }

    public static class ReadLinesPipe implements AnnotablePipe<TypeString.IOStreamLines, Reader, Stream<String>> {
        private ThrowingFunction<Reader, Stream<String>> function;

        @Override
        public StrongType getInputType() {
            return StrongType.of(Reader.class);
        }

        @Override
        public void configure(TypeString.IOStreamLines cfg) {
            final int bufferSize = cfg.bufferSizeKB() == 0 ? 8192 : cfg.bufferSizeKB() * Annotations.KB;
            this.function = reader -> new AutoClosingBufferedReader(reader, bufferSize).lines();
        }

        @Override
        public ThrowingFunction<Reader, Stream<String>> function() {
            return this.function;
        }
    }

    public static class WriteLinesPipe implements AnnotablePipe<TypeString.IOStreamLines, Stream<String>, ThrowingConsumer<Writer>> {
        private String separator;
        private int bufferSize;

        @Override
        public void configure(TypeString.IOStreamLines cfg) {
            this.bufferSize = cfg.bufferSizeKB() == 0 ? 8192 : cfg.bufferSizeKB() * Annotations.KB;
            this.separator = cfg.separator();

        }

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

    public static class JoinArray implements AnnotablePipe<TypeString.Array, String[], String> {
        private final AtomicInteger lineLenght = new AtomicInteger(80);
        private ThrowingFunction<String[], String> function;

        @Override
        public StrongType getInputType() {
            return StrongType.of(String[].class);
        }

        @Override
        public StrongType getOutputType() {
            return TypeString.strongType;
        }

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

        @Override
        public ThrowingFunction<String[], String> function() {
            return this.function;
        }
    }

    public static class SplitArray implements AnnotablePipe<TypeString.Array, String, String[]> {
        private ThrowingFunction<String, String[]> function;

        @Override
        public StrongType getInputType() {
            return TypeString.strongType;
        }

        @Override
        public StrongType getOutputType() {
            return StrongType.of(String[].class);
        }

        @Override
        public void configure(TypeString.Array cfg) {
            final String separator = cfg.separator();
            this.function = line -> line.split(separator, -1);
            this.function = this.function.nullSafe(cfg.nullSafe());
        }

        @Override
        public ThrowingFunction<String, String[]> function() {
            return this.function;
        }
    }


    public static class JoinList implements AnnotablePipe<TypeString.List, Collection<String>, String> {
        private final AtomicInteger lineLenght = new AtomicInteger(80);
        private ThrowingFunction<Collection<String>, String> function;

        @Override
        public StrongType getInputType() {
            return StrongType.of(Collection.class)
                             .parameterizedWith(TypeString.strongType);
        }

        @Override
        public StrongType getOutputType() {
            return TypeString.strongType;
        }

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

        @Override
        public ThrowingFunction<Collection<String>, String> function() {
            return this.function;
        }
    }

    public static class SplitList implements AnnotablePipe<TypeString.List, String, List<String>> {
        private ThrowingFunction<String, List<String>> function;

        @Override
        public StrongType getInputType() {
            return TypeString.strongType;
        }

        @Override
        public StrongType getOutputType() {
            return StrongType.of(List.class)
                             .parameterizedWith(TypeString.strongType);
        }

        @Override
        public void configure(TypeString.List cfg) {
            final String separator = cfg.separator();
            this.function = line -> Arrays.asList(line.split(separator, -1));
            this.function = this.function.nullSafe(cfg.nullSafe());
        }

        @Override
        public ThrowingFunction<String, List<String>> function() {
            return this.function;
        }
    }

    public static class JoinSet implements AnnotablePipe<TypeString.Set, Collection<String>, String> {
        private final AtomicInteger lineLenght = new AtomicInteger(80);
        private ThrowingFunction<Collection<String>, String> function;

        @Override
        public StrongType getInputType() {
            return StrongType.of(Collection.class)
                             .parameterizedWith(TypeString.strongType);
        }

        @Override
        public StrongType getOutputType() {
            return TypeString.strongType;
        }

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

        @Override
        public ThrowingFunction<Collection<String>, String> function() {
            return this.function;
        }
    }

    public static class SplitSet implements AnnotablePipe<TypeString.Set, String, Set<String>> {
        private ThrowingFunction<String, Set<String>> function;

        @Override
        public StrongType getInputType() {
            return TypeString.strongType;
        }

        @Override
        public StrongType getOutputType() {
            return StrongType.of(Set.class)
                             .parameterizedWith(TypeString.strongType);
        }

        @Override
        public void configure(TypeString.Set cfg) {
            final String separator = cfg.separator();
            this.function = line -> Sets.newHashSet(line.split(separator, -1));
            this.function = this.function.nullSafe(cfg.nullSafe());
        }

        @Override
        public ThrowingFunction<String, Set<String>> function() {
            return this.function;
        }
    }

    public static class JoinStream implements AnnotablePipe<TypeString.Stream, java.util.stream.Stream<String>, String> {
        private ThrowingFunction<java.util.stream.Stream<String>, String> function;

        @Override
        public StrongType getInputType() {
            return StrongType.of(Stream.class)
                             .parameterizedWith(TypeString.strongType);
        }

        @Override
        public StrongType getOutputType() {
            return TypeString.strongType;
        }

        @Override
        public void configure(TypeString.Stream cfg) {
            final String separator = cfg.separator();
            this.function = stream -> stream.collect(Collectors.joining(separator));
            this.function = this.function.nullSafe(cfg.nullSafe());
        }

        @Override
        public ThrowingFunction<java.util.stream.Stream<String>, String> function() {
            return this.function;
        }
    }

    public static class SplitStream implements AnnotablePipe<TypeString.Stream, String, Stream<String>> {
        private ThrowingFunction<String, Stream<String>> function;

        @Override
        public StrongType getInputType() {
            return TypeString.strongType;
        }

        @Override
        public StrongType getOutputType() {
            return StrongType.of(Stream.class)
                             .parameterizedWith(TypeString.strongType);
        }

        @Override
        public void configure(TypeString.Stream cfg) {
            final String separator = cfg.separator();
            this.function = line -> Stream.of(line.split(separator, -1));
            this.function = this.function.nullSafe(cfg.nullSafe());
        }

        @Override
        public ThrowingFunction<String, Stream<String>> function() {
            return this.function;
        }
    }

    public static class ReadCellValue implements AnnotablePipe<TypeString.cellValue, Cell, String> {
        private ThrowingFunction<Cell, String> function;

        @Override
        public StrongType getInputType() {
            return StrongType.of(Cell.class);
        }

        @Override
        public StrongType getOutputType() {
            return TypeString.strongType;
        }

        @Override
        public void configure(TypeString.cellValue cfg) {
            this.function = cell -> {
                if (cfg.nullSafe() && cell.getCellType() == CellType.BLANK) return null;
                return cell.getStringCellValue();
            };
            this.function = this.function.nullSafe(cfg.nullSafe());
        }

        @Override
        public ThrowingFunction<Cell, String> function() {
            return this.function;
        }
    }


    public static class WriteCellValue implements AnnotablePipe<TypeString.cellValue, String, ThrowingConsumer<Cell>> {
        private ThrowingFunction<String, ThrowingConsumer<Cell>> function;

        @Override
        public StrongType getInputType() {
            return TypeString.strongType;
        }

        @Override
        public void configure(TypeString.cellValue cfg) {
            if (cfg.nullSafe())
                this.function = string -> string == null ? cell -> cell.setBlank() : cell -> cell.setCellValue(string);
            else
                this.function = string -> cell -> cell.setCellValue(string);
        }

        @Override
        public ThrowingFunction<String, ThrowingConsumer<Cell>> function() {
            return this.function;
        }
    }
}
