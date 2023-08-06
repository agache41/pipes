package com.orm.pipe.registry;

import com.orm.annotations.Annotations;
import com.orm.functional.ThrowingConsumer;
import com.orm.pipe.AnnotablePipe;
import com.orm.pipes.encoding.IOEncoding;
import com.orm.pipes.iostream.IOStream;
import com.orm.pipes.typeFile.TypeFile;
import com.orm.pipes.typeString.TypeString;
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
                Annotations.DEFAULT,
                "read",
                false);
        assertNotNull(readPipe);
    }

    @Test
    public void testBuildWritePipe() throws Exception {
        AnnotablePipe<?, String, ThrowingConsumer<Stream<?>>> writePipe = PipeRegistry.buildPipeFrom(PipeRegistryCSVTest.class,
                Annotations.DEFAULT,
                "write",
                true);
        assertNotNull(writePipe);
    }

    @Test
    public void testWriteReadPipe() throws Throwable {
        AnnotablePipe<?, Stream<String>, ThrowingConsumer<String>> writePipe = PipeRegistry.buildPipeFrom(PipeRegistryCSVTest.class,
                Annotations.DEFAULT,
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
                Annotations.DEFAULT,
                "read",
                false);
        List<String> readData = readPipe.function()
                                        .apply(TestFile)
                                        .collect(Collectors.toList());
        assertEquals(writeData,
                readData);
    }

}