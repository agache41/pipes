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

package io.github.agache41.ormpipes.pipes.numeric.typeInteger;

import io.github.agache41.ormpipes.functional.StrongType;
import io.github.agache41.ormpipes.functional.ThrowingConsumer;
import io.github.agache41.ormpipes.functional.ThrowingFunction;
import io.github.agache41.ormpipes.pipe.AnnotablePipe;
import io.github.agache41.ormpipes.pipes.base.format.AbstractFormat;
import io.github.agache41.ormpipes.pipes.base.format.AbstractNullSafe;
import io.github.agache41.ormpipes.pipes.base.format.AbstractParse;
import io.github.agache41.ormpipes.pipes.numeric.typeBigDecimal.TypeBigDecimal;
import io.github.agache41.ormpipes.pipes.numeric.typeBigInteger.TypeBigInteger;
import io.github.agache41.ormpipes.pipes.numeric.typeByte.TypeByte;
import io.github.agache41.ormpipes.pipes.numeric.typeDouble.TypeDouble;
import io.github.agache41.ormpipes.pipes.numeric.typeFloat.TypeFloat;
import io.github.agache41.ormpipes.pipes.numeric.typeLong.TypeLong;
import io.github.agache41.ormpipes.pipes.numeric.typeShort.TypeShort;
import io.github.agache41.ormpipes.pipes.typeObject.TypeObject;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;

import java.lang.annotation.Annotation;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.NumberFormat;

/**
 * <pre>
 * The type Integer pipes.
 * </pre>
 */
public class IntegerPipes {
    /**
     * <pre>
     * The type Integer to string.
     * </pre>
     */
    public static class IntegerToString extends AbstractFormat<TypeInteger.New, Integer> implements AnnotablePipe<TypeInteger.New, Integer, String> {

        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getInputType() {
            return TypeInteger.strongType;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void configure(TypeInteger.New cfg) {
            super.configure(cfg);
            if (this.simple) {
                return;
            }
            this.function = this.getIntegerNumberFormat(cfg.format(), cfg.languageTag())::format;
        }
    }

    /**
     * <pre>
     * The type Parse integer.
     * </pre>
     */
    public static class ParseInteger extends AbstractParse<TypeInteger.New, Integer> implements AnnotablePipe<TypeInteger.New, String, Integer> {

        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getOutputType() {
            return TypeInteger.strongType;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void configure(TypeInteger.New cfg) {
            super.configure(cfg);
            if (this.simple) {
                this.function = Integer::parseInt;
                return;
            }
            final NumberFormat integerInstance = this.getIntegerNumberFormat(cfg.format(),
                                                                             cfg.languageTag());
            this.function = string -> integerInstance.parse(string)
                                                     .intValue();
        }
    }

    /**
     * <pre>
     * The type Value.
     * </pre>
     */
    public static class Value implements AnnotablePipe<TypeInteger.value, Object, Integer> {

        private Integer value;

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
            return TypeInteger.strongType;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void configure(TypeInteger.value cfg) {
            this.value = cfg.value();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public ThrowingFunction<Object, Integer> function() {
            return object -> this.value;
        }
    }

    /**
     * <pre>
     * The type Short value.
     * </pre>
     */
    public static class ShortValue extends AbstractNullSafe<Integer, Short> implements AnnotablePipe<Annotation, Integer, Short> {

        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getInputType() {
            return TypeInteger.strongType;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getOutputType() {
            return TypeShort.strongType;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void configure(Annotation cfg) {
            this.function = Integer::shortValue;
            super.configure(cfg);
        }
    }

    /**
     * <pre>
     * The type Long value.
     * </pre>
     */
    public static class LongValue extends AbstractNullSafe<Integer, Long> implements AnnotablePipe<Annotation, Integer, Long> {

        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getInputType() {
            return TypeInteger.strongType;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getOutputType() {
            return TypeLong.strongType;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void configure(Annotation cfg) {
            this.function = Integer::longValue;
            super.configure(cfg);
        }
    }

    /**
     * <pre>
     * The type Double value.
     * </pre>
     */
    public static class DoubleValue extends AbstractNullSafe<Integer, Double> implements AnnotablePipe<Annotation, Integer, Double> {

        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getInputType() {
            return TypeInteger.strongType;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getOutputType() {
            return TypeDouble.strongType;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void configure(Annotation cfg) {
            this.function = Integer::doubleValue;
            super.configure(cfg);
        }
    }

    /**
     * <pre>
     * The type Float value.
     * </pre>
     */
    public static class FloatValue extends AbstractNullSafe<Integer, Float> implements AnnotablePipe<Annotation, Integer, Float> {

        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getInputType() {
            return TypeInteger.strongType;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getOutputType() {
            return TypeFloat.strongType;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void configure(Annotation cfg) {
            this.function = Integer::floatValue;
            super.configure(cfg);
        }
    }

    /**
     * <pre>
     * The type Big integer value.
     * </pre>
     */
    public static class BigIntegerValue extends AbstractNullSafe<Integer, BigInteger> implements AnnotablePipe<Annotation, Integer, BigInteger> {

        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getInputType() {
            return TypeInteger.strongType;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getOutputType() {
            return TypeBigInteger.strongType;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void configure(Annotation cfg) {
            this.function = integerValue -> BigInteger.valueOf(integerValue.longValue());
            super.configure(cfg);
        }
    }

    /**
     * <pre>
     * The type Big decimal value.
     * </pre>
     */
    public static class BigDecimalValue extends AbstractNullSafe<Integer, BigDecimal> implements AnnotablePipe<Annotation, Integer, BigDecimal> {

        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getInputType() {
            return TypeInteger.strongType;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getOutputType() {
            return TypeBigDecimal.strongType;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void configure(Annotation cfg) {
            this.function = integerValue -> BigDecimal.valueOf(integerValue.longValue());
            super.configure(cfg);
        }
    }

    /**
     * <pre>
     * The type Byte value.
     * </pre>
     */
    public static class ByteValue extends AbstractNullSafe<Integer, Byte> implements AnnotablePipe<Annotation, Integer, Byte> {

        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getInputType() {
            return TypeInteger.strongType;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getOutputType() {
            return TypeByte.strongType;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void configure(Annotation cfg) {
            this.function = Integer::byteValue;
            super.configure(cfg);
        }
    }

    /**
     * <pre>
     * The type Read cell value.
     * </pre>
     */
    public static class ReadCellValue implements AnnotablePipe<TypeInteger.cellValue, Cell, Integer> {
        private ThrowingFunction<Cell, Integer> function;

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
            return TypeInteger.strongType;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void configure(TypeInteger.cellValue cfg) {
            this.function = cell -> {
                if (cfg.nullSafe() && cell.getCellType() == CellType.BLANK) {
                    return null;
                }
                return (int) cell.getNumericCellValue();
            };
            this.function = this.function.nullSafe(cfg.nullSafe());
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public ThrowingFunction<Cell, Integer> function() {
            return this.function;
        }
    }


    /**
     * <pre>
     * The type Write cell value.
     * </pre>
     */
    public static class WriteCellValue implements AnnotablePipe<TypeInteger.cellValue, Integer, ThrowingConsumer<Cell>> {
        private ThrowingFunction<Integer, ThrowingConsumer<Cell>> function;

        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getInputType() {
            return TypeInteger.strongType;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void configure(TypeInteger.cellValue cfg) {
            if (cfg.nullSafe()) {
                this.function = integer -> integer == null ? cell -> cell.setBlank() : cell -> cell.setCellValue(integer);
            } else {
                this.function = integer -> cell -> cell.setCellValue(integer);
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public ThrowingFunction<Integer, ThrowingConsumer<Cell>> function() {
            return this.function;
        }
    }
}
