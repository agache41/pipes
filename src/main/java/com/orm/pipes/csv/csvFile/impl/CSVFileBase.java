package com.orm.pipes.csv.csvFile.impl;

import com.orm.functional.Helper;
import com.orm.pipes.csv.csvFile.CSVFile;
import com.orm.reflection.accessor.Accessor;
import com.orm.reflection.registry.Annotable;
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


