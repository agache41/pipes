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

package io.github.agache41.ormpipes.pipes.numeric;

import io.github.agache41.ormpipes.pipe.AnnotablePipe;
import io.github.agache41.ormpipes.pipe.registry.Registry;
import io.github.agache41.ormpipes.pipes.base.parser.StringToStreamOfBeansParser;
import io.github.agache41.ormpipes.pipes.csv.csvFile.CSVFile;
import io.github.agache41.ormpipes.pipes.numeric.typeBigDecimal.BigDecimalFormatTest;
import io.github.agache41.ormpipes.pipes.numeric.typeBigInteger.BigIntegerFormatTest;
import io.github.agache41.ormpipes.pipes.numeric.typeBoolean.BooleanFormatTest;
import io.github.agache41.ormpipes.pipes.numeric.typeDouble.DoubleFormatTest;
import io.github.agache41.ormpipes.pipes.numeric.typeFloat.FloatFormatTest;
import io.github.agache41.ormpipes.pipes.numeric.typeInteger.IntegerFormatTest;
import io.github.agache41.ormpipes.pipes.numeric.typeLong.LongFormatTest;
import io.github.agache41.ormpipes.pipes.numeric.typeShort.ShortFormatTest;
import io.github.agache41.ormpipes.pipes.temporal.typeDate.DateFormatTest;
import io.github.agache41.ormpipes.pipes.temporal.typeLocalDate.LocalDateFormatTest;
import io.github.agache41.ormpipes.pipes.temporal.typeLocalDateTime.LocalDateTimeFormatTest;
import io.github.agache41.ormpipes.pipes.temporal.typeLocalTime.LocalTimeFormatTest;
import io.github.agache41.ormpipes.pipes.temporal.typeSqlDate.SqlDateFormatTest;
import io.github.agache41.ormpipes.pipes.temporal.typeTimestamp.TimestampFormatTest;
import io.github.agache41.ormpipes.pipes.zip.zipArchive.Zip;
import io.github.agache41.ormpipes.vminfo.GenericVMInfo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static io.github.agache41.ormpipes.config.Constants.DEFAULT;

@Execution(ExecutionMode.CONCURRENT)
@EnabledIfSystemProperty(named = "pipes.test.generate", matches = "true")
public class GenerateTests extends BaseFormatTest {

    public static final Boolean[] TRUE_AND_FALSE = {Boolean.TRUE, Boolean.FALSE};
    public static final Boolean[] ONLY_TRUE = {Boolean.TRUE};
    public static final Boolean[] ONLY_FALSE = {Boolean.TRUE};
    protected static final String[] ONE_EMPTY = {""};
    protected static final String[] EMPTY = {};
    protected static final String[] numericFormats = {"", "0", "00", "00000000", //
                                                      ".0", ".00", ".00000000",  //
                                                      "0.0", "00.00", "00000000.00000000", //
                                                      "0,0", "00,00", "00000000,00000000", //
                                                      "#", "##", "########", //
                                                      ".#", ".##", ".##########", //
                                                      "#.#", "##.##", "###########.#########", //
                                                      "#,#", "##,##", "###########,#########", //
                                                      "###,###,###.##"};
    protected static final String[] numericFormatsSmall = {"", "00000000.00000000", "###########,#########", "###,###,###.##"};
    protected static final String[] numberValues =
            new String[]{null, "", "null", "   ", "abcd", "--x", "0 ", " 000", "a 02", "123h", "123L", "1-2", "1", "2", "1234", "123 456 789", "000 0001", "00000 1234", "1.2",
                         "1,2", "123,456,789.123", "123.456.789,123", "1234E10", "1234.567E5", "1234,567E8", "1234.567E8"};
    protected static final String[] numberValuesSmall = {null, "", "null", "1", "2", "1234", "123 456 789", "000 0001", "a 02", "1234.567E5"};
    protected static final String[] dateFormats =
            {"", "xyz", "yyyy.MM.dd", "dd-MM-yyyy", "yyMMdd", "yyyyy.MMMM.dd", "YYYY-'W'ww-u", "BASIC_ISO_DATE", "ISO_LOCAL_DATE", "ISO_OFFSET_DATE", "ISO_DATE"};

    protected static final String[] dateValues = {null, "", "2001.07.04", "04-07-2001", "010704", "02001.July.04", "2001-W27-3"};

    protected static final String[] dateTimeFormats =
            new String[]{"", "xyz", "yyyy.MM.dd GGG 'at' HH:mm:ss z", "yyyy.MMMM.dd GGG hh:mm a", "dd-MM-yyyy hh:mm a", "dd/MM/yyyy HH:mm",
                         "ISO_LOCAL_DATE_TIME", "ISO_OFFSET_DATE_TIME", "ISO_ZONED_DATE_TIME", "ISO_DATE_TIME", "RFC_1123_DATE_TIME"};

    protected static final String[] dateTimeValues =
            new String[]{"", "2009.06.30 AD at 08:29:36 PDT", "2009.June.30 AD 08:29 AM", "04-07-2001 10:20 PM", "04/07/2001 10:20"};

    protected static final String[] timeFormats =
            new String[]{"", "xyz", "hh:mm:ss z", "hh:mm a", "hh:mm a VV", "HH:mm", "dd/MM/yyyy hh:mm", "ISO_TIME", "ISO_OFFSET_TIME", "ISO_LOCAL_TIME"
            };

    protected static final String[] timeValues =
            new String[]{"", "08:29:36 PDT", "08:29 AM", "10:20 PM", "10:20 PM Z", "10:20"};

    protected static String[] booleanValues = {null, "", "true", "false", "null", "1234", "true and false", "1", "empty", "100"};
    protected static String[][] booleanTrueFormats = {{""}, {"null"}, {"true"}, {"true", "TRUE", "1"}};
    protected static String[][] booleanFalseFormats = {{""}, {"false"}, {"null"}, {"false", "FALSE", "0", "", "null", "empty"}};

    protected static String[] timeZones = {"", "UTC", "GMT+1", "America/Los_Angeles", "-05:00", "Pacific/Honolulu"};

    @Test
    @Execution(ExecutionMode.CONCURRENT)
    void generateBooleanFormatCsvTestFile() throws Throwable {
        this.generateBooleanCsvTestFile(BooleanFormatTest.BooleanFormatAnnotation::new, BooleanFormatTest.class, "Small", booleanTrueFormats, booleanFalseFormats, TRUE_AND_FALSE, TRUE_AND_FALSE, TRUE_AND_FALSE, TRUE_AND_FALSE, booleanValues);
    }

    @Test
    @Execution(ExecutionMode.CONCURRENT)
    void generateShortFormatCsvTestFiles() throws Throwable {
        this.generateNumericCsvTestFile(ShortFormatTest.ShortFormatAnnotation::new, ShortFormatTest.class, "", numericFormats, GenericVMInfo.LANGUAGE_TAGS, ONE_EMPTY, TRUE_AND_FALSE, TRUE_AND_FALSE, TRUE_AND_FALSE, numberValues);
        this.generateNumericCsvTestFile(() -> new ShortFormatTest.ShortFormatAnnotation(), ShortFormatTest.class, "Small", numericFormatsSmall, GenericVMInfo.LANGUAGE_TAGS_SMALL, ONE_EMPTY, TRUE_AND_FALSE, TRUE_AND_FALSE, TRUE_AND_FALSE, numberValuesSmall);
    }

    @Test
    @Execution(ExecutionMode.CONCURRENT)
    void generateIntegerFormatCsvTestFiles() throws Throwable {
        this.generateNumericCsvTestFile(IntegerFormatTest.IntegerFormatAnnotation::new, IntegerFormatTest.class, "", numericFormats, GenericVMInfo.LANGUAGE_TAGS, ONE_EMPTY, TRUE_AND_FALSE, TRUE_AND_FALSE, TRUE_AND_FALSE, numberValues);
        this.generateNumericCsvTestFile(IntegerFormatTest.IntegerFormatAnnotation::new, IntegerFormatTest.class, "Small", numericFormatsSmall, GenericVMInfo.LANGUAGE_TAGS_SMALL, ONE_EMPTY, TRUE_AND_FALSE, TRUE_AND_FALSE, TRUE_AND_FALSE, numberValuesSmall);
    }

    @Test
    @Execution(ExecutionMode.CONCURRENT)
    void generateLongFormatCsvTestFiles() throws Throwable {
        this.generateNumericCsvTestFile(LongFormatTest.LongFormatAnnotation::new, LongFormatTest.class, "", numericFormats, GenericVMInfo.LANGUAGE_TAGS, ONE_EMPTY, TRUE_AND_FALSE, TRUE_AND_FALSE, TRUE_AND_FALSE, numberValues);
        this.generateNumericCsvTestFile(() -> new LongFormatTest.LongFormatAnnotation(), LongFormatTest.class, "Small", numericFormatsSmall, GenericVMInfo.LANGUAGE_TAGS_SMALL, ONE_EMPTY, TRUE_AND_FALSE, TRUE_AND_FALSE, TRUE_AND_FALSE, numberValuesSmall);
    }


    @Test
    @Execution(ExecutionMode.CONCURRENT)
    void generateDoubleFormatCsvTestFiles() throws Throwable {
        this.generateNumericCsvTestFile(DoubleFormatTest.DoubleFormatAnnotation::new, DoubleFormatTest.class, "", numericFormats, GenericVMInfo.LANGUAGE_TAGS, ONE_EMPTY, TRUE_AND_FALSE, TRUE_AND_FALSE, TRUE_AND_FALSE, numberValues);
        this.generateNumericCsvTestFile(DoubleFormatTest.DoubleFormatAnnotation::new, DoubleFormatTest.class, "Small", numericFormatsSmall, GenericVMInfo.LANGUAGE_TAGS_SMALL, ONE_EMPTY, TRUE_AND_FALSE, TRUE_AND_FALSE, TRUE_AND_FALSE, numberValuesSmall);
    }

    @Test
    @Execution(ExecutionMode.CONCURRENT)
    void generateFloatFormatCsvTestFiles() throws Throwable {
        this.generateNumericCsvTestFile(FloatFormatTest.FloatFormatAnnotation::new, FloatFormatTest.class, "", numericFormats, GenericVMInfo.LANGUAGE_TAGS, ONE_EMPTY, TRUE_AND_FALSE, TRUE_AND_FALSE, TRUE_AND_FALSE, numberValues);
        this.generateNumericCsvTestFile(FloatFormatTest.FloatFormatAnnotation::new, FloatFormatTest.class, "Small", numericFormatsSmall, GenericVMInfo.LANGUAGE_TAGS_SMALL, ONE_EMPTY, TRUE_AND_FALSE, TRUE_AND_FALSE, TRUE_AND_FALSE, numberValuesSmall);
    }


    @Test
    @Execution(ExecutionMode.CONCURRENT)
    void generateBigDecimalFormatCsvTestFiles() throws Throwable {
        this.generateNumericCsvTestFile(BigDecimalFormatTest.BigDecimalFormatAnnotation::new, BigDecimalFormatTest.class, "", numericFormats, GenericVMInfo.LANGUAGE_TAGS, ONE_EMPTY, TRUE_AND_FALSE, TRUE_AND_FALSE, TRUE_AND_FALSE, numberValues);
        this.generateNumericCsvTestFile(BigDecimalFormatTest.BigDecimalFormatAnnotation::new, BigDecimalFormatTest.class, "Small", numericFormatsSmall, GenericVMInfo.LANGUAGE_TAGS_SMALL, ONE_EMPTY, TRUE_AND_FALSE, TRUE_AND_FALSE, TRUE_AND_FALSE, numberValuesSmall);
    }

    @Test
    @Execution(ExecutionMode.CONCURRENT)
    void generateBigIntegerFormatCsvTestFiles() throws Throwable {
        this.generateNumericCsvTestFile(BigIntegerFormatTest.BigIntegerFormatAnnotation::new, BigIntegerFormatTest.class, "Small", ONE_EMPTY, ONE_EMPTY, ONE_EMPTY, TRUE_AND_FALSE, TRUE_AND_FALSE, TRUE_AND_FALSE, numberValuesSmall);
    }

    @Test
    @Execution(ExecutionMode.CONCURRENT)
    void generateDateFormatCsvTestFiles() throws Throwable {
        this.generateNumericCsvTestFile(DateFormatTest.DateFormatAnnotation::new, DateFormatTest.class, "", dateFormats, GenericVMInfo.LANGUAGE_TAGS, timeZones, TRUE_AND_FALSE, TRUE_AND_FALSE, TRUE_AND_FALSE, dateValues);
        this.generateNumericCsvTestFile(DateFormatTest.DateFormatAnnotation::new, DateFormatTest.class, "Small", dateFormats, GenericVMInfo.LANGUAGE_TAGS_SMALL, timeZones, TRUE_AND_FALSE, TRUE_AND_FALSE, TRUE_AND_FALSE, dateValues);
    }

    @Test
    @Execution(ExecutionMode.CONCURRENT)
    void generateLocalDateFormatCsvTestFiles() throws Throwable {
        this.generateNumericCsvTestFile(LocalDateFormatTest.LocalDateFormatAnnotation::new, LocalDateFormatTest.class, "", dateFormats, GenericVMInfo.LANGUAGE_TAGS, timeZones, TRUE_AND_FALSE, TRUE_AND_FALSE, TRUE_AND_FALSE, dateValues);
        this.generateNumericCsvTestFile(LocalDateFormatTest.LocalDateFormatAnnotation::new, LocalDateFormatTest.class, "Small", dateFormats, GenericVMInfo.LANGUAGE_TAGS_SMALL, timeZones, TRUE_AND_FALSE, TRUE_AND_FALSE, TRUE_AND_FALSE, dateValues);
    }

    @Test
    @Execution(ExecutionMode.CONCURRENT)
    void generateLocalDateTimeFormatCsvTestFiles() throws Throwable {
        this.generateNumericCsvTestFile(LocalDateTimeFormatTest.LocalDateTimeFormatAnnotation::new, LocalDateTimeFormatTest.class, "", dateTimeFormats, GenericVMInfo.LANGUAGE_TAGS,
                                        timeZones,
                                        TRUE_AND_FALSE, TRUE_AND_FALSE, TRUE_AND_FALSE, dateTimeValues);
        this.generateNumericCsvTestFile(LocalDateTimeFormatTest.LocalDateTimeFormatAnnotation::new, LocalDateTimeFormatTest.class, "Small", dateTimeFormats, GenericVMInfo.LANGUAGE_TAGS_SMALL,
                                        timeZones, TRUE_AND_FALSE, TRUE_AND_FALSE, TRUE_AND_FALSE, dateTimeValues);
    }

    @Test
    @Execution(ExecutionMode.CONCURRENT)
    void generateLocalTimeFormatCsvTestFiles() throws Throwable {
        this.generateNumericCsvTestFile(LocalTimeFormatTest.LocalTimeFormatAnnotation::new, LocalTimeFormatTest.class, "", timeFormats, GenericVMInfo.LANGUAGE_TAGS, timeZones,
                                        TRUE_AND_FALSE, TRUE_AND_FALSE, TRUE_AND_FALSE, timeValues);
        this.generateNumericCsvTestFile(LocalTimeFormatTest.LocalTimeFormatAnnotation::new, LocalTimeFormatTest.class, "Small", timeFormats, GenericVMInfo.LANGUAGE_TAGS_SMALL,
                                        timeZones, TRUE_AND_FALSE, TRUE_AND_FALSE, TRUE_AND_FALSE, timeValues);
    }

    @Test
    @Execution(ExecutionMode.CONCURRENT)
    void generateTimestampFormatCsvTestFiles() throws Throwable {
        this.generateNumericCsvTestFile(TimestampFormatTest.TimestampFormatAnnotation::new, TimestampFormatTest.class, "", dateTimeFormats, GenericVMInfo.LANGUAGE_TAGS, timeZones,
                                        TRUE_AND_FALSE, TRUE_AND_FALSE, TRUE_AND_FALSE, dateTimeValues);
        this.generateNumericCsvTestFile(TimestampFormatTest.TimestampFormatAnnotation::new, TimestampFormatTest.class, "Small", dateTimeFormats, GenericVMInfo.LANGUAGE_TAGS_SMALL,
                                        timeZones, TRUE_AND_FALSE, TRUE_AND_FALSE, TRUE_AND_FALSE, dateTimeValues);
    }

    @Test
    @Execution(ExecutionMode.CONCURRENT)
    void generateSqlDateFormatCsvTestFiles() throws Throwable {
        this.generateNumericCsvTestFile(SqlDateFormatTest.SqlDateFormatAnnotation::new, SqlDateFormatTest.class, "", dateFormats, GenericVMInfo.LANGUAGE_TAGS, timeZones, TRUE_AND_FALSE,
                                        TRUE_AND_FALSE, TRUE_AND_FALSE, dateValues);
        this.generateNumericCsvTestFile(SqlDateFormatTest.SqlDateFormatAnnotation::new, SqlDateFormatTest.class, "Small", dateFormats, GenericVMInfo.LANGUAGE_TAGS_SMALL, timeZones,
                                        TRUE_AND_FALSE, TRUE_AND_FALSE, TRUE_AND_FALSE, dateValues);
    }


    ////////////////////////////////////////////////////////////////////////////////////
    // Numeric
    protected void generateNumericCsvTestFile(Supplier<BaseNumericTestConfigFor<?>> constructor,
                                              Class<?> testClass,
                                              String fileSuffix,
                                              String[] values,
                                              String[] languageTags,
                                              String[] zoneIds,
                                              Boolean[] nullSafes,
                                              Boolean[] blankSafes,
                                              Boolean[] noExceptions,
                                              String... testValues) throws Throwable {

        new StringToStreamOfBeansParser<>(BaseNumericTestConfigFor.class, fileSuffix.isEmpty() ? "zip" : DEFAULT)
                .write(this.testFileName(testClass, fileSuffix),//
                       this.generateNumericTests(constructor, fileSuffix, values, languageTags, zoneIds, nullSafes, blankSafes, noExceptions, testValues));
    }


    private Stream<BaseNumericTestConfigFor> generateNumericTests(Supplier<BaseNumericTestConfigFor<?>> constructor,
                                                                  String fileSuffix,
                                                                  String[] values,
                                                                  String[] languageTags,
                                                                  String[] zoneIds,
                                                                  Boolean[] nullSafes,
                                                                  Boolean[] blankSafes,
                                                                  Boolean[] noExceptions,
                                                                  String... testValues) {
        List<BaseNumericTestConfigFor> data = new LinkedList<>();
        for (String testValue : testValues) {
            //generate for the simple case
            for (Boolean nullSafe : nullSafes)
                for (Boolean blankSafe : blankSafes)
                    for (Boolean noException : noExceptions)
                        data.add(this.generateNumericTest(constructor, testValue, "", "", "", Boolean.TRUE, nullSafe, blankSafe, noException));
            //generate for all possible settings
            for (String value : values)
                for (String languageTag : languageTags)
                    for (String zoneId : zoneIds)
                        for (Boolean nullSafe : nullSafes)
                            for (Boolean blankSafe : blankSafes)
                                for (Boolean noException : noExceptions)
                                    data.add(this.generateNumericTest(constructor, testValue, value, languageTag, zoneId, Boolean.FALSE, nullSafe, blankSafe, noException));

        }
        return data.stream();
    }

    private BaseNumericTestConfigFor<?> generateNumericTest(Supplier<BaseNumericTestConfigFor<?>> constructor,
                                                            String testValue,
                                                            String value,
                                                            String languageTag,
                                                            String zoneId,
                                                            Boolean simple,
                                                            Boolean nullSafe,
                                                            Boolean blankSafe,
                                                            Boolean noException) {
        BaseNumericTestConfigFor configAnnotation = constructor.get();
        configAnnotation.setFormat(value);
        configAnnotation.setLanguageTag(languageTag);
        configAnnotation.setZoneId(zoneId);
        return this.generateTest(configAnnotation, testValue, simple, nullSafe, blankSafe, noException);
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    /// Boolean

    protected void generateBooleanCsvTestFile(Supplier<BooleanFormatTest.BooleanFormatAnnotation> constructor,
                                              Class<?> testClass,
                                              String fileSuffix,
                                              String[][] values,
                                              String[][] falseValues,
                                              Boolean[] nullOrUnknownIsFalses,
                                              Boolean[] nullSafes,
                                              Boolean[] blankSafes,
                                              Boolean[] noExceptions,
                                              String... testValues) throws Throwable {

        new StringToStreamOfBeansParser<>(BooleanFormatTest.BooleanFormatAnnotation.class, fileSuffix.isEmpty() ? "zip" : DEFAULT)
                                  .write(this.testFileName(testClass, fileSuffix),//
                                         this.generateBooleansTests(constructor, fileSuffix, values, falseValues, nullOrUnknownIsFalses, nullSafes, blankSafes, noExceptions, testValues));
    }

    private Stream<BooleanFormatTest.BooleanFormatAnnotation> generateBooleansTests(Supplier<BooleanFormatTest.BooleanFormatAnnotation> constructor,
                                                                                    String fileSuffix,
                                                                                    String[][] values,
                                                                                    String[][] falseValues,
                                                                                    Boolean[] nullOrUnknownIsFalses,
                                                                                    Boolean[] nullSafes,
                                                                                    Boolean[] blankSafes,
                                                                                    Boolean[] noExceptions,
                                                                                    String... testValues) {
        List<BooleanFormatTest.BooleanFormatAnnotation> data = new LinkedList<>();
        for (String testValue : testValues) {
            //generate for the simple case
            for (Boolean nullSafe : nullSafes)
                for (Boolean blankSafe : blankSafes)
                    for (Boolean noException : noExceptions)
                        data.add(this.generateBooleanTest(constructor, testValue, Boolean.TRUE, nullSafe, blankSafe, noException, EMPTY, EMPTY, false));
            //generate for all possible settings
            for (String[] value : values)
                for (String[] falseValue : falseValues)
                    for (Boolean nullOrUnknownIsFalse : nullOrUnknownIsFalses)
                        for (Boolean nullSafe : nullSafes)
                            for (Boolean blankSafe : blankSafes)
                                for (Boolean noException : noExceptions)
                                    data.add(this.generateBooleanTest(constructor, testValue, Boolean.FALSE, nullSafe, blankSafe, noException, value, falseValue, nullOrUnknownIsFalse));
        }
        return data.stream();
    }

    private BooleanFormatTest.BooleanFormatAnnotation generateBooleanTest(Supplier<BooleanFormatTest.BooleanFormatAnnotation> constructor,
                                                                          String testValue,
                                                                          Boolean simple,
                                                                          Boolean nullSafe,
                                                                          Boolean blankSafe,
                                                                          Boolean noException,
                                                                          String[] values,
                                                                          String[] falseValues,
                                                                          Boolean nullOrUnknownIsFalse) {
        BooleanFormatTest.BooleanFormatAnnotation configAnnotation = constructor.get();
        configAnnotation.setValues(values);
        configAnnotation.setFalseValues(falseValues);
        configAnnotation.setNullOrUnknownIsFalse(nullOrUnknownIsFalse);
        return this.generateTest(configAnnotation, testValue, simple, nullSafe, blankSafe, noException);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /// Generic test generation
    private <T extends BaseTestConfigFor<T>> T generateTest(T configAnnotation,
                                                            String testValue,
                                                            Boolean simple,
                                                            Boolean nullSafe,
                                                            Boolean blankSafe,
                                                            Boolean noException) {
        configAnnotation.setInput(testValue);
        configAnnotation.setSimple(simple);
        configAnnotation.setNullSafe(nullSafe);
        configAnnotation.setBlankSafe(blankSafe);
        configAnnotation.setNoException(noException);
        try {
            AnnotablePipe<?, String, Object> read = Registry.createAndConfigureFromMethod(AnnotablePipe.class, configAnnotation, "read", this);
            AnnotablePipe<?, Object, String> write = Registry.createAndConfigureFromMethod(AnnotablePipe.class, configAnnotation, "write", this);

            configAnnotation.setOutput(write.function()
                                            .apply(read.function()
                                                       .apply(testValue)));
            configAnnotation.setException("");
            configAnnotation.setExceptionMessage("");
        } catch (Throwable e) {
            configAnnotation.setOutput("");
            configAnnotation.setException(e.getClass()
                                           .getCanonicalName());
            configAnnotation.setExceptionMessage(e.getMessage());
        }
        return configAnnotation;
    }

    private String testFileName(Class<?> testClass,
                                String suffix) {

        String testFileName = "../../src/test/java/" + testClass.getCanonicalName()
                                                                .replace('.', '/') + suffix + (suffix.isEmpty() ? Zip.Archive.extension : CSVFile.extension);
        logger.infof("Testing %s", testFileName);
        return testFileName;
    }
}
