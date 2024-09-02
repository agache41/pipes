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

import io.github.agache41.ormpipes.pipes.csv.csvField.CSVAccessor;
import io.github.agache41.ormpipes.pipes.typeString.TypeString;
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

    /**
* {@inheritDoc}
*/
@Override
    public Object aggregateArguments(ArgumentsAccessor argumentsAccessor, ParameterContext parameterContext) throws ArgumentsAggregationException {
        BaseNumericTestConfigFor baseNumericTestConfigFor = (BaseNumericTestConfigFor) super.aggregateArguments(argumentsAccessor, parameterContext);
        baseNumericTestConfigFor.setFormat(argumentsAccessor.getString(8));
        baseNumericTestConfigFor.setLanguageTag(argumentsAccessor.getString(9));
        baseNumericTestConfigFor.setZoneId(argumentsAccessor.getString(10));
        return baseNumericTestConfigFor;
    }
}
