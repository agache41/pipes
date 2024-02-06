package io.github.agache41.ormpipes.pipes.encoding;

import io.github.agache41.ormpipes.config.Constants;
import io.github.agache41.ormpipes.pipe.DualPipe;
import io.github.agache41.ormpipes.pipe.registry.PipeRegistry;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.jboss.logging.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;

import java.io.*;

@Nested
public abstract class BaseTestBean {
    public static final String TARGET_TEST_CLASSES = "target/test-classes";
    private static final Logger logger = Logger.getLogger(BaseTestBean.class);
    private final String readPath;
    private final String writePath;
    private final String expected;

    protected BaseTestBean(String readPath,
                           String writePath,
                           String expected) {
        this.readPath = readPath;
        this.writePath = writePath;
        this.expected = expected;
    }

    public String read(InputStream is) throws Throwable {
        try (Reader reader = (Reader) PipeRegistry.buildPipeFrom(this, Constants.DEFAULT, DualPipe.READ, false)
                                                  .function()
                                                  .apply(is)) {
            return IOUtils.toString(reader);
        }
    }

    public String readAuto(InputStream is) throws Throwable {
        try (Reader reader = (Reader) PipeRegistry.buildPipeFrom(this, Constants.DEFAULT, DualPipe.READ, false)
                                                  .function()
                                                  .apply(is)) {
            return IOUtils.toString(reader);
        }
    }

    public void write(String input,
                      OutputStream os) throws Throwable {
        try (Writer writer = (Writer) PipeRegistry.buildPipeFrom(this, Constants.DEFAULT, DualPipe.WRITE, true)
                                                  .function()
                                                  .apply(os)) {
            IOUtils.write(input,
                    writer);
        }
    }

    public String read(String path) throws Throwable {
        logger.infof("reading from path:%s", path);
        File file = new File(TARGET_TEST_CLASSES,
                path);
        return this.read(new FileInputStream(file));
    }

    public String readAuto(String path) throws Throwable {
        logger.infof(
                "reading from path:%s",
                path);
        File file = new File(TARGET_TEST_CLASSES,
                path);
        return this.readAuto(new FileInputStream(file));
    }

    public void write(String input,
                      String path) throws Throwable {
        logger.infof(
                "writing in path:%s",
                path);
        File file = new File(TARGET_TEST_CLASSES,
                path);
        file
                .getParentFile()
                .mkdirs();
        this.write(input,
                new FileOutputStream(file));
    }

    public BaseTestBean doReadTest() throws Throwable {
        logger.infof(
                "start reading test");
        String actual = this.read(this.readPath);
        Assertions.assertEquals(this.expected,
                actual);
        // do auto
        String actualAuto = this.readAuto(this.readPath);
        Assertions.assertEquals(this.expected,
                actualAuto);
        logger.infof(
                "end reading test");
        return this;
    }

    public BaseTestBean doWriteTest() throws Throwable {
        logger.infof(
                "start writing test");
        this.write(this.expected,
                this.writePath);
        String actual = this.read(this.writePath);
        Assertions.assertEquals(this.expected,
                actual);
        logger.infof(
                "end writing test");
        return this;
    }

    @IOEncoding.Automatic
    protected static class AutoEncoding {

    }
}
