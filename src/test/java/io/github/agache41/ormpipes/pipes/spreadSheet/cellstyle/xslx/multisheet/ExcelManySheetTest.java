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

package io.github.agache41.ormpipes.pipes.spreadSheet.cellstyle.xslx.multisheet;

import io.github.agache41.ormpipes.pipes.baseTest.AbstractBaseObjectTest;
import io.github.agache41.ormpipes.pipes.baseTest.values.AbstractValuesFeeder;
import io.github.agache41.ormpipes.pipes.spreadSheet.SpreadSheet;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import java.util.stream.Collectors;

class ExcelManySheetTest extends AbstractBaseObjectTest<ExcelTestMultiBean> {

    private final ExcelSheetTestBean1Feeder feeder1 = new ExcelSheetTestBean1Feeder();
    private final ExcelSheetTestBean2Feeder feeder2 = new ExcelSheetTestBean2Feeder();
    private final ExcelTestMultiBean
            bean = new ExcelTestMultiBean();

    public ExcelManySheetTest() {
        super.init(ExcelTestMultiBean.class, SpreadSheet.xlsx.extension);
        this.bean.setSheet1(this.feeder1.getValues()
                                        .stream());
        this.bean.setSheet2(this.feeder2.getValues()
                                        .stream());
    }

    @Test
    @Order(0)
    @Execution(ExecutionMode.SAME_THREAD)
    void testWrite() throws Throwable {
        this.writeFile(this.bean, "");
    }

    @Test
    @Order(1)
    @Execution(ExecutionMode.SAME_THREAD)
    void testRead() throws Throwable {
        ExcelTestMultiBean excelTestMultiBean = this.readFile("");
        System.out.println(excelTestMultiBean);
        Assertions.assertTrue(CollectionUtils.isEqualCollection(this.feeder1.getValues(), excelTestMultiBean.getSheet1()
                                                                                                            .collect(Collectors.toList())));
        Assertions.assertTrue(CollectionUtils.isEqualCollection(this.feeder2.getValues(), excelTestMultiBean.getSheet2()
                                                                                                            .collect(Collectors.toList())));

    }

    public static class ExcelSheetTestBean1Feeder extends AbstractValuesFeeder<ExcelSheetTestBean1> {
        /**
* {@inheritDoc}
*/
@Override
        public ExcelSheetTestBean1 get() {
            return new ExcelSheetTestBean1();
        }
    }

    public static class ExcelSheetTestBean2Feeder extends AbstractValuesFeeder<ExcelSheetTestBean2> {
        /**
* {@inheritDoc}
*/
@Override
        public ExcelSheetTestBean2 get() {
            return new ExcelSheetTestBean2();
        }
    }
}