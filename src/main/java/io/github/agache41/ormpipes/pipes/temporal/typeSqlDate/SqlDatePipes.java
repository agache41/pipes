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

public class SqlDatePipes {
    public static class SqlDateToString extends AbstractFormat<TypeSqlDate.New, Date> implements AnnotablePipe<TypeSqlDate.New, Date, String> {

        @Override
        public StrongType getInputType() {
            return TypeSqlDate.strongType;
        }

        @Override
        public void configure(TypeSqlDate.New cfg) {
            super.configure(cfg);
            if (this.simple) return;
            final DateTimeFormatter formatter = this.getDateTimeFormatter(cfg.format(), cfg.languageTag(), cfg.zoneId());
            this.function = sqlDate -> formatter.format(sqlDate.toLocalDate());
        }
    }

    public static class ParseSqlDate extends AbstractParse<TypeSqlDate.New, Date> implements AnnotablePipe<TypeSqlDate.New, String, Date> {

        @Override
        public StrongType getOutputType() {
            return TypeSqlDate.strongType;
        }

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

    public static class Now implements AnnotablePipe<TypeSqlDate.now, Object, Date> {
        @Override
        public StrongType getInputType() {
            return TypeObject.strongType;
        }

        @Override
        public StrongType getOutputType() {
            return TypeSqlDate.strongType;
        }

        @Override
        public void configure(TypeSqlDate.now cfg) {
        }

        @Override
        public ThrowingFunction<Object, Date> function() {
            return object -> new Date(System.currentTimeMillis());
        }
    }

    public static class ReadCellValue implements AnnotablePipe<TypeSqlDate.cellValue, Cell, Date> {
        private ThrowingFunction<Cell, Date> function;

        @Override
        public StrongType getInputType() {
            return StrongType.of(Cell.class);
        }

        @Override
        public StrongType getOutputType() {
            return TypeSqlDate.strongType;
        }

        @Override
        public void configure(TypeSqlDate.cellValue cfg) {
            this.function = cell -> {
                if (cfg.nullSafe() && cell.getCellType() == CellType.BLANK) return null;
                return new Date(cell.getDateCellValue()
                                    .getTime());
            };
            this.function = this.function.nullSafe(cfg.nullSafe());
        }

        @Override
        public ThrowingFunction<Cell, Date> function() {
            return this.function;
        }
    }


    public static class WriteCellValue implements AnnotablePipe<TypeSqlDate.cellValue, Date, ThrowingConsumer<Cell>> {
        private ThrowingFunction<Date, ThrowingConsumer<Cell>> function;

        @Override
        public StrongType getInputType() {
            return TypeSqlDate.strongType;
        }

        @Override
        public void configure(TypeSqlDate.cellValue cfg) {
            if (cfg.nullSafe())
                this.function = sqlDate -> sqlDate == null ? cell -> cell.setBlank() : cell -> cell.setCellValue(sqlDate);
            else
                this.function = sqlDate -> cell -> cell.setCellValue(sqlDate);
        }

        @Override
        public ThrowingFunction<Date, ThrowingConsumer<Cell>> function() {
            return this.function;
        }
    }
}
