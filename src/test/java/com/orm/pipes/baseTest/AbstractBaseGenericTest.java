package com.orm.pipes.baseTest;

import com.orm.pipes.base.parser.Parser;
import com.orm.pipes.baseTest.values.AbstractValuesFeeder;
import com.orm.pipes.baseTest.values.TestBean;
import com.orm.pipes.baseTest.values.Values;
import com.orm.pipes.typeFile.FilePipes;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import java.io.File;
import java.lang.reflect.Constructor;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

import static com.orm.config.Settings.DEFAULT;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Execution(ExecutionMode.CONCURRENT)
public abstract class AbstractBaseGenericTest<T extends TestBean, Output> extends AbstractValuesFeeder<T> implements Supplier<T>, Values<T> {

    protected Parser<T, String, Output> parser;

    protected List<T> values;
    protected List<T> lessValues;
    protected String testFileName;
    protected File file;
    private Constructor<T> constructor;
    private String extension;


    protected void init(Class<T> clazz, String extension) {
        this.init(clazz, DEFAULT, extension);
    }

    protected void init(Class<T> clazz, String view, String extension) {
        this.extension = extension;
        try {
            this.constructor = clazz.getConstructor();
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

        this.parser = new Parser<T, String, Output>(clazz, String.class, view);
        this.testFileName = this.getTestFileName("");
        this.file = this.getTestFile(this.testFileName);
        this.values = super.getValues();
        this.lessValues = super.getLessValues();
    }

    protected File getTestFile(String testFileName) {
        return new File(FilePipes.resourceDirectory, testFileName);
    }

    protected String getTestFileName(String suffix) {
        return "../../src/test/java/" + this.getClass()
                                            .getCanonicalName()
                                            .replace('.', '/') + suffix + this.extension;
    }

    protected Output readFile(String suffix) throws Throwable {
        String suffixFileName = this.getTestFileName(suffix);
        File suffixColumnsFile = this.getTestFile(suffixFileName);
        Assertions.assertTrue(suffixColumnsFile.exists());
        Output readValues = this.parser.read(suffixFileName);
        Assertions.assertTrue(suffixColumnsFile.delete());
        Assertions.assertFalse(suffixColumnsFile.exists());
        System.out.println(readValues);
        Assertions.assertNotNull(readValues);
        return readValues;
    }

    protected void writeFile(String suffix, Output output) throws Throwable {
        String suffixFileName = this.getTestFileName(suffix);
        File suffixColumnsFile = this.getTestFile(suffixFileName);
        suffixColumnsFile.delete();
        Assertions.assertFalse(suffixColumnsFile.exists());
        this.parser.write(suffixFileName, output);
        Assertions.assertTrue(suffixColumnsFile.exists());
        Assertions.assertTrue(suffixColumnsFile.delete());
        this.parser.write(suffixFileName, output);
        Assertions.assertTrue(suffixColumnsFile.exists());
    }

    protected Output writeReadFile(String suffix, Output output) throws Throwable {
        this.writeFile(suffix, output);
        Output readOutput = this.readFile(suffix);
        System.out.println("Written:" + output);
        System.out.println("Read   :" + readOutput);
        if (output instanceof Collection) {
            Assertions.assertTrue(CollectionUtils.isEqualCollection((Collection<?>) output, (Collection<?>) readOutput));
        } else
            Assertions.assertTrue(Objects.deepEquals(output, readOutput));
        return readOutput;
    }

    @Override
    public T get() {
        try {
            return this.constructor.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<T> getValues() {
        return this.values;
    }

    @Override
    public List<T> getLessValues() {
        return this.lessValues;
    }
}
