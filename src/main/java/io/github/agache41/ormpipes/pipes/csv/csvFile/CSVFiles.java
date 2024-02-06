package io.github.agache41.ormpipes.pipes.csv.csvFile;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <pre>
 * The interface Csv files.
 * </pre>
 */
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface CSVFiles {
    /**
     * <pre>
     * Value csv file [ ].
     * </pre>
     *
     * @return the csv file [ ]
     */
    CSVFile[] value();
}
