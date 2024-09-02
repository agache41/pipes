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

package io.github.agache41.ormpipes.pipes.numeric.typeBoolean;

import io.github.agache41.ormpipes.functional.StrongType;
import io.github.agache41.ormpipes.functional.ThrowingConsumer;
import io.github.agache41.ormpipes.functional.ThrowingFunction;
import io.github.agache41.ormpipes.pipe.AnnotablePipe;
import io.github.agache41.ormpipes.pipes.base.format.AbstractFormat;
import io.github.agache41.ormpipes.pipes.base.format.AbstractNullSafe;
import io.github.agache41.ormpipes.pipes.base.format.AbstractParse;
import io.github.agache41.ormpipes.pipes.typeObject.TypeObject;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 * The type Boolean pipes.
 * </pre>
 */
public class BooleanPipes {
    /**
     * <pre>
     * The type Boolean to string.
     * </pre>
     */
    public static class BooleanToString extends AbstractFormat<TypeBoolean.New, Boolean> implements AnnotablePipe<TypeBoolean.New, Boolean, String> {

        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getInputType() {
            return TypeBoolean.strongType;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void configure(TypeBoolean.New cfg) {
            super.configure(cfg);
            if (this.simple) {
                return;
            }
            final String trueValue = cfg.value().length == 0 ? null : cfg.value()[0];
            final String falseValue = cfg.falseValue().length == 0 ? null : cfg.falseValue()[0];
            final String unknownValue = cfg.nullOrUnknownIsFalse() ? falseValue : null;
            this.function = value -> (null == value) ? unknownValue : value ? trueValue : falseValue;
        }
    }

    /**
     * <pre>
     * The type Parse boolean.
     * </pre>
     */
    public static class ParseBoolean extends AbstractParse<TypeBoolean.New, Boolean> implements AnnotablePipe<TypeBoolean.New, String, Boolean> {

        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getOutputType() {
            return TypeBoolean.strongType;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void configure(TypeBoolean.New cfg) {
            super.configure(cfg);
            if (this.simple) {
                this.function = Boolean::parseBoolean;
                return;
            }
            final Boolean unknownValue = cfg.nullOrUnknownIsFalse() ? Boolean.FALSE : null;
            final Map<String, Boolean> valuesMap = new HashMap<>();
            for (String trueValue : cfg.value()) {
                valuesMap.put(trueValue, Boolean.TRUE);
            }
            for (String falseValue : cfg.falseValue()) {
                if (valuesMap.containsKey(falseValue)) {
                    throw new IllegalArgumentException(" Value \"" + falseValue + "\" is already used for true?");
                }
                valuesMap.put(falseValue, Boolean.FALSE);
            }
            this.function = string -> valuesMap.computeIfAbsent(string, k -> unknownValue);
        }
    }

    /**
     * <pre>
     * The type Value of.
     * </pre>
     */
    public static class ValueOf implements AnnotablePipe<TypeBoolean.value, Object, Boolean> {

        private Boolean value;

        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getInputType() {
            return TypeObject.strongType;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getOutputType() {
            return TypeBoolean.strongType;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void configure(TypeBoolean.value cfg) {
            this.value = cfg.value();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public ThrowingFunction<Object, Boolean> function() {
            return object -> this.value;
        }
    }

    /**
     * <pre>
     * The type Negate.
     * </pre>
     */
    public static class Negate extends AbstractNullSafe<Boolean, Boolean> implements AnnotablePipe<Annotation, Boolean, Boolean> {

        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getInputType() {
            return TypeBoolean.strongType;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getOutputType() {
            return TypeBoolean.strongType;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void configure(Annotation cfg) {
            this.function = booleanValue -> !booleanValue;
            super.configure(cfg);
        }
    }

    /**
     * <pre>
     * The type Read cell value.
     * </pre>
     */
    public static class ReadCellValue implements AnnotablePipe<TypeBoolean.cellValue, Cell, Boolean> {
        private ThrowingFunction<Cell, Boolean> function;

        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getInputType() {
            return StrongType.of(Cell.class);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getOutputType() {
            return TypeBoolean.strongType;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void configure(TypeBoolean.cellValue cfg) {
            this.function = cell -> {
                if (cfg.nullSafe() && cell.getCellType() == CellType.BLANK) {
                    return null;
                }
                return cell.getBooleanCellValue();
            };
            this.function = this.function.nullSafe(cfg.nullSafe());
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public ThrowingFunction<Cell, Boolean> function() {
            return this.function;
        }
    }


    /**
     * <pre>
     * The type Write cell value.
     * </pre>
     */
    public static class WriteCellValue implements AnnotablePipe<TypeBoolean.cellValue, Boolean, ThrowingConsumer<Cell>> {
        private ThrowingFunction<Boolean, ThrowingConsumer<Cell>> function;

        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getInputType() {
            return TypeBoolean.strongType;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void configure(TypeBoolean.cellValue cfg) {
            if (cfg.nullSafe()) {
                this.function = booleaner -> booleaner == null ? cell -> cell.setBlank() : cell -> cell.setCellValue(booleaner);
            } else {
                this.function = booleaner -> cell -> cell.setCellValue(booleaner);
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public ThrowingFunction<Boolean, ThrowingConsumer<Cell>> function() {
            return this.function;
        }
    }

}
