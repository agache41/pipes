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

package io.github.agache41.ormpipes.pipes.baseTest.values;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface TestBean extends Comparable<TestBean> {

    String getString0();

    void setString0(String string0);

    Integer getInteger1();

    void setInteger1(Integer integer1);

    Long getLong2();

    void setLong2(Long long2);

    Double getDouble3();

    void setDouble3(Double double3);

    Float getFloat4();

    void setFloat4(Float float4);

    LocalDate getLocaldate5();

    void setLocaldate5(LocalDate localdate5);

    Date getDate6();

    void setDate6(Date date);

    List<String> getList7();

    void setList7(List<String> list7);

    int hashCode();

    /**
* {@inheritDoc}
*/
@Override
    default int compareTo(TestBean o) {
        return this.hashCode() - o.hashCode();
    }
}
