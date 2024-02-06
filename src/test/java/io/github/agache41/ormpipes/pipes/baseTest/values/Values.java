package io.github.agache41.ormpipes.pipes.baseTest.values;

import java.util.List;

public interface Values<T extends TestBean> {
    List<T> getValues();

    List<T> getLessValues();
}
