package com.orm.pipes.baseTest;

import com.orm.pipes.csv.csvFile.CSVFile;
import com.orm.pipes.typeFile.FilePipes;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import java.io.File;
import java.lang.reflect.Constructor;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Execution(ExecutionMode.CONCURRENT)
public abstract class AbstractBaseObjectTest<T> {


    protected CSVFile.StringObjectParser<T> parser;
    protected String testFileName;
    protected File file;
    private Constructor<T> constructor;
    private String extension;

    protected void init(Class<T> clazz, String extension) {

        this.extension = extension;
        try {
            this.constructor = clazz.getConstructor();
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

        this.parser = new CSVFile.StringObjectParser<T>(clazz);
        this.testFileName = this.getTestFileName("");
        this.file = this.getTestFile(this.testFileName);
    }

    protected File getTestFile(String testFileName) {
        return new File(FilePipes.resourceDirectory, testFileName);
    }

    protected String getTestFileName(String suffix) {
        return "../../src/test/java/" + this.getClass()
                                            .getCanonicalName()
                                            .replace('.', '/') + suffix + this.extension;
    }

    protected T readFile(String suffix) throws Throwable {
        String suffixFileName = this.getTestFileName(suffix);
        File suffixColumnsFile = this.getTestFile(suffixFileName);
        Assertions.assertTrue(suffixColumnsFile.exists());
        T value = this.parser.read(suffixFileName);
        Assertions.assertNotNull(value);
        Assertions.assertTrue(suffixColumnsFile.delete());
        Assertions.assertFalse(suffixColumnsFile.exists());
        return value;
    }

    protected void writeFile(T bean, String suffix) throws Throwable {
        String suffixFileName = this.getTestFileName(suffix);
        File suffixColumnsFile = this.getTestFile(suffixFileName);
        suffixColumnsFile.delete();
        Assertions.assertFalse(suffixColumnsFile.exists());
        this.parser.write(suffixFileName, bean);
        Assertions.assertTrue(suffixColumnsFile.exists());
    }


    protected void testWriteRead(T bean, String suffix) throws Throwable {
        this.writeFile(bean, suffix);
        T readBean = this.readFile("");
        System.out.println("Written :" + bean);
        System.out.println("Read    :" + bean);
        Assertions.assertEquals(bean, readBean);
    }

}
