package com.orm.pipes.csv.csvFile;

import com.orm.functional.StrongType;
import com.orm.functional.ThrowingFunction;
import com.orm.pipe.AnnotablePipe;
import com.orm.pipes.csv.csvFile.impl.CSVFileBase;
import com.orm.pipes.csv.csvFile.impl.CSVFileHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class CSVFileWriter extends CSVFileBase implements AnnotablePipe<CSVFile, Stream<?>, Stream<String[]>> {

    @Override
    public StrongType getInputType() {
        return StrongType.of(Stream.class)
                         .parameterizedWith("?");
    }

    @Override
    public StrongType getOutputType() {
        return StrongType.of(Stream.class)
                         .parameterizedWith(String[].class);
    }

    @Override
    public ThrowingFunction<Stream<?>, Stream<String[]>> function() {
        return input -> {
            CSVFileHandler handler = this.handler();
            handler.validate();
            if (handler.useFirstLineAsHeader) {
                if (handler.validHeader) {
                    List<String[]> headerList = new ArrayList<>();
                    headerList.add(handler.header);
                    return Stream.concat(headerList.stream(),
                            input.map(ThrowingFunction.wrap(line -> this.doLine(handler, line))));
                } else throw new RuntimeException(" Header is not available for writing the first line !" + handler);
            }
            return input.map(ThrowingFunction.wrap(line -> this.doLine(handler, line)));
        };
    }

    private String[] doLine(CSVFileHandler handler, Object input) throws Throwable {
        String[] result = new String[handler.positionIndex.length];
        for (int index = 0; index < handler.positionIndex.length; index++) {
            result[handler.positionIndex[index]] = handler.writePipes[index].apply(input);
        }
        return result;
    }
}
