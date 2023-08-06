package com.orm.pipes.temporal.typeLocalTime;

import com.orm.functional.StrongType;
import com.orm.functional.ThrowingConsumer;
import com.orm.functional.ThrowingFunction;
import com.orm.pipe.AnnotablePipe;
import com.orm.pipes.base.format.AbstractFormat;
import com.orm.pipes.base.format.AbstractParse;
import com.orm.pipes.typeObject.TypeObject;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class LocalTimePipes {
    public static class LocalTimeToString extends AbstractFormat<TypeLocalTime.New, LocalTime> implements AnnotablePipe<TypeLocalTime.New, LocalTime, String> {

        @Override
        public StrongType getInputType() {
            return TypeLocalTime.strongType;
        }

        @Override
        public void configure(TypeLocalTime.New cfg) {
            super.configure(cfg);
            if (this.simple) return;
            final DateTimeFormatter formatter = this.getDateTimeFormatter(cfg.format(), cfg.languageTag(), cfg.zoneId());
            this.function = formatter::format;
        }
    }

    public static class ParseLocalTime extends AbstractParse<TypeLocalTime.New, LocalTime> implements AnnotablePipe<TypeLocalTime.New, String, LocalTime> {

        @Override
        public StrongType getOutputType() {
            return TypeLocalTime.strongType;
        }

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

    public static class Now implements AnnotablePipe<TypeLocalTime.now, Object, LocalTime> {
        @Override
        public StrongType getInputType() {
            return TypeObject.strongType;
        }

        @Override
        public StrongType getOutputType() {
            return TypeLocalTime.strongType;
        }

        @Override
        public void configure(TypeLocalTime.now cfg) {
        }

        @Override
        public ThrowingFunction<Object, LocalTime> function() {
            return object -> LocalTime.now();
        }
    }

    public static class ReadCellValue implements AnnotablePipe<TypeLocalTime.cellValue, Cell, LocalTime> {
        private ThrowingFunction<Cell, LocalTime> function;

        @Override
        public StrongType getInputType() {
            return StrongType.of(Cell.class);
        }

        @Override
        public StrongType getOutputType() {
            return TypeLocalTime.strongType;
        }

        @Override
        public void configure(TypeLocalTime.cellValue cfg) {
            this.function = cell -> {
                if (cfg.nullSafe() && cell.getCellType() == CellType.BLANK) return null;
                return cell.getLocalDateTimeCellValue()
                           .toLocalTime();
            };
            this.function = this.function.nullSafe(cfg.nullSafe());
        }

        @Override
        public ThrowingFunction<Cell, LocalTime> function() {
            return this.function;
        }
    }


    public static class WriteCellValue implements AnnotablePipe<TypeLocalTime.cellValue, LocalTime, ThrowingConsumer<Cell>> {
        private final DateTimeFormatter excelTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        private ThrowingFunction<LocalTime, ThrowingConsumer<Cell>> function;

        @Override
        public StrongType getInputType() {
            return TypeLocalTime.strongType;
        }

        @Override
        public void configure(TypeLocalTime.cellValue cfg) {
            if (cfg.nullSafe())
                this.function = localTime -> localTime == null ? cell -> cell.setBlank() : cell -> cell.setCellValue(this.toExcelTime(localTime));
            else
                this.function = localTime -> cell -> cell.setCellValue(this.toExcelTime(localTime));
        }

        private double toExcelTime(LocalTime localTime) {
            return DateUtil.convertTime(this.excelTimeFormatter.format(localTime));
        }

        @Override
        public ThrowingFunction<LocalTime, ThrowingConsumer<Cell>> function() {
            return this.function;
        }
    }
}
