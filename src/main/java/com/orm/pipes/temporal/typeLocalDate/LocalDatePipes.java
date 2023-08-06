package com.orm.pipes.temporal.typeLocalDate;

import com.orm.functional.StrongType;
import com.orm.functional.ThrowingConsumer;
import com.orm.functional.ThrowingFunction;
import com.orm.pipe.AnnotablePipe;
import com.orm.pipes.base.format.AbstractFormat;
import com.orm.pipes.base.format.AbstractParse;
import com.orm.pipes.typeObject.TypeObject;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDatePipes {
    public static class LocalDateToString extends AbstractFormat<TypeLocalDate.New, LocalDate> implements AnnotablePipe<TypeLocalDate.New, LocalDate, String> {

        @Override
        public StrongType getInputType() {
            return TypeLocalDate.strongType;
        }

        @Override
        public void configure(TypeLocalDate.New cfg) {
            super.configure(cfg);
            if (this.simple) return;
            final DateTimeFormatter formatter = this.getDateTimeFormatter(cfg.format(), cfg.languageTag(), cfg.zoneId());
            this.function = formatter::format;
        }
    }

    public static class ParseLocalDate extends AbstractParse<TypeLocalDate.New, LocalDate> implements AnnotablePipe<TypeLocalDate.New, String, LocalDate> {

        @Override
        public StrongType getOutputType() {
            return TypeLocalDate.strongType;
        }

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

    public static class Now implements AnnotablePipe<TypeLocalDate.now, Object, LocalDate> {
        @Override
        public StrongType getInputType() {
            return TypeObject.strongType;
        }

        @Override
        public StrongType getOutputType() {
            return TypeLocalDate.strongType;
        }

        @Override
        public void configure(TypeLocalDate.now cfg) {
        }

        @Override
        public ThrowingFunction<Object, LocalDate> function() {
            return object -> LocalDate.now();
        }
    }

    public static class ReadCellValue implements AnnotablePipe<TypeLocalDate.cellValue, Cell, LocalDate> {
        private ThrowingFunction<Cell, LocalDate> function;

        @Override
        public StrongType getInputType() {
            return StrongType.of(Cell.class);
        }

        @Override
        public StrongType getOutputType() {
            return TypeLocalDate.strongType;
        }

        @Override
        public void configure(TypeLocalDate.cellValue cfg) {
            this.function = cell -> {
                if (cfg.nullSafe() && cell.getCellType() == CellType.BLANK) return null;
                return cell.getLocalDateTimeCellValue()
                           .toLocalDate();
            };
            this.function = this.function.nullSafe(cfg.nullSafe());
        }

        @Override
        public ThrowingFunction<Cell, LocalDate> function() {
            return this.function;
        }
    }


    public static class WriteCellValue implements AnnotablePipe<TypeLocalDate.cellValue, LocalDate, ThrowingConsumer<Cell>> {
        private ThrowingFunction<LocalDate, ThrowingConsumer<Cell>> function;

        @Override
        public StrongType getInputType() {
            return TypeLocalDate.strongType;
        }

        @Override
        public void configure(TypeLocalDate.cellValue cfg) {
            if (cfg.nullSafe())
                this.function = localDate -> localDate == null ? cell -> cell.setBlank() : cell -> cell.setCellValue(localDate);
            else
                this.function = localDate -> cell -> cell.setCellValue(localDate.atStartOfDay());
        }

        @Override
        public ThrowingFunction<LocalDate, ThrowingConsumer<Cell>> function() {
            return this.function;
        }
    }
}
