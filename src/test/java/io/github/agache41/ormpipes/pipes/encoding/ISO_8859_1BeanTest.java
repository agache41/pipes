package io.github.agache41.ormpipes.pipes.encoding;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;


@IOEncoding.Charset("ISO-8859-1")
@Execution(ExecutionMode.CONCURRENT)
public class ISO_8859_1BeanTest extends BaseTestBean {
    public ISO_8859_1BeanTest() {
        super("decoding/read/ISO-8859-1.txt", "decoding/write/ISO-8859-1.txt", "dfVÄMpkdvjpwnbvkpwngvWNBGOIN    WKGBVNWKBGIOWERNHBGIO   W");
    }

    @Test
    @Execution(ExecutionMode.CONCURRENT)
    public void readTest() throws Throwable {
        this.doReadTest();
    }

    @Test
    @Execution(ExecutionMode.CONCURRENT)
    public void writeTest() throws Throwable {
        this.doWriteTest();
    }
}
