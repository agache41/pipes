package io.github.agache41.ormpipes.pipes.typeString;

import io.github.agache41.annotator.annotations.Extends;
import io.github.agache41.ormpipes.functional.StrongType;
import io.github.agache41.ormpipes.functional.ThrowingConsumer;
import io.github.agache41.ormpipes.pipe.AnnotablePipe;
import io.github.agache41.ormpipes.pipe.DualPipe;
import org.apache.poi.ss.usermodel.Cell;

import java.io.Reader;
import java.io.Writer;
import java.lang.annotation.*;
import java.util.Collection;

import static io.github.agache41.ormpipes.config.Annotations.DEFAULT;

@Repeatable(TypeStrings.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
public @interface TypeString {
    StrongType strongType = StrongType.of(String.class);

    @Repeatable(TypeStrings.toUpperCases.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends(DualPipe.class)
    @interface toUpperCase {
        boolean nullSafe() default true;

        String[] enabledOn() default {"read", "write"};

        Class<? extends AnnotablePipe<toUpperCase, String, String>> read() default StringPipes.ToUppercase.class;

        Class<? extends AnnotablePipe<TypeString.toUpperCase, String, String>> write() default StringPipes.ToUppercase.class;

        String view() default DEFAULT;
    }

    @Repeatable(TypeStrings.toLowerCases.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends(DualPipe.class)
    @interface toLowerCase {

        boolean nullSafe() default true;

        String[] enabledOn() default {"read", "write"};

        Class<? extends AnnotablePipe<TypeString.toLowerCase, String, String>> read() default StringPipes.ToLowerCase.class;

        Class<? extends AnnotablePipe<TypeString.toLowerCase, String, String>> write() default StringPipes.ToLowerCase.class;

        String view() default DEFAULT;
    }

    @Repeatable(TypeStrings.trims.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends(DualPipe.class)
    @interface trim {

        boolean nullSafe() default true;

        String[] enabledOn() default {"read", "write"};

        Class<? extends AnnotablePipe<TypeString.trim, String, String>> read() default StringPipes.Trim.class;

        Class<? extends AnnotablePipe<TypeString.trim, String, String>> write() default StringPipes.Trim.class;

        String view() default DEFAULT;
    }

    @Repeatable(TypeStrings.substrings.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends(DualPipe.class)
    @interface substring {
        int END_OF_INPUT = -1;

        int beginIndex();

        int endIndex() default END_OF_INPUT;

        boolean nullSafe() default true;

        String[] enabledOn() default {"read", "write"};

        Class<? extends AnnotablePipe<TypeString.substring, String, String>> read() default StringPipes.Substring.class;

        Class<? extends AnnotablePipe<TypeString.substring, String, String>> write() default StringPipes.Substring.class;

        String view() default DEFAULT;
    }

    @Repeatable(TypeStrings.replaces.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends(DualPipe.class)
    @interface replace {

        String value();

        String with();

        boolean nullSafe() default true;

        String[] enabledOn() default {"read", "write"};

        Class<? extends AnnotablePipe<TypeString.replace, String, String>> read() default StringPipes.Replace.class;

        Class<? extends AnnotablePipe<TypeString.replace, String, String>> write() default StringPipes.Replace.class;

        String view() default DEFAULT;
    }

    @Repeatable(TypeStrings.replaceAlls.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends(DualPipe.class)
    @interface replaceAll {

        String value();

        String with();

        boolean nullSafe() default true;

        String[] enabledOn() default {"read", "write"};

        Class<? extends AnnotablePipe<TypeString.replaceAll, String, String>> read() default StringPipes.ReplaceAll.class;

        Class<? extends AnnotablePipe<TypeString.replaceAll, String, String>> write() default StringPipes.ReplaceAll.class;

        String view() default DEFAULT;
    }

    @Repeatable(TypeStrings.quoteds.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends(DualPipe.class)
    @interface quoted {

        char value() default '"';

        boolean nullSafe() default true;

        String[] enabledOn() default {"read", "write"};

        Class<? extends AnnotablePipe<quoted, String, String>> read() default StringPipes.UnQuote.class;

        Class<? extends AnnotablePipe<quoted, String, String>> write() default StringPipes.Quote.class;

        String view() default DEFAULT;
    }

    @Repeatable(TypeStrings.nullables.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends(DualPipe.class)
    @interface nullable {

        String value() default "[NULL]";

        boolean nullSafe() default false;

        String[] enabledOn() default {"read", "write"};

        Class<? extends AnnotablePipe<nullable, String, String>> read() default StringPipes.FromNullableString.class;

        Class<? extends AnnotablePipe<nullable, String, String>> write() default StringPipes.ToNullableString.class;

        String view() default DEFAULT;
    }

    @Repeatable(TypeStrings.values.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends(DualPipe.class)
    @interface value {

        String value();

        boolean nullSafe() default false;

        String[] enabledOn() default {"read", "write"};

        Class<? extends AnnotablePipe<value, Object, String>> read() default StringPipes.ValueOf.class;

        Class<? extends AnnotablePipe<value, Object, String>> write() default StringPipes.ValueOf.class;

        String view() default DEFAULT;
    }

    @Repeatable(TypeStrings.IOLiness.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends(DualPipe.class)
    @interface IOStreamLines {

        String LF = "\n";
        String CR = "\r";
        String CRLF = "\r\n";
        String LINUX = LF;
        String WINDOWS = CRLF;
        String MACOS = CR;
        String NONE = "";

        String separator() default LINUX;

        int bufferSizeKB() default 0;

        String[] enabledOn() default {"read", "write"};

        Class<? extends AnnotablePipe<IOStreamLines, Reader, java.util.stream.Stream<String>>> read() default StringPipes.ReadLinesPipe.class;

        Class<? extends AnnotablePipe<IOStreamLines, java.util.stream.Stream<String>, ThrowingConsumer<Writer>>> write() default StringPipes.WriteLinesPipe.class;

        String view() default DEFAULT;
    }

    @Repeatable(TypeStrings.Arrays.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends(DualPipe.class)
    @interface Array {

        String separator();

        boolean nullSafe() default true;

        String[] enabledOn() default {"read", "write"};

        Class<? extends AnnotablePipe<TypeString.Array, String, String[]>> read() default StringPipes.SplitArray.class;

        Class<? extends AnnotablePipe<TypeString.Array, String[], String>> write() default StringPipes.JoinArray.class;

        String view() default DEFAULT;

    }

    @Repeatable(TypeStrings.Lists.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends(DualPipe.class)
    @interface List {

        String separator();

        boolean nullSafe() default true;

        String[] enabledOn() default {"read", "write"};

        Class<? extends AnnotablePipe<TypeString.List, String, java.util.List<String>>> read() default StringPipes.SplitList.class;

        Class<? extends AnnotablePipe<TypeString.List, Collection<String>, String>> write() default StringPipes.JoinList.class;

        String view() default DEFAULT;

    }

    @Repeatable(TypeStrings.Sets.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends(DualPipe.class)
    @interface Set {

        String separator();

        boolean nullSafe() default true;

        String[] enabledOn() default {"read", "write"};

        Class<? extends AnnotablePipe<TypeString.Set, String, java.util.Set<String>>> read() default StringPipes.SplitSet.class;

        Class<? extends AnnotablePipe<TypeString.Set, Collection<String>, String>> write() default StringPipes.JoinSet.class;

        String view() default DEFAULT;

    }

    @Repeatable(TypeStrings.Streams.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends(DualPipe.class)
    @interface Stream {

        String separator();

        boolean nullSafe() default true;

        String[] enabledOn() default {"read", "write"};

        Class<? extends AnnotablePipe<TypeString.Stream, String, java.util.stream.Stream<String>>> read() default StringPipes.SplitStream.class;

        Class<? extends AnnotablePipe<TypeString.Stream, java.util.stream.Stream<String>, String>> write() default StringPipes.JoinStream.class;

        String view() default DEFAULT;

    }

    @Repeatable(TypeStrings.CellValues.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends(DualPipe.class)
    @interface cellValue {

        boolean nullSafe() default true;

        String[] enabledOn() default {"read", "write"};

        Class<? extends AnnotablePipe<cellValue, Cell, String>> read() default StringPipes.ReadCellValue.class;

        Class<? extends AnnotablePipe<cellValue, String, ThrowingConsumer<Cell>>> write() default StringPipes.WriteCellValue.class;

        String view() default DEFAULT;

    }
}
