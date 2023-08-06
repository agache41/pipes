package com.orm.pipes.temporal.typeLocalDateTime;

import com.orm.functional.StrongType;
import com.orm.functional.ThrowingConsumer;
import com.orm.functional.ThrowingFunction;
import com.orm.pipe.AnnotablePipe;
import com.orm.pipes.base.format.AbstractFormat;
import com.orm.pipes.base.format.AbstractParse;
import com.orm.pipes.typeObject.TypeObject;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimePipes {
    public static class LocalDateTimeToString extends AbstractFormat<TypeLocalDateTime.New, LocalDateTime> implements AnnotablePipe<TypeLocalDateTime.New, LocalDateTime, String> {

        @Override
        public StrongType getInputType() {
            return TypeLocalDateTime.strongType;
        }

        @Override
        public void configure(TypeLocalDateTime.New cfg) {
            super.configure(cfg);
            if (this.simple) return;
            final DateTimeFormatter formatter = this.getDateTimeFormatter(cfg.format(), cfg.languageTag(), cfg.zoneId());
            this.function = formatter::format;
        }
    }

    public static class ParseLocalDateTime extends AbstractParse<TypeLocalDateTime.New, LocalDateTime> implements AnnotablePipe<TypeLocalDateTime.New, String, LocalDateTime> {

        @Override
        public StrongType getOutputType() {
            return TypeLocalDateTime.strongType;
        }

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

    public static class Now implements AnnotablePipe<TypeLocalDateTime.now, Object, LocalDateTime> {
        @Override
        public StrongType getInputType() {
            return TypeObject.strongType;
        }

        @Override
        public StrongType getOutputType() {
            return TypeLocalDateTime.strongType;
        }

        @Override
        public void configure(TypeLocalDateTime.now cfg) {
        }

        @Override
        public ThrowingFunction<Object, LocalDateTime> function() {
            return object -> LocalDateTime.now();
        }
    }

    public static class ReadCellValue implements AnnotablePipe<TypeLocalDateTime.cellValue, Cell, LocalDateTime> {
        private ThrowingFunction<Cell, LocalDateTime> function;

        @Override
        public StrongType getInputType() {
            return StrongType.of(Cell.class);
        }

        @Override
        public StrongType getOutputType() {
            return TypeLocalDateTime.strongType;
        }

        @Override
        public void configure(TypeLocalDateTime.cellValue cfg) {
            this.function = cell -> {
                if (cfg.nullSafe() && cell.getCellType() == CellType.BLANK) return null;
                return cell.getLocalDateTimeCellValue();
            };
            this.function = this.function.nullSafe(cfg.nullSafe());
        }

        @Override
        public ThrowingFunction<Cell, LocalDateTime> function() {
            return this.function;
        }
    }


    public static class WriteCellValue implements AnnotablePipe<TypeLocalDateTime.cellValue, LocalDateTime, ThrowingConsumer<Cell>> {
        private ThrowingFunction<LocalDateTime, ThrowingConsumer<Cell>> function;

        @Override
        public StrongType getInputType() {
            return TypeLocalDateTime.strongType;
        }

        @Override
        public void configure(TypeLocalDateTime.cellValue cfg) {
            if (cfg.nullSafe())
                this.function = localDateTime -> localDateTime == null ? cell -> cell.setBlank() : cell -> cell.setCellValue(localDateTime);
            else
                this.function = localDateTime -> cell -> cell.setCellValue(localDateTime);
        }

        @Override
        public ThrowingFunction<LocalDateTime, ThrowingConsumer<Cell>> function() {
            return this.function;
        }
    }
}
