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

package examples.excel.fileToBean;

import examples.csv.fileToBean.CSVFileBean;import io.github.agache41.ormpipes.pipes.base.parser.FileToStreamOfBeansParser;import io.github.agache41.ormpipes.pipes.base.parser.StringToStreamOfBeansParser;
import io.github.agache41.ormpipes.pipes.typeFile.FilePipes;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ExcelFileTest {
    private final String testFileName = "excelFile.xlsx";
    private final File file = this.getTestFile(testFileName);
    private final List<ExcelFileBean> beans = createBeans();

    @Test
    void fileToStreamOfBeansTest() throws Throwable {

        //given
        FileToStreamOfBeansParser<ExcelFileBean> parser = new FileToStreamOfBeansParser<>(ExcelFileBean.class);

        this.file.delete();
        assertFalse(this.file.exists());

        //when
        parser.write(this.file, this.beans.stream());

        //then
        assertTrue(this.file.exists());

        LinkedList<ExcelFileBean> readout = parser.read(this.file)
                                                .collect(Collectors.toCollection(LinkedList::new));
        assertThat(this.beans).hasSameElementsAs(readout);
    }

    public File getTestFile(String testFileName) {
        return new File(FilePipes.resourceDirectory, testFileName);
    }

    public List<ExcelFileBean> createBeans() {
        final ExcelFileBean nuller = new ExcelFileBean();
        final ExcelFileBean minValue = new ExcelFileBean();
        minValue.setString0("");
        minValue.setInteger1(Integer.MIN_VALUE);
        minValue.setLong2(Long.MIN_VALUE);
        minValue.setDouble3(Double.MIN_VALUE);
        minValue.setFloat4(Float.MIN_VALUE);
        minValue.setLocaldate5(LocalDate.of(1900, 1, 1));
        minValue.setDate6(Date.valueOf(LocalDate.of(1900, 1, 1)));
        minValue.setList7(Arrays.asList("1", "2", "3"));

        final ExcelFileBean value = new ExcelFileBean();
        value.setString0("string0");
        value.setInteger1(1);
        value.setLong2(2L);
        value.setDouble3(3.0);
        value.setFloat4(4.0F);
        value.setLocaldate5(LocalDate.of(2022, 2, 22));
        value.setDate6(Date.valueOf(LocalDate.of(2022, 2, 22)));
        value.setList7(Arrays.asList("a", "b", "c", "d", "e", "f", "g", "h", "i", "j"));

        return new ArrayList<>(Arrays.asList(nuller, minValue, value));
    }
}
