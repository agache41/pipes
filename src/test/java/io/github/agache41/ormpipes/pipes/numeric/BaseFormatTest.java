package io.github.agache41.ormpipes.pipes.numeric;

import io.github.agache41.ormpipes.pipe.AnnotablePipe;
import io.github.agache41.ormpipes.pipe.registry.Registry;
import io.github.agache41.ormpipes.pipes.csv.csvFile.CSVFile;
import io.github.agache41.ormpipes.pipes.typeFile.FilePipes;
import io.github.agache41.ormpipes.pipes.zip.zipArchive.Zip;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.TestInstance;
import org.opentest4j.AssertionFailedError;

import java.io.File;
import java.lang.annotation.Annotation;

import static io.github.agache41.ormpipes.config.Annotations.DEFAULT;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@TestInstance(PER_CLASS)
public abstract class BaseFormatTest {


    protected static Logger logger = LogManager.getLogger(BaseFormatTest.class);

    protected void doRegressionTest(Class<? extends BaseTestConfigFor> configClass) throws Throwable {
        logger.info("Starting big Csv Test on " + configClass.getSimpleName());
        String fileSuffix = "";
        CSVFile.StringStreamParser.ofClass(configClass, fileSuffix.isEmpty() ? "zip" : DEFAULT)
                                  .read(this.testFileName(fileSuffix))
                                  .forEach(this::doTest);
    }

    protected <A extends Annotation, B extends BaseTestConfigFor, T> void doTest(B configAnnotation) {
        try {
            AnnotablePipe<A, String, T> read = Registry.createAndConfigureFromMethod(AnnotablePipe.class, configAnnotation, "read", this);
            AnnotablePipe<A, T, String> write = Registry.createAndConfigureFromMethod(AnnotablePipe.class, configAnnotation, "write", this);


            String output = write.function()
                                 .apply(read.function()
                                            .apply(configAnnotation.input()));
            if (configAnnotation.failed()) {
                fail(this.info(configAnnotation) + ":No exception was thrown expecting " + configAnnotation.exception()
                                                                                                           .getSimpleName());
            } else {
                assertEquals(configAnnotation.output(), output, this.info(configAnnotation));
            }
        } catch (Throwable e) {
            if (e instanceof AssertionFailedError) {
                throw (AssertionFailedError) e;
            }
            if (!configAnnotation.failed()) {
                fail(this.info(configAnnotation) + ":No exception was expected , found " + e.getClass()
                                                                                            .getCanonicalName(), e);
            } else {
                assertEquals(configAnnotation.exception(), e.getClass(), this.info(configAnnotation));
                String expectedExceptionMessage = configAnnotation.getExceptionMessage();
                String exceptionMessage = e.getMessage();
                if (expectedExceptionMessage == null) assertNull(exceptionMessage);
                else assertEquals(expectedExceptionMessage.length(), exceptionMessage.length());
            }
        }
    }

    private String info(BaseTestConfigFor configAnnotation) {
        return "On :" + configAnnotation.toString();
    }

    private String testFileName(String suffix) {
        String testFileName = this.getClass()
                                  .getCanonicalName()
                                  .replace('.', '/') + suffix + (suffix.isEmpty() ? Zip.Archive.extension : CSVFile.extension);
        logger.info("Testing {}", testFileName);
        return testFileName;
    }

    public boolean existsSmall() {
        return this.fileExists("small");
    }

    private boolean fileExists(String suffix) {
        String testFileName = this.testFileName(suffix);
        File testFile = new File(FilePipes.resourceDirectory, testFileName);
        return testFile.exists();
    }
}


