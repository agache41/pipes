package io.github.agache41.ormpipes.pipes.encoding;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

@IOEncoding.Automatic
@Execution(ExecutionMode.CONCURRENT)
public class AUTOBeanTest extends BaseTestBean {

    public AUTOBeanTest() {
        super("decoding/read/AUTO.txt", "decoding/write/AUTO.txt", "dfVÄMpkdvjpwnbvkpwngvWNBGOIN    WKGBVNWKBGIOWERNHBGIO   W");
    }

    @Test
    @Execution(ExecutionMode.CONCURRENT)
    public void writeTest() throws Throwable {
        this.doWriteTest();
    }

    @Test
    @Execution(ExecutionMode.CONCURRENT)
    public void readTest() throws Throwable {
        this.doReadTest();
    }
}
