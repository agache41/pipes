package io.github.agache41.ormpipes.pipes.collectors;

import io.github.agache41.ormpipes.pipes.base.othogonal.enums.PositionMethod;
import io.github.agache41.ormpipes.pipes.baseTest.values.BaseCSVTestBean;
import io.github.agache41.ormpipes.pipes.csv.csvFile.CSVFile;
import io.github.agache41.ormpipes.pipes.encoding.IOEncoding;
import io.github.agache41.ormpipes.pipes.iostream.IOStream;
import io.github.agache41.ormpipes.pipes.typeFile.TypeFile;
import io.github.agache41.ormpipes.pipes.typeString.TypeString;

@TypeFile.NewResource
@IOStream.FileBased
@IOEncoding.Automatic
@TypeString.IOStreamLines(separator = TypeString.IOStreamLines.CR)
@TypeString.Array(separator = ";")
@CSVFile(positionMethod = PositionMethod.Fields)
@Collectors.toList(view = "list")
@Collectors.toLinkedList(view = "linkedList")
@Collectors.toSet(view = "set")
@Collectors.toTreeSet(view = "treeSet")
public class CollectorsTestBean extends BaseCSVTestBean {

}
