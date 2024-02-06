package io.github.agache41.ormpipes.vminfo;

import java.util.stream.Stream;

public interface VMInfo {
    String getVmVersion();

    Stream<String> getLanguageTags();

    int countLanguageTags();

}
