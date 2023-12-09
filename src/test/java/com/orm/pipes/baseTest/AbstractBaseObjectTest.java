package com.orm.pipes.baseTest;

import com.orm.pipes.csv.csvFile.CSVFile;
import com.orm.pipes.typeFile.FilePipes;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import java.io.File;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Execution(ExecutionMode.CONCURRENT)
public abstract class AbstractBaseObjectTest<T> {


    protected CSVFile.StringObjectParser<T> parser;
    protected String testFileName;
    protected File file;
    private String extension;

    protected void init(Class<T> clazz, String extension) {

        this.extension = extension;
        this.parser = new CSVFile.StringObjectParser<>(clazz);
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
}
