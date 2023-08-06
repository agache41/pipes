package com.orm.pipes.numeric;

import com.orm.pipes.csv.csvField.CSVAccessor;
import com.orm.pipes.typeString.TypeString;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.aggregator.ArgumentsAggregationException;

@Data
@EqualsAndHashCode(callSuper = true)
public abstract class BaseNumericTestConfigFor<T extends BaseNumericTestConfigFor> extends BaseTestConfigFor<T> {
    @TypeString.quoted
    @TypeString.nullable(NULL_KEY)
    @CSVAccessor(name = "format", position = 8)
    private String format;
    @TypeString.quoted
    @TypeString.nullable(NULL_KEY)
    @CSVAccessor(name = "languageTag", position = 9)
    private String languageTag;
    @TypeString.quoted
    @TypeString.nullable(NULL_KEY)
    @CSVAccessor(name = "zoneId", position = 10)
    private String zoneId;

    public String format() {
        return this.format;
    }

    public String languageTag() {
        return this.languageTag;
    }

    public String zoneId() {
        return this.zoneId;
    }

    @Override
    public Object aggregateArguments(ArgumentsAccessor argumentsAccessor, ParameterContext parameterContext) throws ArgumentsAggregationException {
        BaseNumericTestConfigFor baseNumericTestConfigFor = (BaseNumericTestConfigFor) super.aggregateArguments(argumentsAccessor, parameterContext);
        baseNumericTestConfigFor.setFormat(argumentsAccessor.getString(8));
        baseNumericTestConfigFor.setLanguageTag(argumentsAccessor.getString(9));
        baseNumericTestConfigFor.setZoneId(argumentsAccessor.getString(10));
        return baseNumericTestConfigFor;
    }
}
