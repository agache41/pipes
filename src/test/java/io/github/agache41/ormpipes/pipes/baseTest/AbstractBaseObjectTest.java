/*
 *    Copyright 2022-2023  Alexandru Agache
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package io.github.agache41.ormpipes.pipes.baseTest;

import io.github.agache41.ormpipes.pipes.base.parser.StringToBeanParser;
import io.github.agache41.ormpipes.pipes.csv.csvFile.CSVFile;
import io.github.agache41.ormpipes.pipes.typeFile.FilePipes;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import java.io.File;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Execution(ExecutionMode.CONCURRENT)
public abstract class AbstractBaseObjectTest<T> {


    protected StringToBeanParser<T> parser;
    protected String testFileName;
    protected File file;
    private String extension;

    protected void init(Class<T> clazz, String extension) {

        this.extension = extension;
        this.parser = new StringToBeanParser<>(clazz);
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
