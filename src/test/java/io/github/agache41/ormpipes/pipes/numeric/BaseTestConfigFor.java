package io.github.agache41.ormpipes.pipes.numeric;

import io.github.agache41.ormpipes.config.Annotations;
import io.github.agache41.ormpipes.pipes.csv.csvField.CSVAccessor;
import io.github.agache41.ormpipes.pipes.csv.csvFile.CSVFile;
import io.github.agache41.ormpipes.pipes.encoding.EncodingPipes;
import io.github.agache41.ormpipes.pipes.encoding.IOEncoding;
import io.github.agache41.ormpipes.pipes.iostream.IOStream;
import io.github.agache41.ormpipes.pipes.numeric.typeBoolean.TypeBoolean;
import io.github.agache41.ormpipes.pipes.typeFile.TypeFile;
import io.github.agache41.ormpipes.pipes.typeString.TypeString;
import io.github.agache41.ormpipes.pipes.zip.zipArchive.Zip;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.aggregator.ArgumentsAggregationException;
import org.junit.jupiter.params.aggregator.ArgumentsAggregator;

import java.lang.annotation.Annotation;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.fail;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TypeFile.NewResource
@TypeFile.mkdirs
@IOStream.FileBased
@Zip.Archive(view = "zip")
@Zip.Entry(view = "zip", fileName = "data.csv")
@IOEncoding.BOM(EncodingPipes.BOMCharset.UTF_8)
@TypeString.IOStreamLines
@TypeString.Array(separator = ";")
@CSVFile
public abstract class BaseTestConfigFor<T extends BaseTestConfigFor> implements Annotation, ArgumentsAggregator, Supplier<T> {

    public static final String NULL_KEY = "[null]";
    public static final String EMPTY_ARRAY = "{}";

    @TypeString.quoted
    @TypeString.nullable(NULL_KEY)
    @CSVAccessor(name = "input", position = 0)
    private String input;
    @TypeString.quoted
    @TypeString.nullable(NULL_KEY)
    @CSVAccessor(name = "output", position = 1)
    private String output;
    @TypeString.quoted
    @CSVAccessor(name = "exception", position = 2)
    private String exception;
    @TypeString.quoted
    @TypeString.nullable(NULL_KEY)
    @CSVAccessor(name = "exceptionMessage", position = 3)
    private String exceptionMessage;
    @TypeBoolean.New
    @CSVAccessor(name = "simple", position = 4)
    private Boolean simple;
    @TypeBoolean.New
    @CSVAccessor(name = "nullSafe", position = 5)
    private Boolean nullSafe;
    @TypeBoolean.New
    @CSVAccessor(name = "blankSafe", position = 6)
    private Boolean blankSafe;
    @TypeBoolean.New
    @CSVAccessor(name = "noException", position = 7)
    private Boolean noException;

    protected String nullable(String input) {
        if (NULL_KEY.equalsIgnoreCase(input))
            return null;
        return input;
    }

    public String[] splitNullable(String input) {
        if (NULL_KEY.equalsIgnoreCase(input))
            return null;
        if (EMPTY_ARRAY.equals(input))
            return new String[]{};
        return input.split(",");
    }

    @Override
    public Object aggregateArguments(ArgumentsAccessor argumentsAccessor, ParameterContext parameterContext) throws ArgumentsAggregationException {
        T result = this.get();
        result.setInput(argumentsAccessor.getString(0));
        result.setOutput(argumentsAccessor.getString(1));
        result.setException(argumentsAccessor.getString(2));
        result.setExceptionMessage(argumentsAccessor.getString(3));
        result.setSimple(argumentsAccessor.getBoolean(4));
        result.setNullSafe(argumentsAccessor.getBoolean(5));
        result.setBlankSafe(argumentsAccessor.getBoolean(6));
        result.setNoException(argumentsAccessor.getBoolean(7));
        return result;
    }

    public String input() {
        return this.nullable(this.input);
    }

    public String output() {
        return this.nullable(this.output);
    }

    public boolean failed() {
        return !this.exception.isEmpty();
    }

    public Class<?> exception() {
        if (this.exception.isEmpty()) {
            return null;
        }
        try {
            return this.getClass()
                       .getClassLoader()
                       .loadClass(this.exception);
        } catch (ClassNotFoundException e) {
            fail(e);
            return null;
        }
    }

    public boolean simple() {
        return this.simple;
    }

    public boolean nullSafe() {
        return this.nullSafe;
    }

    public boolean blankSafe() {
        return this.blankSafe;
    }

    public boolean noException() {
        return this.noException;
    }

    public String[] enabledOn() {
        return new String[]{"read", "write"};
    }

    public String view() {
        return Annotations.DEFAULT;
    }
}
