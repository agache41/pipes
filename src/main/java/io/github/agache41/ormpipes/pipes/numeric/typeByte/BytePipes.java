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

package io.github.agache41.ormpipes.pipes.numeric.typeByte;

import io.github.agache41.ormpipes.functional.StrongType;
import io.github.agache41.ormpipes.functional.ThrowingConsumer;
import io.github.agache41.ormpipes.functional.ThrowingFunction;
import io.github.agache41.ormpipes.pipe.AnnotablePipe;
import io.github.agache41.ormpipes.pipes.base.format.AbstractNullSafe;
import io.github.agache41.ormpipes.pipes.numeric.typeBigDecimal.TypeBigDecimal;
import io.github.agache41.ormpipes.pipes.numeric.typeBigInteger.TypeBigInteger;
import io.github.agache41.ormpipes.pipes.numeric.typeDouble.TypeDouble;
import io.github.agache41.ormpipes.pipes.numeric.typeFloat.TypeFloat;
import io.github.agache41.ormpipes.pipes.numeric.typeInteger.TypeInteger;
import io.github.agache41.ormpipes.pipes.numeric.typeLong.TypeLong;
import io.github.agache41.ormpipes.pipes.numeric.typeShort.TypeShort;
import io.github.agache41.ormpipes.pipes.typeObject.TypeObject;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;

import java.lang.annotation.Annotation;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * <pre>
 * The type Byte pipes.
 * </pre>
 */
public class BytePipes {

    /**
     * <pre>
     * The type Value.
     * </pre>
     */
    public static class Value implements AnnotablePipe<TypeByte.value, Object, Byte> {

        private Byte value;

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
            return TypeByte.strongType;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void configure(TypeByte.value cfg) {
            this.value = cfg.value();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public ThrowingFunction<Object, Byte> function() {
            return object -> this.value;
        }
    }

    /**
     * <pre>
     * The type Short value.
     * </pre>
     */
    public static class ShortValue extends AbstractNullSafe<Byte, Short> implements AnnotablePipe<Annotation, Byte, Short> {

        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getInputType() {
            return TypeByte.strongType;
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
            this.function = Byte::shortValue;
            super.configure(cfg);
        }
    }

    /**
     * <pre>
     * The type Integer value.
     * </pre>
     */
    public static class IntegerValue extends AbstractNullSafe<Byte, Integer> implements AnnotablePipe<Annotation, Byte, Integer> {

        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getInputType() {
            return TypeByte.strongType;
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
            this.function = Byte::intValue;
            super.configure(cfg);
        }
    }

    /**
     * <pre>
     * The type Long value.
     * </pre>
     */
    public static class LongValue extends AbstractNullSafe<Byte, Long> implements AnnotablePipe<Annotation, Byte, Long> {

        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getInputType() {
            return TypeByte.strongType;
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
            this.function = Byte::longValue;
            super.configure(cfg);
        }
    }

    /**
     * <pre>
     * The type Double value.
     * </pre>
     */
    public static class DoubleValue extends AbstractNullSafe<Byte, Double> implements AnnotablePipe<Annotation, Byte, Double> {

        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getInputType() {
            return TypeByte.strongType;
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
            this.function = Byte::doubleValue;
            super.configure(cfg);
        }
    }

    /**
     * <pre>
     * The type Float value.
     * </pre>
     */
    public static class FloatValue extends AbstractNullSafe<Byte, Float> implements AnnotablePipe<Annotation, Byte, Float> {

        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getInputType() {
            return TypeByte.strongType;
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
            this.function = Byte::floatValue;
            super.configure(cfg);
        }
    }

    /**
     * <pre>
     * The type Big integer value.
     * </pre>
     */
    public static class BigIntegerValue extends AbstractNullSafe<Byte, BigInteger> implements AnnotablePipe<Annotation, Byte, BigInteger> {

        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getInputType() {
            return TypeByte.strongType;
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
    public static class BigDecimalValue extends AbstractNullSafe<Byte, BigDecimal> implements AnnotablePipe<Annotation, Byte, BigDecimal> {

        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getInputType() {
            return TypeByte.strongType;
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
     * The type Read cell value.
     * </pre>
     */
    public static class ReadCellValue implements AnnotablePipe<TypeByte.cellValue, Cell, Byte> {
        private ThrowingFunction<Cell, Byte> function;

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
            return TypeByte.strongType;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void configure(TypeByte.cellValue cfg) {
            this.function = cell -> {
                if (cfg.nullSafe() && cell.getCellType() == CellType.BLANK) {
                    return null;
                }
                return (byte) cell.getNumericCellValue();
            };
            this.function = this.function.nullSafe(cfg.nullSafe());
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public ThrowingFunction<Cell, Byte> function() {
            return this.function;
        }
    }


    /**
     * <pre>
     * The type Write cell value.
     * </pre>
     */
    public static class WriteCellValue implements AnnotablePipe<TypeByte.cellValue, Byte, ThrowingConsumer<Cell>> {
        private ThrowingFunction<Byte, ThrowingConsumer<Cell>> function;

        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getInputType() {
            return TypeByte.strongType;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void configure(TypeByte.cellValue cfg) {
            if (cfg.nullSafe()) {
                this.function = byter -> byter == null ? cell -> cell.setBlank() : cell -> cell.setCellValue(byter);
            } else {
                this.function = byter -> cell -> cell.setCellValue(byter);
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public ThrowingFunction<Byte, ThrowingConsumer<Cell>> function() {
            return this.function;
        }
    }

}
