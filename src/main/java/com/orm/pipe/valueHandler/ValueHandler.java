package com.orm.pipe.valueHandler;

public interface ValueHandler {

    <Output> Output handleValue(Class<Output> outputClass,
                                Object inputValue);
}
