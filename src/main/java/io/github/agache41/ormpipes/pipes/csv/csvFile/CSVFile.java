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

import static io.github.agache41.ormpipes.config.Annotations.DEFAULT;

@Repeatable(CSVFiles.class)
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Extends({DualPipe.class, OrthogonalFile.class})
public @interface CSVFile {

    String extension = ".csv";

    String[] header() default {};

    boolean useFirstLineAsHeader() default true;

    int skipFirstNLines() default 0;

    NamingMethod namingMethod() default NamingMethod.AccessorNames;

    PositionMethod positionMethod() default PositionMethod.CSVFields;

    Model model() default Model.VariablePositions;

    String[] enabledOn() default {"read", "write"};

    Class<? extends AnnotablePipe<CSVFile, Stream<String[]>, Stream<?>>> read() default CSVFileReader.class;

    Class<? extends AnnotablePipe<CSVFile, Stream<?>, Stream<String[]>>> write() default CSVFileWriter.class;

    String view() default DEFAULT;

    class StringStreamParser<T> extends Parser<T, String, Stream<T>> {
        public StringStreamParser(Class<T> clazz) {
            super(clazz, String.class);
        }

        public StringStreamParser(Class<T> clazz, String view) {
            super(clazz, String.class, view);
        }

        public static <S> StringStreamParser<S> ofClass(Class<S> clazz) {
            return new StringStreamParser<>(clazz);
        }

        public static <S> StringStreamParser<S> ofClass(Class<S> clazz, String view) {
            return new StringStreamParser<>(clazz, view);
        }
    }

    class StringObjectParser<T> extends Parser<T, String, T> {
        public StringObjectParser(Class<T> clazz) {
            super(clazz, String.class);
        }

        public StringObjectParser(Class<T> clazz, String view) {
            super(clazz, String.class, view);
        }

        public static <S> StringObjectParser<S> ofClass(Class<S> clazz) {
            return new StringObjectParser<>(clazz);
        }

        public static <S> StringObjectParser<S> ofClass(Class<S> clazz, String view) {
            return new StringObjectParser<>(clazz, view);
        }
    }
}