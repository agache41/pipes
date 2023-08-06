package com.orm.pipes.numeric.typeBoolean;

import com.orm.annotations.Annotations;
import com.orm.pipe.AnnotablePipe;
import com.orm.pipes.csv.csvField.CSVAccessor;
import com.orm.pipes.numeric.BaseFormatTest;
import com.orm.pipes.numeric.BaseTestConfigFor;
import com.orm.pipes.typeString.TypeString;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.AggregateWith;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.aggregator.ArgumentsAggregationException;
import org.junit.jupiter.params.provider.CsvFileSource;

@Execution(ExecutionMode.CONCURRENT)
public class BooleanFormatTest extends BaseFormatTest {

    @Execution(ExecutionMode.CONCURRENT)
    @ParameterizedTest(name = "\"{0}\" => \"{1}{2}\" " + "<[simple={3},nullSafe={4},blankSafe={5},noException={6}]>" + "<[values=\"{7}\",falseValues=\"{8}\",nullOrUnknownIsFalse={9}]>")
    @CsvFileSource(delimiter = ';', resources = "/com/orm/pipes/numeric/typeBoolean/BooleanFormatTestSmall.csv", numLinesToSkip = 1, nullValues = {
            BaseTestConfigFor.NULL_KEY})
    void csvTest(@AggregateWith(BooleanFormatAnnotation.class) BooleanFormatAnnotation baseConfigAnnotation) {
        this.doTest(baseConfigAnnotation);
    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class BooleanFormatAnnotation extends BaseTestConfigFor<BooleanFormatAnnotation> implements TypeBoolean.New {
        @TypeString.quoted
        @TypeString.Array(separator = ",")
        @CSVAccessor(name = "values", position = 8)
        private String[] values;
        @TypeString.quoted
        @TypeString.Array(separator = ",")
        @CSVAccessor(name = "falseValues", position = 9)
        private String[] falseValues;
        @TypeBoolean.New(nullOrUnknownIsFalse = true)
        @CSVAccessor(name = "nullOrUnknownIsFalse", position = 10)
        private Boolean nullOrUnknownIsFalse;

        @Override
        public String[] value() {
            return this.values;
        }

        @Override
        public String[] falseValue() {
            return this.falseValues;
        }

        @Override
        public boolean nullOrUnknownIsFalse() {
            return this.nullOrUnknownIsFalse;
        }

        public String[] splitNullable(String input) {
            if ("".equals(input)) return new String[]{""};
            return input.split(",");
        }

        public Class<? extends AnnotablePipe<TypeBoolean.New, String, Boolean>> read() {
            return BooleanPipes.ParseBoolean.class;
        }

        public Class<? extends AnnotablePipe<TypeBoolean.New, Boolean, String>> write() {
            return BooleanPipes.BooleanToString.class;
        }

        public String[] enabledOn() {
            return new String[]{"read", "write"};
        }

        public String view() {
            return Annotations.DEFAULT;
        }

        public Class<TypeBoolean.New> annotationType() {
            return TypeBoolean.New.class;
        }

        @Override
        public Object aggregateArguments(ArgumentsAccessor argumentsAccessor, ParameterContext parameterContext) throws ArgumentsAggregationException {
            BooleanFormatAnnotation result = (BooleanFormatAnnotation) super.aggregateArguments(argumentsAccessor, parameterContext);
            result.setValues(this.splitNullable(argumentsAccessor.getString(8)));
            result.setFalseValues(this.splitNullable(argumentsAccessor.getString(9)));
            result.setNullOrUnknownIsFalse(argumentsAccessor.getBoolean(10));
            return result;
        }

        @Override
        public BooleanFormatAnnotation get() {
            return new BooleanFormatAnnotation();
        }
    }
}
