package com.orm.vminfo;

import java.util.stream.Stream;

public interface VMInfo {
    String getVmVersion();

    Stream<String> getLanguageTags();

    int countLanguageTags();

}
