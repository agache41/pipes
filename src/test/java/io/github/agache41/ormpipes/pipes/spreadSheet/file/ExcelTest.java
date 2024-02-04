package io.github.agache41.ormpipes.pipes.spreadSheet.file;

import io.github.agache41.ormpipes.pipes.baseTest.ReadWriteTest;
import io.github.agache41.ormpipes.pipes.spreadSheet.SpreadSheet;
import io.github.agache41.ormpipes.pipes.spreadSheet.SpreadSheet.file.FileStreamParser;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import java.io.File;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


class ExcelTest extends ReadWriteTest<ExcelTemplateBean> {
    private final FileStreamParser<ExcelFileAppendBean> templateParser = FileStreamParser.ofClass(ExcelFileAppendBean.class, "append");

    public ExcelTest() {
        super.init(ExcelTemplateBean.class, SpreadSheet.xls.extension);

    }

    @Test
    @Order(2)
    @Execution(ExecutionMode.SAME_THREAD)
    void testWriteEmptyReadWithTemplate() throws Throwable {
        String suffixFileName = this.getTestFileName("");
        File suffixFile = this.getTestFile(suffixFileName);
        Assertions.assertTrue(suffixFile.exists());
        List<ExcelFileAppendBean> emptyList = Collections.emptyList();
        this.templateParser.write(suffixFile, emptyList.stream());

        List<ExcelFileAppendBean> readValues = this.templateParser.read(suffixFile)
                                                                  .collect(Collectors.toList());
        Assertions.assertNotNull(readValues);
        Assertions.assertTrue(readValues.isEmpty());

        this.assertEquals(this.values, this.readFile(""));
    }

    @Test
    @Order(3)
    @Execution(ExecutionMode.SAME_THREAD)
    void testWriteReadWithTemplate() throws Throwable {
        String suffixFileName = this.getTestFileName("");
        File suffixFile = this.getTestFile(suffixFileName);
        Assertions.assertTrue(suffixFile.exists());
        ExcelFileAppendBean addedBean = new ExcelFileAppendBean();
        addedBean.setString0("added");
        addedBean.setInteger1(123456789);
        addedBean.setLong2(2000L);
        addedBean.setDate6(Date.from(LocalDate.of(2023, 2, 3)
                                              .atStartOfDay()
                                              .toInstant(ZoneOffset.UTC)));

        this.templateParser.write(suffixFile, Stream.of(addedBean));

        ExcelTemplateBean addedTBean = new ExcelTemplateBean();
        addedTBean.setString0("added");
        addedTBean.setInteger1(123456789);
        addedTBean.setLong2(2000L);
        addedTBean.setDate6(Date.from(LocalDate.of(2023, 2, 3)
                                               .atStartOfDay()
                                               .toInstant(ZoneOffset.UTC)));
        this.values.add(addedTBean);

        List<ExcelFileAppendBean> readValues = this.templateParser.read(suffixFile)
                                                                  .collect(Collectors.toList());
        Assertions.assertNotNull(readValues);
        Assertions.assertEquals(1, readValues.size());

        Assertions.assertTrue(CollectionUtils.isEqualCollection(this.values, this.readFile("")));
    }
}