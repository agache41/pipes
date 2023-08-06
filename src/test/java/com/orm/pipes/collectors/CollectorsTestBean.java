package com.orm.pipes.collectors;

import com.orm.pipes.base.othogonal.enums.PositionMethod;
import com.orm.pipes.baseTest.values.BaseCSVTestBean;
import com.orm.pipes.csv.csvFile.CSVFile;
import com.orm.pipes.encoding.IOEncoding;
import com.orm.pipes.iostream.IOStream;
import com.orm.pipes.typeFile.TypeFile;
import com.orm.pipes.typeString.TypeString;

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
