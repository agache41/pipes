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

package examples.excel.multiSheet;

import io.github.agache41.ormpipes.pipes.base.parser.StringToBeanParser;
import io.github.agache41.ormpipes.pipes.typeFile.FilePipes;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class ExcelTest {
    private final String testFileName = "excelMultiSheet.xlsx";
    private final File file = this.getTestFile(testFileName);
    private final ExcelTestMultiBean bean = createBean();

    @Test
    void test() throws Throwable {

        //given
        StringToBeanParser<ExcelTestMultiBean> parser = new StringToBeanParser<>(ExcelTestMultiBean.class);

        this.file.delete();
        assertFalse(this.file.exists());

        //when
        parser.write(this.testFileName, this.bean);

        //then
        assertTrue(this.file.exists());

        ExcelTestMultiBean readout = parser.read(this.testFileName);

        assertNotNull(readout);

        final ExcelTestMultiBean expected = createBean();

        assertThat(expected.getSheet1()
                           .collect(Collectors.toList())).hasSameElementsAs(readout.getSheet1()
                                                                                   .collect(Collectors.toList()));

        assertThat(expected.getSheet2()
                           .collect(Collectors.toList())).hasSameElementsAs(readout.getSheet2()
                                                                                   .collect(Collectors.toList()));

    }

    public File getTestFile(String testFileName) {
        return new File(FilePipes.resourceDirectory, testFileName);
    }

    public ExcelTestMultiBean createBean() {
        List<ExcelSheetTestBean1> l1 = new LinkedList<>();
        l1.add(ExcelSheetTestBean1.builder()
                                  .string0("")
                                  .integer1(Integer.MIN_VALUE)
                                  .long2(Long.MIN_VALUE)
                                  .double3(Double.MIN_VALUE)
                                  .float4(Float.MIN_VALUE)
                                  .localdate5(LocalDate.of(1900, 1, 1))
                                  .date6(Date.valueOf(LocalDate.of(1900, 1, 1)))
                                  .list7(Arrays.asList("1", "2", "3"))
                                  .build());
        l1.add(ExcelSheetTestBean1.builder()
                                  .string0("string0")
                                  .integer1(1)
                                  .long2(2L)
                                  .double3(3.0)
                                  .float4(4.0F)
                                  .localdate5(LocalDate.of(2022, 2, 22))
                                  .date6(Date.valueOf(LocalDate.of(2022, 2, 22)))
                                  .list7(Arrays.asList("a", "b", "c", "d", "e", "f", "g", "h", "i", "j"))
                                  .build());
        l1.add(ExcelSheetTestBean1.builder()
                                  .string0("string1")
                                  .integer1(2)
                                  .long2(3L)
                                  .double3(4.0)
                                  .float4(5.0F)
                                  .localdate5(LocalDate.of(2023, 2, 22))
                                  .date6(Date.valueOf(LocalDate.of(2023, 2, 22)))
                                  .list7(Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10"))
                                  .build());
        l1.add(ExcelSheetTestBean1.builder()
                                  .string0("string2")
                                  .integer1(3)
                                  .long2(4L)
                                  .double3(5.0)
                                  .float4(6.0F)
                                  .localdate5(LocalDate.of(2024, 2, 22))
                                  .date6(Date.valueOf(LocalDate.of(2024, 2, 22)))
                                  .list7(Arrays.asList("1a", "2a", "3a", "4a", "5a", "6a", "7a", "8a", "9a", "10a"))
                                  .build());
        List<ExcelSheetTestBean2> l2 = new LinkedList<>();
        l2.add(ExcelSheetTestBean2.builder()
                                  .string0("")
                                  .integer1(Integer.MIN_VALUE)
                                  .long2(Long.MIN_VALUE)
                                  .double3(Double.MIN_VALUE)
                                  .float4(Float.MIN_VALUE)
                                  .localdate5(LocalDate.of(1900, 1, 1))
                                  .date6(Date.valueOf(LocalDate.of(1900, 1, 1)))
                                  .list7(Arrays.asList("1", "2", "3"))
                                  .build());
        l2.add(ExcelSheetTestBean2.builder()
                                  .string0("string0")
                                  .integer1(1)
                                  .long2(2L)
                                  .double3(3.0)
                                  .float4(4.0F)
                                  .localdate5(LocalDate.of(2022, 2, 22))
                                  .date6(Date.valueOf(LocalDate.of(2022, 2, 22)))
                                  .list7(Arrays.asList("a", "b", "c", "d", "e", "f", "g", "h", "i", "j"))
                                  .build());
        l2.add(ExcelSheetTestBean2.builder()
                                  .string0("string1")
                                  .integer1(2)
                                  .long2(3L)
                                  .double3(4.0)
                                  .float4(5.0F)
                                  .localdate5(LocalDate.of(2023, 2, 22))
                                  .date6(Date.valueOf(LocalDate.of(2023, 2, 22)))
                                  .list7(Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10"))
                                  .build());
        l2.add(ExcelSheetTestBean2.builder()
                                  .string0("string2")
                                  .integer1(3)
                                  .long2(4L)
                                  .double3(5.0)
                                  .float4(6.0F)
                                  .localdate5(LocalDate.of(2024, 2, 22))
                                  .date6(Date.valueOf(LocalDate.of(2024, 2, 22)))
                                  .list7(Arrays.asList("1a", "2a", "3a", "4a", "5a", "6a", "7a", "8a", "9a", "10a"))
                                  .build());
        final ExcelTestMultiBean bean = new ExcelTestMultiBean();
        bean.setSheet1(l1.stream());
        bean.setSheet2(l2.stream());
        return bean;
    }
}
