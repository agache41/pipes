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

package examples.csv.iostreamToBeans;

import io.github.agache41.ormpipes.pipes.base.parser.StringToStreamOfBeansParser;
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

public class CSVTest {
    private final String testFileName = "csvFile.csv";
    private final File file = this.getTestFile(testFileName);
    private final List<CSVBean> beans = createBeans();

    @Test
    void stringToStreamOfBeansTest() throws Throwable {

        //given
        StringToStreamOfBeansParser<CSVBean> parser = new StringToStreamOfBeansParser<>(CSVBean.class);

        this.file.delete();
        assertFalse(this.file.exists());

        //when
        parser.write(this.testFileName, this.beans.stream());

        //then
        assertTrue(this.file.exists());

        LinkedList<CSVBean> readout = parser.read(this.testFileName)
                                            .collect(Collectors.toCollection(LinkedList::new));
        assertThat(this.beans).hasSameElementsAs(readout);
    }

    public File getTestFile(String testFileName) {
        return new File(FilePipes.resourceDirectory, testFileName);
    }

    public List<CSVBean> createBeans() {
        final CSVBean nuller = new CSVBean();
        final CSVBean minValue = new CSVBean();
        minValue.setString0("");
        minValue.setInteger1(Integer.MIN_VALUE);
        minValue.setLong2(Long.MIN_VALUE);
        minValue.setDouble3(Double.MIN_VALUE);
        minValue.setFloat4(Float.MIN_VALUE);
        minValue.setLocaldate5(LocalDate.of(1900, 1, 1));
        minValue.setDate6(Date.valueOf(LocalDate.of(1900, 1, 1)));
        minValue.setList7(Arrays.asList("1", "2", "3"));

        final CSVBean value = new CSVBean();
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
