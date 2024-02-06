package io.github.agache41.ormpipes.pipes.temporal.typeLocalDateTime;

import io.github.agache41.ormpipes.functional.StrongType;
import io.github.agache41.ormpipes.functional.ThrowingConsumer;
import io.github.agache41.ormpipes.functional.ThrowingFunction;
import io.github.agache41.ormpipes.pipe.AnnotablePipe;
import io.github.agache41.ormpipes.pipes.base.format.AbstractFormat;
import io.github.agache41.ormpipes.pipes.base.format.AbstractParse;
import io.github.agache41.ormpipes.pipes.typeObject.TypeObject;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * <pre>
 * The type Local date time pipes.
 * </pre>
 */
public class LocalDateTimePipes {
    /**
     * <pre>
     * The type Local date time to string.
     * </pre>
     */
    public static class LocalDateTimeToString extends AbstractFormat<TypeLocalDateTime.New, LocalDateTime> implements AnnotablePipe<TypeLocalDateTime.New, LocalDateTime, String> {

        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getInputType() {
            return TypeLocalDateTime.strongType;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void configure(TypeLocalDateTime.New cfg) {
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
     * The type Parse local date time.
     * </pre>
     */
    public static class ParseLocalDateTime extends AbstractParse<TypeLocalDateTime.New, LocalDateTime> implements AnnotablePipe<TypeLocalDateTime.New, String, LocalDateTime> {

        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getOutputType() {
            return TypeLocalDateTime.strongType;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void configure(TypeLocalDateTime.New cfg) {
            super.configure(cfg);
            if (this.simple) {
                this.function = LocalDateTime::parse;
                return;
            }
            final DateTimeFormatter formatter = this.getDateTimeFormatter(cfg.format(), cfg.languageTag(), cfg.zoneId());
            this.function = string -> formatter.parse(string, LocalDateTime::from);
        }
    }

    /**
     * <pre>
     * The type Now.
     * </pre>
     */
    public static class Now implements AnnotablePipe<TypeLocalDateTime.now, Object, LocalDateTime> {
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
            return TypeLocalDateTime.strongType;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void configure(TypeLocalDateTime.now cfg) {
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public ThrowingFunction<Object, LocalDateTime> function() {
            return object -> LocalDateTime.now();
        }
    }

    /**
     * <pre>
     * The type Read cell value.
     * </pre>
     */
    public static class ReadCellValue implements AnnotablePipe<TypeLocalDateTime.cellValue, Cell, LocalDateTime> {
        private ThrowingFunction<Cell, LocalDateTime> function;

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
            return TypeLocalDateTime.strongType;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void configure(TypeLocalDateTime.cellValue cfg) {
            this.function = cell -> {
                if (cfg.nullSafe() && cell.getCellType() == CellType.BLANK) {
                    return null;
                }
                return cell.getLocalDateTimeCellValue();
            };
            this.function = this.function.nullSafe(cfg.nullSafe());
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public ThrowingFunction<Cell, LocalDateTime> function() {
            return this.function;
        }
    }


    /**
     * <pre>
     * The type Write cell value.
     * </pre>
     */
    public static class WriteCellValue implements AnnotablePipe<TypeLocalDateTime.cellValue, LocalDateTime, ThrowingConsumer<Cell>> {
        private ThrowingFunction<LocalDateTime, ThrowingConsumer<Cell>> function;

        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getInputType() {
            return TypeLocalDateTime.strongType;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void configure(TypeLocalDateTime.cellValue cfg) {
            if (cfg.nullSafe()) {
                this.function = localDateTime -> localDateTime == null ? cell -> cell.setBlank() : cell -> cell.setCellValue(localDateTime);
            } else {
                this.function = localDateTime -> cell -> cell.setCellValue(localDateTime);
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public ThrowingFunction<LocalDateTime, ThrowingConsumer<Cell>> function() {
            return this.function;
        }
    }
}
