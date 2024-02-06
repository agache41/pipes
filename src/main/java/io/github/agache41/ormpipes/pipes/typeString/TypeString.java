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

import static io.github.agache41.ormpipes.config.Constants.DEFAULT;

/**
 * <pre>
 * The interface Type string.
 * </pre>
 */
@Repeatable(TypeStrings.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
public @interface TypeString {
    /**
     * <pre>
     * The constant strongType.
     * </pre>
     */
    StrongType strongType = StrongType.of(String.class);

    /**
     * <pre>
     * The interface To upper case.
     * </pre>
     */
    @Repeatable(TypeStrings.toUpperCases.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends(DualPipe.class)
    @interface toUpperCase {
        /**
         * <pre>
         * Null safe boolean.
         * </pre>
         *
         * @return the boolean
         */
        boolean nullSafe() default true;

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
        Class<? extends AnnotablePipe<toUpperCase, String, String>> read() default StringPipes.ToUppercase.class;

        /**
         * <pre>
         * Write class.
         * </pre>
         *
         * @return the class
         */
        Class<? extends AnnotablePipe<TypeString.toUpperCase, String, String>> write() default StringPipes.ToUppercase.class;

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
     * The interface To lower case.
     * </pre>
     */
    @Repeatable(TypeStrings.toLowerCases.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends(DualPipe.class)
    @interface toLowerCase {

        /**
         * <pre>
         * Null safe boolean.
         * </pre>
         *
         * @return the boolean
         */
        boolean nullSafe() default true;

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
        Class<? extends AnnotablePipe<TypeString.toLowerCase, String, String>> read() default StringPipes.ToLowerCase.class;

        /**
         * <pre>
         * Write class.
         * </pre>
         *
         * @return the class
         */
        Class<? extends AnnotablePipe<TypeString.toLowerCase, String, String>> write() default StringPipes.ToLowerCase.class;

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
     * The interface Trim.
     * </pre>
     */
    @Repeatable(TypeStrings.trims.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends(DualPipe.class)
    @interface trim {

        /**
         * <pre>
         * Null safe boolean.
         * </pre>
         *
         * @return the boolean
         */
        boolean nullSafe() default true;

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
        Class<? extends AnnotablePipe<TypeString.trim, String, String>> read() default StringPipes.Trim.class;

        /**
         * <pre>
         * Write class.
         * </pre>
         *
         * @return the class
         */
        Class<? extends AnnotablePipe<TypeString.trim, String, String>> write() default StringPipes.Trim.class;

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
     * The interface Substring.
     * </pre>
     */
    @Repeatable(TypeStrings.substrings.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends(DualPipe.class)
    @interface substring {
        /**
         * <pre>
         * The constant END_OF_INPUT.
         * </pre>
         */
        int END_OF_INPUT = -1;

        /**
         * <pre>
         * Begin index int.
         * </pre>
         *
         * @return the int
         */
        int beginIndex();

        /**
         * <pre>
         * End index int.
         * </pre>
         *
         * @return the int
         */
        int endIndex() default END_OF_INPUT;

        /**
         * <pre>
         * Null safe boolean.
         * </pre>
         *
         * @return the boolean
         */
        boolean nullSafe() default true;

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
        Class<? extends AnnotablePipe<TypeString.substring, String, String>> read() default StringPipes.Substring.class;

        /**
         * <pre>
         * Write class.
         * </pre>
         *
         * @return the class
         */
        Class<? extends AnnotablePipe<TypeString.substring, String, String>> write() default StringPipes.Substring.class;

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
     * The interface Replace.
     * </pre>
     */
    @Repeatable(TypeStrings.replaces.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends(DualPipe.class)
    @interface replace {

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
         * With string.
         * </pre>
         *
         * @return the string
         */
        String with();

        /**
         * <pre>
         * Null safe boolean.
         * </pre>
         *
         * @return the boolean
         */
        boolean nullSafe() default true;

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
        Class<? extends AnnotablePipe<TypeString.replace, String, String>> read() default StringPipes.Replace.class;

        /**
         * <pre>
         * Write class.
         * </pre>
         *
         * @return the class
         */
        Class<? extends AnnotablePipe<TypeString.replace, String, String>> write() default StringPipes.Replace.class;

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
     * The interface Replace all.
     * </pre>
     */
    @Repeatable(TypeStrings.replaceAlls.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends(DualPipe.class)
    @interface replaceAll {

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
         * With string.
         * </pre>
         *
         * @return the string
         */
        String with();

        /**
         * <pre>
         * Null safe boolean.
         * </pre>
         *
         * @return the boolean
         */
        boolean nullSafe() default true;

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
        Class<? extends AnnotablePipe<TypeString.replaceAll, String, String>> read() default StringPipes.ReplaceAll.class;

        /**
         * <pre>
         * Write class.
         * </pre>
         *
         * @return the class
         */
        Class<? extends AnnotablePipe<TypeString.replaceAll, String, String>> write() default StringPipes.ReplaceAll.class;

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
     * The interface Quoted.
     * </pre>
     */
    @Repeatable(TypeStrings.quoteds.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends(DualPipe.class)
    @interface quoted {

        /**
         * <pre>
         * Value char.
         * </pre>
         *
         * @return the char
         */
        char value() default '"';

        /**
         * <pre>
         * Null safe boolean.
         * </pre>
         *
         * @return the boolean
         */
        boolean nullSafe() default true;

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
        Class<? extends AnnotablePipe<quoted, String, String>> read() default StringPipes.UnQuote.class;

        /**
         * <pre>
         * Write class.
         * </pre>
         *
         * @return the class
         */
        Class<? extends AnnotablePipe<quoted, String, String>> write() default StringPipes.Quote.class;

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
     * The interface Nullable.
     * </pre>
     */
    @Repeatable(TypeStrings.nullables.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends(DualPipe.class)
    @interface nullable {

        /**
         * <pre>
         * Value string.
         * </pre>
         *
         * @return the string
         */
        String value() default "[NULL]";

        /**
         * <pre>
         * Null safe boolean.
         * </pre>
         *
         * @return the boolean
         */
        boolean nullSafe() default false;

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
        Class<? extends AnnotablePipe<nullable, String, String>> read() default StringPipes.FromNullableString.class;

        /**
         * <pre>
         * Write class.
         * </pre>
         *
         * @return the class
         */
        Class<? extends AnnotablePipe<nullable, String, String>> write() default StringPipes.ToNullableString.class;

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
     * The interface Value.
     * </pre>
     */
    @Repeatable(TypeStrings.values.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends(DualPipe.class)
    @interface value {

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
         * Null safe boolean.
         * </pre>
         *
         * @return the boolean
         */
        boolean nullSafe() default false;

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
        Class<? extends AnnotablePipe<value, Object, String>> read() default StringPipes.ValueOf.class;

        /**
         * <pre>
         * Write class.
         * </pre>
         *
         * @return the class
         */
        Class<? extends AnnotablePipe<value, Object, String>> write() default StringPipes.ValueOf.class;

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
     * The interface Io stream lines.
     * </pre>
     */
    @Repeatable(TypeStrings.IOLiness.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends(DualPipe.class)
    @interface IOStreamLines {

        /**
         * <pre>
         * The constant LF.
         * </pre>
         */
        String LF = "\n";
        /**
         * <pre>
         * The constant CR.
         * </pre>
         */
        String CR = "\r";
        /**
         * <pre>
         * The constant CRLF.
         * </pre>
         */
        String CRLF = "\r\n";
        /**
         * <pre>
         * The constant LINUX.
         * </pre>
         */
        String LINUX = LF;
        /**
         * <pre>
         * The constant WINDOWS.
         * </pre>
         */
        String WINDOWS = CRLF;
        /**
         * <pre>
         * The constant MACOS.
         * </pre>
         */
        String MACOS = CR;
        /**
         * <pre>
         * The constant NONE.
         * </pre>
         */
        String NONE = "";

        /**
         * <pre>
         * Separator string.
         * </pre>
         *
         * @return the string
         */
        String separator() default LINUX;

        /**
         * <pre>
         * Buffer size kb int.
         * </pre>
         *
         * @return the int
         */
        int bufferSizeKB() default 0;

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
        Class<? extends AnnotablePipe<IOStreamLines, Reader, java.util.stream.Stream<String>>> read() default StringPipes.ReadLinesPipe.class;

        /**
         * <pre>
         * Write class.
         * </pre>
         *
         * @return the class
         */
        Class<? extends AnnotablePipe<IOStreamLines, java.util.stream.Stream<String>, ThrowingConsumer<Writer>>> write() default StringPipes.WriteLinesPipe.class;

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
     * The interface Array.
     * </pre>
     */
    @Repeatable(TypeStrings.Arrays.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends(DualPipe.class)
    @interface Array {

        /**
         * <pre>
         * Separator string.
         * </pre>
         *
         * @return the string
         */
        String separator();

        /**
         * <pre>
         * Null safe boolean.
         * </pre>
         *
         * @return the boolean
         */
        boolean nullSafe() default true;

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
        Class<? extends AnnotablePipe<TypeString.Array, String, String[]>> read() default StringPipes.SplitArray.class;

        /**
         * <pre>
         * Write class.
         * </pre>
         *
         * @return the class
         */
        Class<? extends AnnotablePipe<TypeString.Array, String[], String>> write() default StringPipes.JoinArray.class;

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
     * The interface List.
     * </pre>
     */
    @Repeatable(TypeStrings.Lists.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends(DualPipe.class)
    @interface List {

        /**
         * <pre>
         * Separator string.
         * </pre>
         *
         * @return the string
         */
        String separator();

        /**
         * <pre>
         * Null safe boolean.
         * </pre>
         *
         * @return the boolean
         */
        boolean nullSafe() default true;

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
        Class<? extends AnnotablePipe<TypeString.List, String, java.util.List<String>>> read() default StringPipes.SplitList.class;

        /**
         * <pre>
         * Write class.
         * </pre>
         *
         * @return the class
         */
        Class<? extends AnnotablePipe<TypeString.List, Collection<String>, String>> write() default StringPipes.JoinList.class;

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
     * The interface Set.
     * </pre>
     */
    @Repeatable(TypeStrings.Sets.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends(DualPipe.class)
    @interface Set {

        /**
         * <pre>
         * Separator string.
         * </pre>
         *
         * @return the string
         */
        String separator();

        /**
         * <pre>
         * Null safe boolean.
         * </pre>
         *
         * @return the boolean
         */
        boolean nullSafe() default true;

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
        Class<? extends AnnotablePipe<TypeString.Set, String, java.util.Set<String>>> read() default StringPipes.SplitSet.class;

        /**
         * <pre>
         * Write class.
         * </pre>
         *
         * @return the class
         */
        Class<? extends AnnotablePipe<TypeString.Set, Collection<String>, String>> write() default StringPipes.JoinSet.class;

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
     * The interface Stream.
     * </pre>
     */
    @Repeatable(TypeStrings.Streams.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends(DualPipe.class)
    @interface Stream {

        /**
         * <pre>
         * Separator string.
         * </pre>
         *
         * @return the string
         */
        String separator();

        /**
         * <pre>
         * Null safe boolean.
         * </pre>
         *
         * @return the boolean
         */
        boolean nullSafe() default true;

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
        Class<? extends AnnotablePipe<TypeString.Stream, String, java.util.stream.Stream<String>>> read() default StringPipes.SplitStream.class;

        /**
         * <pre>
         * Write class.
         * </pre>
         *
         * @return the class
         */
        Class<? extends AnnotablePipe<TypeString.Stream, java.util.stream.Stream<String>, String>> write() default StringPipes.JoinStream.class;

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
     * The interface Cell value.
     * </pre>
     */
    @Repeatable(TypeStrings.CellValues.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends(DualPipe.class)
    @interface cellValue {

        /**
         * <pre>
         * Null safe boolean.
         * </pre>
         *
         * @return the boolean
         */
        boolean nullSafe() default true;

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
        Class<? extends AnnotablePipe<cellValue, Cell, String>> read() default StringPipes.ReadCellValue.class;

        /**
         * <pre>
         * Write class.
         * </pre>
         *
         * @return the class
         */
        Class<? extends AnnotablePipe<cellValue, String, ThrowingConsumer<Cell>>> write() default StringPipes.WriteCellValue.class;

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
