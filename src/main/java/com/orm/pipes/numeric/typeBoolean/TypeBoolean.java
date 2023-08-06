package com.orm.pipes.numeric.typeBoolean;

import com.orm.annotations.Extends;
import com.orm.functional.StrongType;
import com.orm.functional.ThrowingConsumer;
import com.orm.pipe.AnnotablePipe;
import com.orm.pipe.DualPipe;
import org.apache.poi.ss.usermodel.Cell;

import java.lang.annotation.*;

import static com.orm.annotations.Annotations.DEFAULT;

@Repeatable(TypeBooleans.class)
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface TypeBoolean {
    StrongType strongType = StrongType.of(Boolean.class);

    @Repeatable(TypeBooleans.News.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends(DualPipe.class)
    @interface New {
        /**
         * The values to be considered for the True Value.
         * The first provide value will be used on formatting.
         *
         * @return
         */
        String[] value() default {"true", "TRUE", "1"};

        /**
         * The values to be considered for the False Value.
         * The first provide value will be used on formatting.
         *
         * @return
         */
        String[] falseValue() default {"false", "FALSE", "0", "", "null", "empty"};

        /**
         * If set to true a null or unknown values is to be parsed as False, otherwise null.
         * If set to true a null value will be formatted as False, otherwise null.
         *
         * @return
         */
        boolean nullOrUnknownIsFalse() default false;

        /**
         * If enabled the parse(String input) and respectively toString() methods of the current type will be used.
         * The value and falseValue will be ignored.
         * <p>
         * Usage is not general recommended but in special cases can improve performance.
         *
         * @return
         */
        boolean simple() default false;

        /**
         * If set to true it will return upon parsing/formatting null for null input values.
         *
         * @return
         */
        boolean nullSafe() default true;

        /**
         * If set to true it will :
         * - for blank input values on parsing will return null
         * - for null values on formatting will return blank.
         * <p>
         * This setting overrides the nullSafe settings, if enabled.
         *
         * @return
         */
        boolean blankSafe() default true;

        /**
         * Process the input without throwing an exception,
         * in case the parsing was unsuccessful
         * it will return null on parsing.
         *
         * <p>
         * The setting has no effect on Formatting
         *
         * @return
         */
        boolean noException() default true;

        String[] enabledOn() default {"read", "write"};

        Class<? extends AnnotablePipe<New, String, Boolean>> read() default BooleanPipes.ParseBoolean.class;

        Class<? extends AnnotablePipe<New, Boolean, String>> write() default BooleanPipes.BooleanToString.class;

        String view() default DEFAULT;
    }

    @Repeatable(TypeBooleans.values.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends(DualPipe.class)
    @interface value {
        boolean value();

        String[] enabledOn() default {"read", "write"};

        Class<? extends AnnotablePipe<TypeBoolean.value, Object, Boolean>> read() default BooleanPipes.ValueOf.class;

        Class<? extends AnnotablePipe<TypeBoolean.value, Object, Boolean>> write() default BooleanPipes.ValueOf.class;

        String view() default DEFAULT;
    }

    @Repeatable(TypeBooleans.negates.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends(DualPipe.class)
    @interface negate {
        boolean nullSafe() default true;

        String[] enabledOn() default {"read", "write"};

        Class<? extends AnnotablePipe<Annotation, Boolean, Boolean>> read() default BooleanPipes.Negate.class;

        Class<? extends AnnotablePipe<Annotation, Boolean, Boolean>> write() default BooleanPipes.Negate.class;

        String view() default DEFAULT;
    }

    @Repeatable(TypeBooleans.CellValues.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends(DualPipe.class)
    @interface cellValue {

        boolean nullSafe() default true;

        String[] enabledOn() default {"read", "write"};

        Class<? extends AnnotablePipe<TypeBoolean.cellValue, Cell, Boolean>> read() default BooleanPipes.ReadCellValue.class;

        Class<? extends AnnotablePipe<TypeBoolean.cellValue, Boolean, ThrowingConsumer<Cell>>> write() default BooleanPipes.WriteCellValue.class;

        String view() default DEFAULT;

    }
}
