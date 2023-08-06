package com.orm.pipes.csv.csvFile;

import com.orm.functional.StrongType;
import com.orm.functional.ThrowingFunction;
import com.orm.pipe.AnnotablePipe;
import com.orm.pipes.csv.csvFile.impl.CSVFileBase;
import com.orm.pipes.csv.csvFile.impl.CSVFileHandler;

import java.util.Objects;
import java.util.stream.Stream;

public class CSVFileReader extends CSVFileBase implements AnnotablePipe<CSVFile, Stream<String[]>, Stream<?>> {


    @Override
    public StrongType getInputType() {
        return StrongType.of(Stream.class)
                         .parameterizedWith(String[].class);

    }

    @Override
    public StrongType getOutputType() {
        return StrongType.of(Stream.class)
                         .parameterizedWith("?");
    }


    @Override
    public ThrowingFunction<Stream<String[]>, Stream<?>> function() {
        return inputStream -> {
            CSVFileHandler handler = this.handler();
            return inputStream.skip(this.skipFirstNLines)
                              .map(ThrowingFunction.wrap(line -> this.doLine(handler, line)))
                              .filter(Objects::nonNull)
                              .sequential();
        };
    }

    private Object doLine(CSVFileHandler handler, String[] line) throws Throwable {
        handler.lineNumber++;
        if (handler.useFirstLineAsHeader) {
            handler.setHeader(line);
            handler.useFirstLineAsHeader = false;
            return null;
        }
        handler.validateLine(line.length);
        Object result = this.get();
        for (int index = 0; index < handler.positionIndex.length; index++) {
            handler.readPipes[index]
                    .apply(line[handler.positionIndex[index]])
                    .accept(result);
        }
        return result;
    }
}
