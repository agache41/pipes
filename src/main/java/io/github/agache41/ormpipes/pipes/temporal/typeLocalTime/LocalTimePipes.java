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

package io.github.agache41.ormpipes.pipes.temporal.typeLocalTime;

import io.github.agache41.ormpipes.functional.StrongType;
import io.github.agache41.ormpipes.functional.ThrowingConsumer;
import io.github.agache41.ormpipes.functional.ThrowingFunction;
import io.github.agache41.ormpipes.pipe.AnnotablePipe;
import io.github.agache41.ormpipes.pipes.base.format.AbstractFormat;
import io.github.agache41.ormpipes.pipes.base.format.AbstractParse;
import io.github.agache41.ormpipes.pipes.typeObject.TypeObject;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * <pre>
 * The type Local time pipes.
 * </pre>
 */
public class LocalTimePipes {
    /**
     * <pre>
     * The type Local time to string.
     * </pre>
     */
    public static class LocalTimeToString extends AbstractFormat<TypeLocalTime.New, LocalTime> implements AnnotablePipe<TypeLocalTime.New, LocalTime, String> {

        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getInputType() {
            return TypeLocalTime.strongType;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void configure(TypeLocalTime.New cfg) {
            super.configure(cfg);
            if (this.simple) {
                return;
            }
            final DateTimeFormatter formatter = this.getDateTimeFormatter(cfg.format(), cfg.languageTag(), cfg.zoneId());
            this.function = formatter::format;
        }
    }

    /**
     * <pre>
     * The type Parse local time.
     * </pre>
     */
    public static class ParseLocalTime extends AbstractParse<TypeLocalTime.New, LocalTime> implements AnnotablePipe<TypeLocalTime.New, String, LocalTime> {

        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getOutputType() {
            return TypeLocalTime.strongType;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void configure(TypeLocalTime.New cfg) {
            super.configure(cfg);
            if (this.simple) {
                this.function = LocalTime::parse;
                return;
            }
            final DateTimeFormatter formatter = this.getDateTimeFormatter(cfg.format(), cfg.languageTag(), cfg.zoneId());
            this.function = string -> formatter.parse(string, LocalTime::from);
        }
    }

    /**
     * <pre>
     * The type Now.
     * </pre>
     */
    public static class Now implements AnnotablePipe<TypeLocalTime.now, Object, LocalTime> {
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
            return TypeLocalTime.strongType;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void configure(TypeLocalTime.now cfg) {
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public ThrowingFunction<Object, LocalTime> function() {
            return object -> LocalTime.now();
        }
    }

    /**
     * <pre>
     * The type Read cell value.
     * </pre>
     */
    public static class ReadCellValue implements AnnotablePipe<TypeLocalTime.cellValue, Cell, LocalTime> {
        private ThrowingFunction<Cell, LocalTime> function;

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
            return TypeLocalTime.strongType;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void configure(TypeLocalTime.cellValue cfg) {
            this.function = cell -> {
                if (cfg.nullSafe() && cell.getCellType() == CellType.BLANK) {
                    return null;
                }
                return cell.getLocalDateTimeCellValue()
                           .toLocalTime();
            };
            this.function = this.function.nullSafe(cfg.nullSafe());
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public ThrowingFunction<Cell, LocalTime> function() {
            return this.function;
        }
    }


    /**
     * <pre>
     * The type Write cell value.
     * </pre>
     */
    public static class WriteCellValue implements AnnotablePipe<TypeLocalTime.cellValue, LocalTime, ThrowingConsumer<Cell>> {
        private final DateTimeFormatter excelTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        private ThrowingFunction<LocalTime, ThrowingConsumer<Cell>> function;

        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getInputType() {
            return TypeLocalTime.strongType;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void configure(TypeLocalTime.cellValue cfg) {
            if (cfg.nullSafe()) {
                this.function = localTime -> localTime == null ? cell -> cell.setBlank() : cell -> cell.setCellValue(this.toExcelTime(localTime));
            } else {
                this.function = localTime -> cell -> cell.setCellValue(this.toExcelTime(localTime));
            }
        }

        private double toExcelTime(LocalTime localTime) {
            return DateUtil.convertTime(this.excelTimeFormatter.format(localTime));
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public ThrowingFunction<LocalTime, ThrowingConsumer<Cell>> function() {
            return this.function;
        }
    }
}
