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

package io.github.agache41.ormpipes.pipes.csv.parser;

import io.github.agache41.ormpipes.pipes.base.parser.StringToStreamOfBeansParser;
import io.github.agache41.ormpipes.pipes.csv.csvFile.CSVFile;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Execution(ExecutionMode.SAME_THREAD)
class CSVParserTest {
    @BeforeAll
    static void clean() {
        File temp = new File("temporary");
        temp.mkdirs();
        for (File file : temp.listFiles()) {
            file.delete();
        }
    }

    @Test
    @Order(0)
    void testWrite() throws Throwable {
        List<CSVTestBean> data = Arrays.asList(CSVTestBean.builder()
                                                          .column1("col1")
                                                          .column2("col2")
                                                          .column3(1)
                                                          .build(),
                CSVTestBean.builder()
                           .column1("col11")
                           .column2("col22")
                           .column3(2)
                           .build(),
                CSVTestBean.builder()
                           .column1("col1111")
                           .column2("col2222")
                           .column3(3)
                           .build(),
                CSVTestBean.builder()
                           .column1("col1")
                           .column2("col2")
                           .column3(4)
                           .build());

        new StringToStreamOfBeansParser<>(CSVTestBean.class)
                .write("temporary/CSVTestFile.csv.zip", data.stream());
    }

    @Test
    @Order(1)
    void testRead() throws Throwable {
        new StringToStreamOfBeansParser<>(CSVTestBean.class)
                                  .read("temporary/CSVTestFile.csv.zip")
                                  .forEach(System.out::println);
    }


    @Test
    @Order(2)
    void testWriteRead() throws Throwable {
        List<CSVTestBean> data = Arrays.asList(CSVTestBean.builder()
                                                          .column1("col1")
                                                          .column2("col2")
                                                          .column3(1)
                                                          .build(),
                CSVTestBean.builder()
                           .column1("col11")
                           .column2("col22")
                           .column3(2)
                           .build(),
                CSVTestBean.builder()
                           .column1("col1111")
                           .column2("col2222")
                           .column3(3)
                           .build(),
                CSVTestBean.builder()
                           .column1("col1")
                           .column2("col2")
                           .column3(4)
                           .build());

        new StringToStreamOfBeansParser<>(CSVTestBean.class)
                                  .write("temporary/CSVTestFile.csv", data.stream());

        List<CSVTestBean> readList = new StringToStreamOfBeansParser<>(CSVTestBean.class)
                                                               .read("temporary/CSVTestFile.csv")
                                                               .collect(Collectors.toList());
        Assertions.assertEquals(data,
                readList);

    }
}