package io.github.agache41.ormpipes.pipe.registry;

import io.github.agache41.ormpipes.config.Constants;
import io.github.agache41.ormpipes.functional.ThrowingConsumer;
import io.github.agache41.ormpipes.pipe.AnnotablePipe;
import io.github.agache41.ormpipes.pipes.encoding.IOEncoding;
import io.github.agache41.ormpipes.pipes.iostream.IOStream;
import io.github.agache41.ormpipes.pipes.typeFile.TypeFile;
import io.github.agache41.ormpipes.pipes.typeString.TypeString;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Disabled
@TypeFile.New
@TypeFile.mkdirs
@IOStream.FileBased
@IOEncoding.Automatic
@TypeString.IOStreamLines(separator = TypeString.IOStreamLines.CRLF)
@TypeString.Array(separator = ";")
@Execution(ExecutionMode.CONCURRENT)
public class PipeRegistryBaseTest {
    public static final String TestFile = "temporary/writeFile.txt";

    @Test
    @Order(0)
    public void testBuildWritePipe() throws Exception {
        AnnotablePipe<?, Stream<String[]>, ThrowingConsumer<String>> writePipe = PipeRegistry.buildPipeFrom(PipeRegistryBaseTest.class,
                                                                                                            Constants.DEFAULT,
                                                                                                            "write",
                                                                                                            false);
        assertNotNull(writePipe);
    }


    @Test
    @Order(1)
    public void testBuildReadPipe() {
        AnnotablePipe<?, String, Stream<String[]>> readPipe = PipeRegistry.buildPipeFrom(PipeRegistryBaseTest.class,
                                                                                         Constants.DEFAULT,
                                                                                         "read",
                                                                                         false);
        assertNotNull(readPipe);
    }

    @Test
    @Order(2)
    public void testWriteReadPipe() throws Throwable {
        AnnotablePipe<?, String, ThrowingConsumer<Stream<String[]>>> writePipe = PipeRegistry.buildPipeFrom(PipeRegistryBaseTest.class,
                                                                                                            Constants.DEFAULT,
                                                                                                            "write",
                                                                                                            false);
        assertNotNull(writePipe);
        List<String[]> writeData = new ArrayList<>();
        for (int index = 0; index < 100; index++)
            writeData.add(new String[]{"Field1", "Field2", "Field2", "" + index});
        writePipe.function()
                 .apply(TestFile)
                 .accept(writeData.stream());

        AnnotablePipe<?, String, Stream<String[]>> readPipe = PipeRegistry.buildPipeFrom(PipeRegistryBaseTest.class,
                                                                                         Constants.DEFAULT,
                                                                                         "read",
                                                                                         false);
        List<String[]> readData = readPipe.function()
                                          .apply(TestFile)
                                          .collect(Collectors.toList());
        assertEquals(writeData.size(),
                readData.size());
        assertEquals(writeData.stream()
                              .map(Stream::of)
                              .map(s -> s.collect(Collectors.joining(",")))
                              .collect(Collectors.joining(";")),
                readData.stream()
                        .map(Stream::of)
                        .map(s -> s.collect(Collectors.joining(",")))
                        .collect(Collectors.joining(";"))
        );

    }
}