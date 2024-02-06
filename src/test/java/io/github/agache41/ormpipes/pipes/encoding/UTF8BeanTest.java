package io.github.agache41.ormpipes.pipes.encoding;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

@IOEncoding.Charset("UTF-8")
@Execution(ExecutionMode.CONCURRENT)
public class UTF8BeanTest extends BaseTestBean {
    public UTF8BeanTest() {
        super("decoding/read/UTF-8.txt", "decoding/write/UTF-8.txt", "dfVÄMpkdvjpwnbvkpwngvWNBGOIN    WKGBVNWKBGIOWERNHBGIO   W");
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
