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

package io.github.agache41.ormpipes.pipes.temporal.typeDate;

import io.github.agache41.ormpipes.functional.StrongType;
import io.github.agache41.ormpipes.functional.ThrowingConsumer;
import io.github.agache41.ormpipes.functional.ThrowingFunction;
import io.github.agache41.ormpipes.pipe.AnnotablePipe;
import io.github.agache41.ormpipes.pipes.base.format.AbstractFormat;
import io.github.agache41.ormpipes.pipes.base.format.AbstractParse;
import io.github.agache41.ormpipes.pipes.typeObject.TypeObject;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <pre>
 * The type Date pipes.
 * </pre>
 */
public class DatePipes {
    /**
     * <pre>
     * The type Date to string.
     * </pre>
     */
    public static class DateToString extends AbstractFormat<TypeDate.New, Date> implements AnnotablePipe<TypeDate.New, Date, String> {

        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getInputType() {
            return TypeDate.strongType;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void configure(TypeDate.New cfg) {
            super.configure(cfg);
            if (this.simple) {
                return;
            }
            final SimpleDateFormat simpleDateFormat = this.getSimpleDateFormat(cfg.format(), cfg.languageTag());
            this.function = simpleDateFormat::format;
        }
    }

    /**
     * <pre>
     * The type Parse date.
     * </pre>
     */
    public static class ParseDate extends AbstractParse<TypeDate.New, Date> implements AnnotablePipe<TypeDate.New, String, Date> {

        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getOutputType() {
            return TypeDate.strongType;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void configure(TypeDate.New cfg) {
            super.configure(cfg);
            if (this.simple) {
                this.function = Date::new;
                return;
            }
            final SimpleDateFormat simpleDateFormat = this.getSimpleDateFormat(cfg.format(), cfg.languageTag());
            this.function = simpleDateFormat::parse;
        }
    }

    /**
     * <pre>
     * The type Now.
     * </pre>
     */
    public static class Now implements AnnotablePipe<TypeDate.now, Object, Date> {
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
            return TypeDate.strongType;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void configure(TypeDate.now cfg) {
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public ThrowingFunction<Object, Date> function() {
            return object -> new Date();
        }
    }

    /**
     * <pre>
     * The type Read cell value.
     * </pre>
     */
    public static class ReadCellValue implements AnnotablePipe<TypeDate.cellValue, Cell, Date> {
        private ThrowingFunction<Cell, Date> function;

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
            return TypeDate.strongType;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void configure(TypeDate.cellValue cfg) {
            this.function = cell -> {
                if (cfg.nullSafe() && cell.getCellType() == CellType.BLANK) {
                    return null;
                }
                return cell.getDateCellValue();
            };
            this.function = this.function.nullSafe(cfg.nullSafe());
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public ThrowingFunction<Cell, Date> function() {
            return this.function;
        }
    }


    /**
     * <pre>
     * The type Write cell value.
     * </pre>
     */
    public static class WriteCellValue implements AnnotablePipe<TypeDate.cellValue, Date, ThrowingConsumer<Cell>> {
        private ThrowingFunction<Date, ThrowingConsumer<Cell>> function;

        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getInputType() {
            return TypeDate.strongType;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void configure(TypeDate.cellValue cfg) {
            if (cfg.nullSafe()) {
                this.function = date -> date == null ? cell -> cell.setBlank() : cell -> cell.setCellValue(date);
            } else {
                this.function = date -> cell -> cell.setCellValue(date);
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public ThrowingFunction<Date, ThrowingConsumer<Cell>> function() {
            return this.function;
        }
    }
}
