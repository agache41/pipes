package io.github.agache41.ormpipes.pipes.csv.csvFile.impl;

import io.github.agache41.ormpipes.functional.Helper;
import io.github.agache41.ormpipes.pipe.registry.Annotable;
import io.github.agache41.ormpipes.pipes.csv.csvFile.CSVFile;
import io.github.agache41.annotator.accessor.Accessor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.function.Supplier;

public abstract class CSVFileBase implements Annotable<CSVFile>, Supplier<Object> {

    protected static final Logger logger = LogManager.getLogger(CSVFile.class);
    protected Class<?> onClass;
    protected int skipFirstNLines;
    protected boolean useFirstLineAsHeader;
    private Supplier<?> constructor;
    private CSVFile cfg;

    @Override
    public Object get() {
        return this.constructor.get();
    }

    @Override
    public void configure(CSVFile cfg, Class<?> onClass, Field onField, Method onMethod, Accessor<?> onAccessor, String operation) {
        this.onClass = onClass;
        this.constructor = Helper.constructor(onClass);
        this.configure(cfg);
    }

    @Override
    public void configure(CSVFile cfg) {
        this.cfg = cfg;
        this.useFirstLineAsHeader = cfg.useFirstLineAsHeader();
        this.skipFirstNLines = cfg.skipFirstNLines();
    }

    protected CSVFileHandler handler() {
        return new CSVFileHandler(this.onClass, this.cfg);
    }

}


