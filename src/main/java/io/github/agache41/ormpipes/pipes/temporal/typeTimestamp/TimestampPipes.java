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

package io.github.agache41.ormpipes.pipes.temporal.typeTimestamp;

import io.github.agache41.ormpipes.functional.StrongType;
import io.github.agache41.ormpipes.functional.ThrowingConsumer;
import io.github.agache41.ormpipes.functional.ThrowingFunction;
import io.github.agache41.ormpipes.pipe.AnnotablePipe;
import io.github.agache41.ormpipes.pipes.base.format.AbstractFormat;
import io.github.agache41.ormpipes.pipes.base.format.AbstractParse;
import io.github.agache41.ormpipes.pipes.temporal.typeDate.TypeDate;
import io.github.agache41.ormpipes.pipes.typeObject.TypeObject;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * <pre>
 * The type Timestamp pipes.
 * </pre>
 */
public class TimestampPipes {
    /**
     * <pre>
     * The type Timestamp to string.
     * </pre>
     */
    public static class TimestampToString extends AbstractFormat<TypeTimestamp.New, Timestamp> implements AnnotablePipe<TypeTimestamp.New, Timestamp, String> {

        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getInputType() {
            return TypeTimestamp.strongType;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void configure(TypeTimestamp.New cfg) {
            super.configure(cfg);
            if (this.simple) {
                return;
            }
            final DateTimeFormatter formatter = this.getDateTimeFormatter(cfg.format(), cfg.languageTag(), cfg.zoneId());
            this.function = timestamp -> formatter.format(timestamp.toLocalDateTime());
        }
    }

    /**
     * <pre>
     * The type Parse timestamp.
     * </pre>
     */
    public static class ParseTimestamp extends AbstractParse<TypeTimestamp.New, Timestamp> implements AnnotablePipe<TypeTimestamp.New, String, Timestamp> {

        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getOutputType() {
            return TypeTimestamp.strongType;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void configure(TypeTimestamp.New cfg) {
            super.configure(cfg);
            super.configure(cfg);
            if (this.simple) {
                this.function = Timestamp::valueOf;
                return;
            }
            final DateTimeFormatter formatter = this.getDateTimeFormatter(cfg.format(), cfg.languageTag(), cfg.zoneId());
            this.function = string -> Timestamp.valueOf(formatter.parse(string, LocalDateTime::from));
        }
    }

    /**
     * <pre>
     * The type Now.
     * </pre>
     */
    public static class Now implements AnnotablePipe<TypeTimestamp.now, Object, Timestamp> {
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
            return TypeTimestamp.strongType;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void configure(TypeTimestamp.now cfg) {
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public ThrowingFunction<Object, Timestamp> function() {
            return object -> new Timestamp(System.currentTimeMillis());
        }
    }

    /**
     * <pre>
     * The type Read cell value.
     * </pre>
     */
    public static class ReadCellValue implements AnnotablePipe<TypeTimestamp.cellValue, Cell, Timestamp> {
        private ThrowingFunction<Cell, Timestamp> function;

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
            return TypeTimestamp.strongType;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void configure(TypeTimestamp.cellValue cfg) {
            this.function = cell -> {
                if (cfg.nullSafe() && cell.getCellType() == CellType.BLANK) {
                    return null;
                }
                return new Timestamp(cell.getDateCellValue()
                                         .getTime());
            };
            this.function = this.function.nullSafe(cfg.nullSafe());
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public ThrowingFunction<Cell, Timestamp> function() {
            return this.function;
        }
    }


    /**
     * <pre>
     * The type Write cell value.
     * </pre>
     */
    public static class WriteCellValue implements AnnotablePipe<TypeTimestamp.cellValue, Timestamp, ThrowingConsumer<Cell>> {
        private ThrowingFunction<Timestamp, ThrowingConsumer<Cell>> function;

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
        public void configure(TypeTimestamp.cellValue cfg) {
            if (cfg.nullSafe()) {
                this.function = timestamp -> timestamp == null ? cell -> cell.setBlank() : cell -> cell.setCellValue(timestamp);
            } else {
                this.function = timestamp -> cell -> cell.setCellValue(timestamp);
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public ThrowingFunction<Timestamp, ThrowingConsumer<Cell>> function() {
            return this.function;
        }
    }
}
