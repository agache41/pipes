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

import io.github.agache41.ormpipes.pipes.baseTest.values.AbstractValuesFeeder;
import io.github.agache41.ormpipes.pipes.baseTest.values.TestBean;
import io.github.agache41.ormpipes.pipes.baseTest.values.Values;
import io.github.agache41.ormpipes.pipes.csv.csvFile.CSVFile;
import io.github.agache41.ormpipes.pipes.typeFile.FilePipes;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import java.io.File;
import java.lang.reflect.Constructor;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Execution(ExecutionMode.CONCURRENT)
public abstract class AbstractBaseStreamTest<T extends TestBean> extends AbstractValuesFeeder<T> implements Supplier<T>, Values<T> {

    protected CSVFile.StringStreamParser<T> parser;

    protected List<T> values;
    protected List<T> lessValues;
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

        this.parser = new CSVFile.StringStreamParser<T>(clazz);
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

    protected List<T> readFile(String suffix) throws Throwable {
        String suffixFileName = this.getTestFileName(suffix);
        File suffixColumnsFile = this.getTestFile(suffixFileName);
        Assertions.assertTrue(suffixColumnsFile.exists());

        List<T> readValues = this.parser.read(suffixFileName)
                                        .collect(Collectors.toList());
        Assertions.assertNotNull(readValues);
        return readValues;
    }

    /**
* {@inheritDoc}
*/
@Override
    public T get() {
        try {
            return this.constructor.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
* {@inheritDoc}
*/
@Override
    public List<T> getValues() {
        return this.values;
    }

    /**
* {@inheritDoc}
*/
@Override
    public List<T> getLessValues() {
        return this.lessValues;
    }
}
