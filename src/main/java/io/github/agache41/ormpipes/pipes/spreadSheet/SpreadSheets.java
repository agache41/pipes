package io.github.agache41.ormpipes.pipes.spreadSheet;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <pre>
 * The interface Spread sheets.
 * </pre>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
public @interface SpreadSheets {
    /**
     * <pre>
     * Value spread sheet [ ].
     * </pre>
     *
     * @return the spread sheet [ ]
     */
    SpreadSheet[] value();

    /**
     * <pre>
     * The interface Xlsxes.
     * </pre>
     */
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @interface xlsxes {
        /**
         * <pre>
         * Value spread sheet . xlsx [ ].
         * </pre>
         *
         * @return the spread sheet . xlsx [ ]
         */
        SpreadSheet.xlsx[] value();
    }

    /**
     * <pre>
     * The interface Xlses.
     * </pre>
     */
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @interface xlses {
        /**
         * <pre>
         * Value spread sheet . xls [ ].
         * </pre>
         *
         * @return the spread sheet . xls [ ]
         */
        SpreadSheet.xls[] value();
    }

    /**
     * <pre>
     * The interface Files.
     * </pre>
     */
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @interface files {
        /**
         * <pre>
         * Value spread sheet . file [ ].
         * </pre>
         *
         * @return the spread sheet . file [ ]
         */
        SpreadSheet.file[] value();
    }

    /**
     * <pre>
     * The interface Selects.
     * </pre>
     */
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @interface selects {
        /**
         * <pre>
         * Value spread sheet . select [ ].
         * </pre>
         *
         * @return the spread sheet . select [ ]
         */
        SpreadSheet.select[] value();
    }

    /**
     * <pre>
     * The interface Sheets.
     * </pre>
     */
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @interface sheets {
        /**
         * <pre>
         * Value spread sheet . sheet [ ].
         * </pre>
         *
         * @return the spread sheet . sheet [ ]
         */
        SpreadSheet.sheet[] value();
    }


    /**
     * <pre>
     * The interface Cell accessors.
     * </pre>
     */
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @interface CellAccessors {
        /**
         * <pre>
         * Value spread sheet . header [ ].
         * </pre>
         *
         * @return the spread sheet . header [ ]
         */
        SpreadSheet.Header[] value();
    }

    /**
     * <pre>
     * The interface Styles.
     * </pre>
     */
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @interface Styles {
        /**
         * <pre>
         * Value spread sheet . content style [ ].
         * </pre>
         *
         * @return the spread sheet . content style [ ]
         */
        SpreadSheet.ContentStyle[] value();
    }

    /**
     * <pre>
     * The interface Fonts.
     * </pre>
     */
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @interface Fonts {
        /**
         * <pre>
         * Value spread sheet . content font [ ].
         * </pre>
         *
         * @return the spread sheet . content font [ ]
         */
        SpreadSheet.ContentFont[] value();
    }

    /**
     * <pre>
     * The interface Formats.
     * </pre>
     */
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @interface Formats {
        /**
         * <pre>
         * Value spread sheet . content format [ ].
         * </pre>
         *
         * @return the spread sheet . content format [ ]
         */
        SpreadSheet.ContentFormat[] value();
    }

    /**
     * <pre>
     * The interface Custom styles.
     * </pre>
     */
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @interface CustomStyles {
        /**
         * <pre>
         * Value spread sheet . custom style [ ].
         * </pre>
         *
         * @return the spread sheet . custom style [ ]
         */
        SpreadSheet.CustomStyle[] value();
    }
}
