package io.github.agache41.ormpipes.pipes.csv.csvFile.impl;

import io.github.agache41.annotator.accessor.Accessor;
import io.github.agache41.ormpipes.functional.Helper;
import io.github.agache41.ormpipes.pipe.registry.Annotable;
import io.github.agache41.ormpipes.pipes.csv.csvFile.CSVFile;
import org.jboss.logging.Logger;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.function.Supplier;

/**
 * <pre>
 * The type Csv file base.
 * </pre>
 */
public abstract class CSVFileBase implements Annotable<CSVFile>, Supplier<Object> {

    /**
     * <pre>
     * The constant logger.
     * </pre>
     */
    protected static final Logger logger = Logger.getLogger(CSVFile.class);
    /**
     * <pre>
     * The On class.
     * </pre>
     */
    protected Class<?> onClass;
    /**
     * <pre>
     * The Skip first n lines.
     * </pre>
     */
    protected int skipFirstNLines;
    /**
     * <pre>
     * The Use first line as header.
     * </pre>
     */
    protected boolean useFirstLineAsHeader;
    private Supplier<?> constructor;
    private CSVFile cfg;

    /**
     * {@inheritDoc}
     */
    @Override
    public Object get() {
        return this.constructor.get();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void configure(CSVFile cfg,
                          Class<?> onClass,
                          Field onField,
                          Method onMethod,
                          Accessor<?> onAccessor,
                          String operation) {
        this.onClass = onClass;
        this.constructor = Helper.constructor(onClass);
        this.configure(cfg);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void configure(CSVFile cfg) {
        this.cfg = cfg;
        this.useFirstLineAsHeader = cfg.useFirstLineAsHeader();
        this.skipFirstNLines = cfg.skipFirstNLines();
    }

    /**
     * <pre>
     * Handler csv file handler.
     * </pre>
     *
     * @return the csv file handler
     */
    protected CSVFileHandler handler() {
        return new CSVFileHandler(this.onClass, this.cfg);
    }

}


