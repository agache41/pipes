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

package io.github.agache41.ormpipes.pipes.numeric.typeFloat;

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
import io.github.agache41.ormpipes.pipes.numeric.typeInteger.TypeInteger;
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
 * The type Float pipes.
 * </pre>
 */
public class FloatPipes {
    /**
     * <pre>
     * The type Float to string.
     * </pre>
     */
    public static class FloatToString extends AbstractFormat<TypeFloat.New, Float> implements AnnotablePipe<TypeFloat.New, Float, String> {

        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getInputType() {
            return TypeFloat.strongType;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void configure(TypeFloat.New cfg) {
            super.configure(cfg);
            if (this.simple) {
                return;
            }
            this.function = this.getNumberFormat(cfg.format(), cfg.languageTag())::format;
        }
    }

    /**
     * <pre>
     * The type Parse float.
     * </pre>
     */
    public static class ParseFloat extends AbstractParse<TypeFloat.New, Float> implements AnnotablePipe<TypeFloat.New, String, Float> {

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
        public void configure(TypeFloat.New cfg) {
            super.configure(cfg);
            if (this.simple) {
                this.function = Float::parseFloat;
                return;
            }
            final NumberFormat numberInstance = this.getNumberFormat(cfg.format(),
                                                                     cfg.languageTag());
            this.function = string -> numberInstance.parse(string)
                                                    .floatValue();

        }
    }

    /**
     * <pre>
     * The type Value.
     * </pre>
     */
    public static class Value implements AnnotablePipe<TypeFloat.value, Object, Float> {

        private Float value;

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
            return TypeFloat.strongType;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void configure(TypeFloat.value cfg) {
            this.value = cfg.value();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public ThrowingFunction<Object, Float> function() {
            return object -> this.value;
        }
    }

    /**
     * <pre>
     * The type Short value.
     * </pre>
     */
    public static class ShortValue extends AbstractNullSafe<Float, Short> implements AnnotablePipe<Annotation, Float, Short> {

        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getInputType() {
            return TypeFloat.strongType;
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
            this.function = Float::shortValue;
            super.configure(cfg);
        }
    }

    /**
     * <pre>
     * The type Integer value.
     * </pre>
     */
    public static class IntegerValue extends AbstractNullSafe<Float, Integer> implements AnnotablePipe<Annotation, Float, Integer> {

        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getInputType() {
            return TypeFloat.strongType;
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
            this.function = Float::intValue;
            super.configure(cfg);
        }
    }

    /**
     * <pre>
     * The type Long value.
     * </pre>
     */
    public static class LongValue extends AbstractNullSafe<Float, Long> implements AnnotablePipe<Annotation, Float, Long> {

        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getInputType() {
            return TypeFloat.strongType;
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
            this.function = Float::longValue;
            super.configure(cfg);
        }
    }

    /**
     * <pre>
     * The type Double value.
     * </pre>
     */
    public static class DoubleValue extends AbstractNullSafe<Float, Double> implements AnnotablePipe<Annotation, Float, Double> {

        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getInputType() {
            return TypeFloat.strongType;
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
            this.function = Float::doubleValue;
            super.configure(cfg);
        }
    }

    /**
     * <pre>
     * The type Big integer value.
     * </pre>
     */
    public static class BigIntegerValue extends AbstractNullSafe<Float, BigInteger> implements AnnotablePipe<Annotation, Float, BigInteger> {

        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getInputType() {
            return TypeFloat.strongType;
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
            this.function = floatValue -> BigDecimal.valueOf(floatValue)
                                                    .toBigInteger();
            super.configure(cfg);
        }
    }

    /**
     * <pre>
     * The type Big decimal value.
     * </pre>
     */
    public static class BigDecimalValue extends AbstractNullSafe<Float, BigDecimal> implements AnnotablePipe<Annotation, Float, BigDecimal> {

        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getInputType() {
            return TypeFloat.strongType;
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
            this.function = floatValue -> BigDecimal.valueOf(floatValue);
            super.configure(cfg);
        }
    }

    /**
     * <pre>
     * The type Byte value.
     * </pre>
     */
    public static class ByteValue extends AbstractNullSafe<Float, Byte> implements AnnotablePipe<Annotation, Float, Byte> {

        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getInputType() {
            return TypeFloat.strongType;
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
            this.function = Float::byteValue;
            super.configure(cfg);
        }
    }

    /**
     * <pre>
     * The type Read cell value.
     * </pre>
     */
    public static class ReadCellValue implements AnnotablePipe<TypeFloat.cellValue, Cell, Float> {
        private ThrowingFunction<Cell, Float> function;

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
            return TypeFloat.strongType;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void configure(TypeFloat.cellValue cfg) {
            this.function = cell -> {
                if (cfg.nullSafe() && cell.getCellType() == CellType.BLANK) {
                    return null;
                }
                return (float) cell.getNumericCellValue();
            };
            this.function = this.function.nullSafe(cfg.nullSafe());
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public ThrowingFunction<Cell, Float> function() {
            return this.function;
        }
    }


    /**
     * <pre>
     * The type Write cell value.
     * </pre>
     */
    public static class WriteCellValue implements AnnotablePipe<TypeFloat.cellValue, Float, ThrowingConsumer<Cell>> {
        private ThrowingFunction<Float, ThrowingConsumer<Cell>> function;

        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getInputType() {
            return TypeFloat.strongType;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void configure(TypeFloat.cellValue cfg) {
            if (cfg.nullSafe()) {
                this.function = floater -> floater == null ? cell -> cell.setBlank() : cell -> cell.setCellValue(floater);
            } else {
                this.function = floater -> cell -> cell.setCellValue(floater.doubleValue());
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public ThrowingFunction<Float, ThrowingConsumer<Cell>> function() {
            return this.function;
        }
    }

}
