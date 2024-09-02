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

package io.github.agache41.ormpipes.pipes.numeric.typeDouble;

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
import java.text.NumberFormat;

/**
 * <pre>
 * The type Double pipes.
 * </pre>
 */
public class DoublePipes {
    /**
     * <pre>
     * The type Double to string.
     * </pre>
     */
    public static class DoubleToString extends AbstractFormat<TypeDouble.New, Double> implements AnnotablePipe<TypeDouble.New, Double, String> {

        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getInputType() {
            return TypeDouble.strongType;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void configure(TypeDouble.New cfg) {
            super.configure(cfg);
            if (this.simple) {
                return;
            }
            this.function = this.getNumberFormat(cfg.format(), cfg.languageTag())::format;
        }
    }

    /**
     * <pre>
     * The type Parse double.
     * </pre>
     */
    public static class ParseDouble extends AbstractParse<TypeDouble.New, Double> implements AnnotablePipe<TypeDouble.New, String, Double> {

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
        public void configure(TypeDouble.New cfg) {
            super.configure(cfg);
            if (this.simple) {
                this.function = Double::parseDouble;
                return;
            }
            final NumberFormat numberInstance = this.getNumberFormat(cfg.format(),
                                                                     cfg.languageTag());
            this.function = string -> numberInstance.parse(string)
                                                    .doubleValue();
        }
    }

    /**
     * <pre>
     * The type Value.
     * </pre>
     */
    public static class Value implements AnnotablePipe<TypeDouble.value, Object, Double> {

        private Double value;

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
            return TypeDouble.strongType;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void configure(TypeDouble.value cfg) {
            this.value = cfg.value();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public ThrowingFunction<Object, Double> function() {
            return object -> this.value;
        }
    }

    /**
     * <pre>
     * The type Short value.
     * </pre>
     */
    public static class ShortValue extends AbstractNullSafe<Double, Short> implements AnnotablePipe<Annotation, Double, Short> {

        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getInputType() {
            return TypeDouble.strongType;
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
            this.function = Double::shortValue;
            super.configure(cfg);
        }
    }

    /**
     * <pre>
     * The type Integer value.
     * </pre>
     */
    public static class IntegerValue extends AbstractNullSafe<Double, Integer> implements AnnotablePipe<Annotation, Double, Integer> {

        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getInputType() {
            return TypeDouble.strongType;
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
            this.function = Double::intValue;
            super.configure(cfg);
        }
    }

    /**
     * <pre>
     * The type Long value.
     * </pre>
     */
    public static class LongValue extends AbstractNullSafe<Double, Long> implements AnnotablePipe<Annotation, Double, Long> {

        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getInputType() {
            return TypeDouble.strongType;
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
            this.function = Double::longValue;
            super.configure(cfg);
        }
    }

    /**
     * <pre>
     * The type Float value.
     * </pre>
     */
    public static class FloatValue extends AbstractNullSafe<Double, Float> implements AnnotablePipe<Annotation, Double, Float> {

        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getInputType() {
            return TypeDouble.strongType;
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
            this.function = Double::floatValue;
            super.configure(cfg);
        }
    }

    /**
     * <pre>
     * The type Big integer value.
     * </pre>
     */
    public static class BigIntegerValue extends AbstractNullSafe<Double, BigInteger> implements AnnotablePipe<Annotation, Double, BigInteger> {

        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getInputType() {
            return TypeDouble.strongType;
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
            this.function = doubleValue -> BigDecimal.valueOf(doubleValue)
                                                     .toBigInteger();
            super.configure(cfg);
        }
    }

    /**
     * <pre>
     * The type Big decimal value.
     * </pre>
     */
    public static class BigDecimalValue extends AbstractNullSafe<Double, BigDecimal> implements AnnotablePipe<Annotation, Double, BigDecimal> {

        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getInputType() {
            return TypeDouble.strongType;
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
            this.function = doubleValue -> BigDecimal.valueOf(doubleValue);
            super.configure(cfg);
        }
    }

    /**
     * <pre>
     * The type Byte value.
     * </pre>
     */
    public static class ByteValue extends AbstractNullSafe<Double, Byte> implements AnnotablePipe<Annotation, Double, Byte> {

        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getInputType() {
            return TypeDouble.strongType;
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
            this.function = Double::byteValue;
            super.configure(cfg);
        }
    }

    /**
     * <pre>
     * The type Read cell value.
     * </pre>
     */
    public static class ReadCellValue implements AnnotablePipe<TypeDouble.cellValue, Cell, Double> {
        private ThrowingFunction<Cell, Double> function;

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
            return TypeDouble.strongType;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void configure(TypeDouble.cellValue cfg) {
            this.function = cell -> {
                if (cfg.nullSafe() && cell.getCellType() == CellType.BLANK) {
                    return null;
                }
                return cell.getNumericCellValue();
            };
            this.function = this.function.nullSafe(cfg.nullSafe());
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public ThrowingFunction<Cell, Double> function() {
            return this.function;
        }
    }


    /**
     * <pre>
     * The type Write cell value.
     * </pre>
     */
    public static class WriteCellValue implements AnnotablePipe<TypeDouble.cellValue, Double, ThrowingConsumer<Cell>> {
        private ThrowingFunction<Double, ThrowingConsumer<Cell>> function;

        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getInputType() {
            return TypeDouble.strongType;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void configure(TypeDouble.cellValue cfg) {
            if (cfg.nullSafe()) {
                this.function = doubler -> doubler == null ? cell -> cell.setBlank() : cell -> cell.setCellValue(doubler);
            } else {
                this.function = doubler -> cell -> cell.setCellValue(doubler);
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public ThrowingFunction<Double, ThrowingConsumer<Cell>> function() {
            return this.function;
        }
    }

}
