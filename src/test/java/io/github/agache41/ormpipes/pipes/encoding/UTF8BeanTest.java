/*
 *    Copyright 2022-2023  Alexandru Agache
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

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
