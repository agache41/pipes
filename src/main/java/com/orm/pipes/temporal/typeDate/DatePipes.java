package com.orm.pipes.temporal.typeDate;

import com.orm.functional.StrongType;
import com.orm.functional.ThrowingConsumer;
import com.orm.functional.ThrowingFunction;
import com.orm.pipe.AnnotablePipe;
import com.orm.pipes.base.format.AbstractFormat;
import com.orm.pipes.base.format.AbstractParse;
import com.orm.pipes.typeObject.TypeObject;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DatePipes {
    public static class DateToString extends AbstractFormat<TypeDate.New, Date> implements AnnotablePipe<TypeDate.New, Date, String> {

        @Override
        public StrongType getInputType() {
            return TypeDate.strongType;
        }

        @Override
        public void configure(TypeDate.New cfg) {
            super.configure(cfg);
            if (this.simple) return;
            final SimpleDateFormat simpleDateFormat = this.getSimpleDateFormat(cfg.format(), cfg.languageTag());
            this.function = simpleDateFormat::format;
        }
    }

    public static class ParseDate extends AbstractParse<TypeDate.New, Date> implements AnnotablePipe<TypeDate.New, String, Date> {

        @Override
        public StrongType getOutputType() {
            return TypeDate.strongType;
        }

        @Override
        public void configure(TypeDate.New cfg) {
            super.configure(cfg);
            if (this.simple) {
                this.function = Date::new;
                return;
            }
            final SimpleDateFormat simpleDateFormat = this.getSimpleDateFormat(cfg.format(), cfg.languageTag());
            this.function = simpleDateFormat::parse;
        }
    }

    public static class Now implements AnnotablePipe<TypeDate.now, Object, Date> {
        @Override
        public StrongType getInputType() {
            return TypeObject.strongType;
        }

        @Override
        public StrongType getOutputType() {
            return TypeDate.strongType;
        }

        @Override
        public void configure(TypeDate.now cfg) {
        }

        @Override
        public ThrowingFunction<Object, Date> function() {
            return object -> new Date();
        }
    }

    public static class ReadCellValue implements AnnotablePipe<TypeDate.cellValue, Cell, Date> {
        private ThrowingFunction<Cell, Date> function;

        @Override
        public StrongType getInputType() {
            return StrongType.of(Cell.class);
        }

        @Override
        public StrongType getOutputType() {
            return TypeDate.strongType;
        }

        @Override
        public void configure(TypeDate.cellValue cfg) {
            this.function = cell -> {
                if (cfg.nullSafe() && cell.getCellType() == CellType.BLANK) return null;
                return cell.getDateCellValue();
            };
            this.function = this.function.nullSafe(cfg.nullSafe());
        }

        @Override
        public ThrowingFunction<Cell, Date> function() {
            return this.function;
        }
    }


    public static class WriteCellValue implements AnnotablePipe<TypeDate.cellValue, Date, ThrowingConsumer<Cell>> {
        private ThrowingFunction<Date, ThrowingConsumer<Cell>> function;

        @Override
        public StrongType getInputType() {
            return TypeDate.strongType;
        }

        @Override
        public void configure(TypeDate.cellValue cfg) {
            if (cfg.nullSafe())
                this.function = date -> date == null ? cell -> cell.setBlank() : cell -> cell.setCellValue(date);
            else
                this.function = date -> cell -> cell.setCellValue(date);
        }

        @Override
        public ThrowingFunction<Date, ThrowingConsumer<Cell>> function() {
            return this.function;
        }
    }
}
