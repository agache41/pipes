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

package io.github.agache41.ormpipes.pipes.numeric.typeShort;

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
import io.github.agache41.ormpipes.pipes.numeric.typeInteger.TypeInteger;
import io.github.agache41.ormpipes.pipes.numeric.typeLong.TypeLong;
import io.github.agache41.ormpipes.pipes.typeObject.TypeObject;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;

import java.lang.annotation.Annotation;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.NumberFormat;

/**
 * <pre>
 * The type Short pipes.
 * </pre>
 */
public class ShortPipes {
    /**
     * <pre>
     * The type Short to string.
     * </pre>
     */
    public static class ShortToString extends AbstractFormat<TypeShort.New, Short> implements AnnotablePipe<TypeShort.New, Short, String> {

        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getInputType() {
            return TypeShort.strongType;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void configure(TypeShort.New cfg) {
            super.configure(cfg);
            if (this.simple) {
                return;
            }
            this.function = this.getIntegerNumberFormat(cfg.format(), cfg.languageTag())::format;
        }
    }

    /**
     * <pre>
     * The type Parse short.
     * </pre>
     */
    public static class ParseShort extends AbstractParse<TypeShort.New, Short> implements AnnotablePipe<TypeShort.New, String, Short> {

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
        public void configure(TypeShort.New cfg) {
            super.configure(cfg);
            if (this.simple) {
                this.function = Short::parseShort;
            } else {
                final NumberFormat integerInstance = this.getIntegerNumberFormat(cfg.format(),
                                                                                 cfg.languageTag());
                this.function = string -> integerInstance.parse(string)
                                                         .shortValue();
            }
        }
    }

    /**
     * <pre>
     * The type Value.
     * </pre>
     */
    public static class Value implements AnnotablePipe<TypeShort.value, Object, Short> {

        private Short value;

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
            return TypeShort.strongType;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void configure(TypeShort.value cfg) {
            this.value = cfg.value();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public ThrowingFunction<Object, Short> function() {
            return object -> this.value;
        }
    }

    /**
     * <pre>
     * The type Integer value.
     * </pre>
     */
    public static class IntegerValue extends AbstractNullSafe<Short, Integer> implements AnnotablePipe<Annotation, Short, Integer> {

        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getInputType() {
            return TypeShort.strongType;
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
        public void configure(Annotation cfg) {
            this.function = Short::intValue;
            super.configure(cfg);
        }
    }

    /**
     * <pre>
     * The type Unsigned integer value.
     * </pre>
     */
    public static class UnsignedIntegerValue extends AbstractNullSafe<Short, Integer> implements AnnotablePipe<Annotation, Short, Integer> {

        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getInputType() {
            return TypeShort.strongType;
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
        public void configure(Annotation cfg) {
            this.function = Short::toUnsignedInt;
            super.configure(cfg);
        }
    }

    /**
     * <pre>
     * The type Long value.
     * </pre>
     */
    public static class LongValue extends AbstractNullSafe<Short, Long> implements AnnotablePipe<Annotation, Short, Long> {

        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getInputType() {
            return TypeShort.strongType;
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
            this.function = Short::longValue;
            super.configure(cfg);
        }
    }

    /**
     * <pre>
     * The type Unsigned long value.
     * </pre>
     */
    public static class UnsignedLongValue extends AbstractNullSafe<Short, Long> implements AnnotablePipe<Annotation, Short, Long> {

        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getInputType() {
            return TypeShort.strongType;
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
            this.function = Short::toUnsignedLong;
            super.configure(cfg);
        }
    }

    /**
     * <pre>
     * The type Double value.
     * </pre>
     */
    public static class DoubleValue extends AbstractNullSafe<Short, Double> implements AnnotablePipe<Annotation, Short, Double> {

        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getInputType() {
            return TypeShort.strongType;
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
            this.function = Short::doubleValue;
            super.configure(cfg);
        }
    }

    /**
     * <pre>
     * The type Float value.
     * </pre>
     */
    public static class FloatValue extends AbstractNullSafe<Short, Float> implements AnnotablePipe<Annotation, Short, Float> {

        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getInputType() {
            return TypeShort.strongType;
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
            this.function = Short::floatValue;
            super.configure(cfg);
        }
    }

    /**
     * <pre>
     * The type Big integer value.
     * </pre>
     */
    public static class BigIntegerValue extends AbstractNullSafe<Short, BigInteger> implements AnnotablePipe<Annotation, Short, BigInteger> {

        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getInputType() {
            return TypeShort.strongType;
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
            this.function = shortValue -> BigInteger.valueOf(shortValue.longValue());
            super.configure(cfg);
        }
    }

    /**
     * <pre>
     * The type Big decimal value.
     * </pre>
     */
    public static class BigDecimalValue extends AbstractNullSafe<Short, BigDecimal> implements AnnotablePipe<Annotation, Short, BigDecimal> {

        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getInputType() {
            return TypeShort.strongType;
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
            this.function = shortValue -> BigDecimal.valueOf(shortValue.longValue());
            super.configure(cfg);
        }
    }

    /**
     * <pre>
     * The type Byte value.
     * </pre>
     */
    public static class ByteValue extends AbstractNullSafe<Short, Byte> implements AnnotablePipe<Annotation, Short, Byte> {

        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getInputType() {
            return TypeShort.strongType;
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
            this.function = Short::byteValue;
            super.configure(cfg);
        }
    }

    /**
     * <pre>
     * The type Read cell value.
     * </pre>
     */
    public static class ReadCellValue implements AnnotablePipe<TypeShort.cellValue, Cell, Short> {
        private ThrowingFunction<Cell, Short> function;

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
            return TypeShort.strongType;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void configure(TypeShort.cellValue cfg) {
            this.function = cell -> {
                if (cfg.nullSafe() && cell.getCellType() == CellType.BLANK) {
                    return null;
                }
                return (short) cell.getNumericCellValue();
            };
            this.function = this.function.nullSafe(cfg.nullSafe());
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public ThrowingFunction<Cell, Short> function() {
            return this.function;
        }
    }


    /**
     * <pre>
     * The type Write cell value.
     * </pre>
     */
    public static class WriteCellValue implements AnnotablePipe<TypeShort.cellValue, Short, ThrowingConsumer<Cell>> {
        private ThrowingFunction<Short, ThrowingConsumer<Cell>> function;

        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getInputType() {
            return TypeShort.strongType;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void configure(TypeShort.cellValue cfg) {
            if (cfg.nullSafe()) {
                this.function = shorter -> shorter == null ? cell -> cell.setBlank() : cell -> cell.setCellValue(shorter);
            } else {
                this.function = shorter -> cell -> cell.setCellValue(shorter);
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public ThrowingFunction<Short, ThrowingConsumer<Cell>> function() {
            return this.function;
        }
    }
}
