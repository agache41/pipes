package io.github.agache41.ormpipes.pipes.temporal.typeLocalDate;

import io.github.agache41.ormpipes.functional.StrongType;
import io.github.agache41.ormpipes.functional.ThrowingConsumer;
import io.github.agache41.ormpipes.functional.ThrowingFunction;
import io.github.agache41.ormpipes.pipe.AnnotablePipe;
import io.github.agache41.ormpipes.pipes.base.format.AbstractFormat;
import io.github.agache41.ormpipes.pipes.base.format.AbstractParse;
import io.github.agache41.ormpipes.pipes.typeObject.TypeObject;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * <pre>
 * The type Local date pipes.
 * </pre>
 */
public class LocalDatePipes {
    /**
     * <pre>
     * The type Local date to string.
     * </pre>
     */
    public static class LocalDateToString extends AbstractFormat<TypeLocalDate.New, LocalDate> implements AnnotablePipe<TypeLocalDate.New, LocalDate, String> {

        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getInputType() {
            return TypeLocalDate.strongType;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void configure(TypeLocalDate.New cfg) {
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
     * The type Parse local date.
     * </pre>
     */
    public static class ParseLocalDate extends AbstractParse<TypeLocalDate.New, LocalDate> implements AnnotablePipe<TypeLocalDate.New, String, LocalDate> {

        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getOutputType() {
            return TypeLocalDate.strongType;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void configure(TypeLocalDate.New cfg) {
            super.configure(cfg);
            if (this.simple) {
                this.function = LocalDate::parse;
                return;
            }
            final DateTimeFormatter formatter = this.getDateTimeFormatter(cfg.format(), cfg.languageTag(), cfg.zoneId());
            this.function = string -> formatter.parse(string, LocalDate::from);

        }
    }

    /**
     * <pre>
     * The type Now.
     * </pre>
     */
    public static class Now implements AnnotablePipe<TypeLocalDate.now, Object, LocalDate> {
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
            return TypeLocalDate.strongType;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void configure(TypeLocalDate.now cfg) {
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public ThrowingFunction<Object, LocalDate> function() {
            return object -> LocalDate.now();
        }
    }

    /**
     * <pre>
     * The type Read cell value.
     * </pre>
     */
    public static class ReadCellValue implements AnnotablePipe<TypeLocalDate.cellValue, Cell, LocalDate> {
        private ThrowingFunction<Cell, LocalDate> function;

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
            return TypeLocalDate.strongType;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void configure(TypeLocalDate.cellValue cfg) {
            this.function = cell -> {
                if (cfg.nullSafe() && cell.getCellType() == CellType.BLANK) {
                    return null;
                }
                return cell.getLocalDateTimeCellValue()
                           .toLocalDate();
            };
            this.function = this.function.nullSafe(cfg.nullSafe());
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public ThrowingFunction<Cell, LocalDate> function() {
            return this.function;
        }
    }


    /**
     * <pre>
     * The type Write cell value.
     * </pre>
     */
    public static class WriteCellValue implements AnnotablePipe<TypeLocalDate.cellValue, LocalDate, ThrowingConsumer<Cell>> {
        private ThrowingFunction<LocalDate, ThrowingConsumer<Cell>> function;

        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getInputType() {
            return TypeLocalDate.strongType;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void configure(TypeLocalDate.cellValue cfg) {
            if (cfg.nullSafe()) {
                this.function = localDate -> localDate == null ? cell -> cell.setBlank() : cell -> cell.setCellValue(localDate);
            } else {
                this.function = localDate -> cell -> cell.setCellValue(localDate.atStartOfDay());
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public ThrowingFunction<LocalDate, ThrowingConsumer<Cell>> function() {
            return this.function;
        }
    }
}
