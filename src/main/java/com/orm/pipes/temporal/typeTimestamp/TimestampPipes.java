package com.orm.pipes.temporal.typeTimestamp;

import com.orm.functional.StrongType;
import com.orm.functional.ThrowingConsumer;
import com.orm.functional.ThrowingFunction;
import com.orm.pipe.AnnotablePipe;
import com.orm.pipes.base.format.AbstractFormat;
import com.orm.pipes.base.format.AbstractParse;
import com.orm.pipes.temporal.typeDate.TypeDate;
import com.orm.pipes.typeObject.TypeObject;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimestampPipes {
    public static class TimestampToString extends AbstractFormat<TypeTimestamp.New, Timestamp> implements AnnotablePipe<TypeTimestamp.New, Timestamp, String> {

        @Override
        public StrongType getInputType() {
            return TypeTimestamp.strongType;
        }

        @Override
        public void configure(TypeTimestamp.New cfg) {
            super.configure(cfg);
            if (this.simple) return;
            final DateTimeFormatter formatter = this.getDateTimeFormatter(cfg.format(), cfg.languageTag(), cfg.zoneId());
            this.function = timestamp -> formatter.format(timestamp.toLocalDateTime());
        }
    }

    public static class ParseTimestamp extends AbstractParse<TypeTimestamp.New, Timestamp> implements AnnotablePipe<TypeTimestamp.New, String, Timestamp> {

        @Override
        public StrongType getOutputType() {
            return TypeTimestamp.strongType;
        }

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

    public static class Now implements AnnotablePipe<TypeTimestamp.now, Object, Timestamp> {
        @Override
        public StrongType getInputType() {
            return TypeObject.strongType;
        }

        @Override
        public StrongType getOutputType() {
            return TypeTimestamp.strongType;
        }

        @Override
        public void configure(TypeTimestamp.now cfg) {
        }

        @Override
        public ThrowingFunction<Object, Timestamp> function() {
            return object -> new Timestamp(System.currentTimeMillis());
        }
    }

    public static class ReadCellValue implements AnnotablePipe<TypeTimestamp.cellValue, Cell, Timestamp> {
        private ThrowingFunction<Cell, Timestamp> function;

        @Override
        public StrongType getInputType() {
            return StrongType.of(Cell.class);
        }

        @Override
        public StrongType getOutputType() {
            return TypeTimestamp.strongType;
        }

        @Override
        public void configure(TypeTimestamp.cellValue cfg) {
            this.function = cell -> {
                if (cfg.nullSafe() && cell.getCellType() == CellType.BLANK) return null;
                return new Timestamp(cell.getDateCellValue()
                                         .getTime());
            };
            this.function = this.function.nullSafe(cfg.nullSafe());
        }

        @Override
        public ThrowingFunction<Cell, Timestamp> function() {
            return this.function;
        }
    }


    public static class WriteCellValue implements AnnotablePipe<TypeTimestamp.cellValue, Timestamp, ThrowingConsumer<Cell>> {
        private ThrowingFunction<Timestamp, ThrowingConsumer<Cell>> function;

        @Override
        public StrongType getInputType() {
            return TypeDate.strongType;
        }

        @Override
        public void configure(TypeTimestamp.cellValue cfg) {
            if (cfg.nullSafe())
                this.function = timestamp -> timestamp == null ? cell -> cell.setBlank() : cell -> cell.setCellValue(timestamp);
            else
                this.function = timestamp -> cell -> cell.setCellValue(timestamp);
        }

        @Override
        public ThrowingFunction<Timestamp, ThrowingConsumer<Cell>> function() {
            return this.function;
        }
    }
}
