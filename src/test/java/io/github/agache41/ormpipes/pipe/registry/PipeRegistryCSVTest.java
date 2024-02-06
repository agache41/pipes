package io.github.agache41.ormpipes.pipe.registry;

import io.github.agache41.ormpipes.config.Constants;
import io.github.agache41.ormpipes.functional.ThrowingConsumer;
import io.github.agache41.ormpipes.pipe.AnnotablePipe;
import io.github.agache41.ormpipes.pipes.encoding.IOEncoding;
import io.github.agache41.ormpipes.pipes.iostream.IOStream;
import io.github.agache41.ormpipes.pipes.typeFile.TypeFile;
import io.github.agache41.ormpipes.pipes.typeString.TypeString;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@TypeFile.NewResource
@IOStream.FileBased
@IOEncoding.Automatic()
@TypeString.IOStreamLines(separator = TypeString.IOStreamLines.CR)
@Execution(ExecutionMode.CONCURRENT)
public class PipeRegistryCSVTest {

    public static final String TestFile = "writeFile.txt";

    @Test
    public void testBuildReadPipe() {
        AnnotablePipe<?, String, Stream<?>> readPipe = PipeRegistry.buildPipeFrom(PipeRegistryCSVTest.class,
                                                                                  Constants.DEFAULT,
                                                                                  "read",
                                                                                  false);
        assertNotNull(readPipe);
    }

    @Test
    public void testBuildWritePipe() throws Exception {
        AnnotablePipe<?, String, ThrowingConsumer<Stream<?>>> writePipe = PipeRegistry.buildPipeFrom(PipeRegistryCSVTest.class,
                                                                                                     Constants.DEFAULT,
                                                                                                     "write",
                                                                                                     true);
        assertNotNull(writePipe);
    }

    @Test
    public void testWriteReadPipe() throws Throwable {
        AnnotablePipe<?, Stream<String>, ThrowingConsumer<String>> writePipe = PipeRegistry.buildPipeFrom(PipeRegistryCSVTest.class,
                                                                                                          Constants.DEFAULT,
                                                                                                          "write",
                                                                                                          true);
        assertNotNull(writePipe);
        List<String> writeData = new ArrayList<>();
        for (int index = 0; index < 100; index++)
            writeData.add("Line" + index);
        writePipe.function()
                 .apply(writeData.stream())
                 .accept(TestFile);

        AnnotablePipe<?, String, Stream<String>> readPipe = PipeRegistry.buildPipeFrom(PipeRegistryCSVTest.class,
                                                                                       Constants.DEFAULT,
                                                                                       "read",
                                                                                       false);
        List<String> readData = readPipe.function()
                                        .apply(TestFile)
                                        .collect(Collectors.toList());
        assertEquals(writeData,
                readData);
    }

}