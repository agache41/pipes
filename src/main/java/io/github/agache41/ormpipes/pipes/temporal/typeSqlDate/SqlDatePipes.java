package io.github.agache41.ormpipes.pipes.temporal.typeSqlDate;

import io.github.agache41.ormpipes.functional.StrongType;
import io.github.agache41.ormpipes.functional.ThrowingConsumer;
import io.github.agache41.ormpipes.functional.ThrowingFunction;
import io.github.agache41.ormpipes.pipe.AnnotablePipe;
import io.github.agache41.ormpipes.pipes.base.format.AbstractFormat;
import io.github.agache41.ormpipes.pipes.base.format.AbstractParse;
import io.github.agache41.ormpipes.pipes.typeObject.TypeObject;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * <pre>
 * The type Sql date pipes.
 * </pre>
 */
public class SqlDatePipes {
    /**
     * <pre>
     * The type Sql date to string.
     * </pre>
     */
    public static class SqlDateToString extends AbstractFormat<TypeSqlDate.New, Date> implements AnnotablePipe<TypeSqlDate.New, Date, String> {

        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getInputType() {
            return TypeSqlDate.strongType;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void configure(TypeSqlDate.New cfg) {
            super.configure(cfg);
            if (this.simple) {
                return;
            }
            final DateTimeFormatter formatter = this.getDateTimeFormatter(cfg.format(), cfg.languageTag(), cfg.zoneId());
            this.function = sqlDate -> formatter.format(sqlDate.toLocalDate());
        }
    }

    /**
     * <pre>
     * The type Parse sql date.
     * </pre>
     */
    public static class ParseSqlDate extends AbstractParse<TypeSqlDate.New, Date> implements AnnotablePipe<TypeSqlDate.New, String, Date> {

        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getOutputType() {
            return TypeSqlDate.strongType;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void configure(TypeSqlDate.New cfg) {
            super.configure(cfg);
            super.configure(cfg);
            if (this.simple) {
                this.function = Date::valueOf;
                return;
            }
            final DateTimeFormatter formatter = this.getDateTimeFormatter(cfg.format(), cfg.languageTag(), cfg.zoneId());
            this.function = string -> Date.valueOf(formatter.parse(string, LocalDate::from));
        }
    }

    /**
     * <pre>
     * The type Now.
     * </pre>
     */
    public static class Now implements AnnotablePipe<TypeSqlDate.now, Object, Date> {
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
            return TypeSqlDate.strongType;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void configure(TypeSqlDate.now cfg) {
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public ThrowingFunction<Object, Date> function() {
            return object -> new Date(System.currentTimeMillis());
        }
    }

    /**
     * <pre>
     * The type Read cell value.
     * </pre>
     */
    public static class ReadCellValue implements AnnotablePipe<TypeSqlDate.cellValue, Cell, Date> {
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
            return TypeSqlDate.strongType;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void configure(TypeSqlDate.cellValue cfg) {
            this.function = cell -> {
                if (cfg.nullSafe() && cell.getCellType() == CellType.BLANK) {
                    return null;
                }
                return new Date(cell.getDateCellValue()
                                    .getTime());
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
    public static class WriteCellValue implements AnnotablePipe<TypeSqlDate.cellValue, Date, ThrowingConsumer<Cell>> {
        private ThrowingFunction<Date, ThrowingConsumer<Cell>> function;

        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getInputType() {
            return TypeSqlDate.strongType;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void configure(TypeSqlDate.cellValue cfg) {
            if (cfg.nullSafe()) {
                this.function = sqlDate -> sqlDate == null ? cell -> cell.setBlank() : cell -> cell.setCellValue(sqlDate);
            } else {
                this.function = sqlDate -> cell -> cell.setCellValue(sqlDate);
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
