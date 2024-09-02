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

package io.github.agache41.ormpipes.pipes.spreadSheet.orthogonal.fixed.SSHeaderSSHeader;

import io.github.agache41.ormpipes.pipes.base.othogonal.enums.Model;
import io.github.agache41.ormpipes.pipes.baseTest.values.BaseExcelTestBean;
import io.github.agache41.ormpipes.pipes.iostream.IOStream;
import io.github.agache41.ormpipes.pipes.spreadSheet.SpreadSheet;
import io.github.agache41.ormpipes.pipes.typeFile.TypeFile;

@TypeFile.NewResource
@IOStream.FileBased
@SpreadSheet.xls
@SpreadSheet.select(sheetName = "ExcelTestBean")
@SpreadSheet.sheet(model = Model.Fixed, disableLineValidation = true)
public class SSHeaderSSHeader extends BaseExcelTestBean {
}

