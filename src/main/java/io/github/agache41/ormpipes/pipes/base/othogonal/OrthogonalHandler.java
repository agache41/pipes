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

package io.github.agache41.ormpipes.pipes.base.othogonal;

import io.github.agache41.annotator.accessor.Accessor;
import io.github.agache41.annotator.annotator.Annotate;
import io.github.agache41.annotator.annotator.Annotator;
import io.github.agache41.annotator.matcher.HaveAnnotation;
import io.github.agache41.ormpipes.functional.ThrowingFunction;
import io.github.agache41.ormpipes.pipes.base.othogonal.enums.Model;
import io.github.agache41.ormpipes.pipes.base.othogonal.enums.NamingMethod;
import io.github.agache41.ormpipes.pipes.base.othogonal.enums.PositionMethod;
import io.github.agache41.ormpipes.pipes.csv.csvFile.CSVFile;
import org.jboss.logging.Logger;

import java.lang.annotation.Annotation;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <pre>
 * The type Orthogonal handler.
 * </pre>
 *
 * @param <ClassCfg>    the type parameter
 * @param <FieldCfg>    the type parameter
 * @param <Entry>       the type parameter
 * @param <InputRead>   the type parameter
 * @param <OutputRead>  the type parameter
 * @param <InputWrite>  the type parameter
 * @param <OutputWrite> the type parameter
 */
public abstract class OrthogonalHandler< //
        ClassCfg extends Annotation, //
        FieldCfg extends Annotation, //
        Entry extends OrthogonalEntry<FieldCfg, InputRead, OutputRead, InputWrite, OutputWrite>, //
        InputRead, //
        OutputRead, //
        InputWrite, //
        OutputWrite> //
{

    /**
     * <pre>
     * The constant logger.
     * </pre>
     */
    protected static final Logger logger = Logger.getLogger(CSVFile.class);
    private final Class<?> onClass;
    private final PositionMethod positionMethod;
    private final NamingMethod namingMethod;
    private final Model model;

    /**
     * <pre>
     * The Use first line as header.
     * </pre>
     */
    public boolean useFirstLineAsHeader;
    /**
     * <pre>
     * The Position index.
     * </pre>
     */
    public int[] positionIndex;
    /**
     * <pre>
     * The Max position.
     * </pre>
     */
    public int maxPosition = 0;
    /**
     * <pre>
     * The Line number.
     * </pre>
     */
    public int lineNumber = 0;
    /**
     * <pre>
     * The Read pipes.
     * </pre>
     */
    public ThrowingFunction<InputRead, OutputRead>[] readPipes;
    /**
     * <pre>
     * The Header.
     * </pre>
     */
    public String[] header;
    /**
     * <pre>
     * The Valid header.
     * </pre>
     */
    public boolean validHeader = false;
    /**
     * <pre>
     * The Write pipes.
     * </pre>
     */
    public ThrowingFunction<InputWrite, OutputWrite>[] writePipes;
    /**
     * <pre>
     * The Entry list.
     * </pre>
     */
    public List<Entry> entryList;
    /**
     * <pre>
     * The Valid positions.
     * </pre>
     */
    protected boolean validPositions = false;
    /**
     * <pre>
     * The Valid names.
     * </pre>
     */
    protected boolean validNames = false;
    /**
     * <pre>
     * The Valid pipes.
     * </pre>
     */
    protected boolean validPipes = false;

    /**
     * <pre>
     * Instantiates a new Orthogonal handler.
     * </pre>
     *
     * @param onClass       the on class
     * @param classCfg      the class cfg
     * @param fieldCfgClass the field cfg class
     */
    public OrthogonalHandler(Class<?> onClass,
                             ClassCfg classCfg,
                             Class<FieldCfg> fieldCfgClass) {
        this.onClass = onClass;
        Annotate<ClassCfg> cfgAnnotator = Annotator.of(classCfg);
        this.header = cfgAnnotator.getAccessor("header")
                                  .getAs(classCfg, String[].class);
        this.useFirstLineAsHeader = cfgAnnotator.getAccessor("useFirstLineAsHeader")
                                                .getAs(classCfg, Boolean.class);
        this.namingMethod = cfgAnnotator.getAccessor("namingMethod")
                                        .getAs(classCfg, NamingMethod.class);
        this.positionMethod = cfgAnnotator.getAccessor("positionMethod")
                                          .getAs(classCfg, PositionMethod.class);
        this.model = cfgAnnotator.getAccessor("model")
                                 .getAs(classCfg, Model.class);
        this.initialize(fieldCfgClass);
    }

    /**
     * <pre>
     * New entry entry.
     * </pre>
     *
     * @param accessor       the accessor
     * @param namingMethod   the naming method
     * @param positionMethod the position method
     * @return the entry
     */
    protected abstract Entry newEntry(Accessor<?> accessor,
                                      NamingMethod namingMethod,
                                      PositionMethod positionMethod);

    /**
     * <pre>
     * New entry entry.
     * </pre>
     *
     * @param name     the name
     * @param position the position
     * @return the entry
     */
    protected abstract Entry newEntry(String name,
                                      int position);


    private void initialize(Class<FieldCfg> fieldCfgClass) {
        this.entryList = Annotator.of(this.onClass)
                                  .getAccessorsThat(HaveAnnotation.ofType(fieldCfgClass))
                                  .sorted(Accessor.fieldPosition())
                                  .map((Accessor<?> accessor) -> this.newEntry(accessor, this.namingMethod, this.positionMethod))
                                  .collect(Collectors.toList());
        if (this.entryList.size() == 0) {
            throw new IllegalArgumentException("No " + fieldCfgClass.getSimpleName() + " annotations found!");
        }
        this.completeFieldPositions();
        this.validateNames();
        this.validatePositions();
        if (this.header.length != 0) {
            this.setHeader(this.header);
        } else {
            this.initializePipes();
            this.initializeHeader();
        }
    }

    //todo: add position ordering in Accessor
    private void completeFieldPositions() {
        if (this.positionMethod == PositionMethod.Fields) {
            for (int index = 0; index < this.entryList.size(); index++) {
                this.entryList.get(index)
                              .setPosition(index);
            }
        }
    }

    private void validateNames() {
        Set<String> names = new HashSet<>(this.entryList.size());
        for (Entry entry : this.entryList) {
            String name = entry.getName();
            if (name.isEmpty()) {
                if (this.model.hasValidNames()) {
                    throw new RuntimeException(" Found missing name (" + entry + "): please check you class!");
                } else {
                    return;
                }
            }
            if (names.contains(name)) {
                if (this.model.hasValidNames()) {
                    throw new RuntimeException(" Found duplicate " + name + " (" + entry + "): please check you class!");
                } else {
                    return;
                }
            }
            names.add(name);
        }
        names.clear();
        this.validNames = true;
    }

    private void validatePositions() {
        Set<Integer> positions = new HashSet<>();
        for (int index = 0; index < this.entryList.size(); index++) {
            Integer position = this.entryList.get(index)
                                             .getPosition();
            if (Accessor.NO_POSITION == position) {
                if (this.model.hasValidPositions()) {
                    throw new RuntimeException(" Found missing position (" + this.entryList.get(index)
                                                                                           .toString() + "): please check you class!");
                } else {
                    return;
                }
            }
            if (positions.contains(position)) {
                if (this.model.hasValidPositions()) {
                    throw new RuntimeException(" Found duplicate position (" + this.entryList.get(index)
                                                                                             .toString() + "): please check you class!");
                } else {
                    return;
                }
            }
            if (position < 0) {
                if (this.model.hasValidPositions()) {
                    throw new RuntimeException(" Found position out of range (" + this.entryList.get(index)
                                                                                                .toString() + "): please check you class!");
                } else {
                    return;
                }
            }
            positions.add(position);
        }
        positions.clear();
        this.validPositions = true;
    }

    /**
     * <pre>
     * Sets header.
     * </pre>
     *
     * @param header the header
     */
    public void setHeader(String[] header) {
        if (header.length == 0) {
            throw new RuntimeException(" Provided empty header!");
        }
        this.header = header;
        this.validHeader = true;
        this.model.verify(this.validNames, this.validPositions);
        if (this.model == Model.VariablePositions) {
            Map<String, Integer> headerMap = new HashMap<>();
            for (int index = 0; index < header.length; index++) {
                String name = header[index];
                if (headerMap.containsKey(name)) {
                    throw new RuntimeException(" Found duplicate name \"" + name + "\" in header");
                }
                headerMap.put(name, index);
            }
            for (Entry entry : this.entryList) {
                if (!entry.isEnabled()) {
                    continue;
                }
                String name = entry.getName();
                if (headerMap.containsKey(name)) {
                    int headerPosition = headerMap.get(name);
                    if (entry.getPosition() != headerPosition) {
                        // update positions according to header
                        entry.setPosition(headerPosition);
                    }
                } else {
                    // if the entry is not in the header, disable it.
                    entry.disable();
                }
                headerMap.remove(name);
            }
            //remaining entries
            headerMap.entrySet()
                     .stream()
                     .map(entry -> this.newEntry(entry.getKey(), entry.getValue()))
                     .forEach(this.entryList::add);
            this.validPositions = true;
        } else if (this.model == Model.VariableNames) {
            Map<Integer, String> headerMap = new HashMap<>();
            for (int index = 0; index < header.length; index++) {
                headerMap.put(index, header[index]);
            }
            for (Entry entry : this.entryList) {
                if (!entry.isEnabled()) {
                    continue;
                }
                int position = entry.getPosition();
                if (headerMap.containsKey(position)) {
                    String headerName = headerMap.get(position);
                    if (!entry.getName()
                              .equals(headerName)) {
                        entry.setName(headerName);
                    }
                } else { // the remaining entries will be disabled
                    entry.disable();
                }
                headerMap.remove(position);
            }
            //remaining entries
            headerMap.entrySet()
                     .stream()
                     .map(entry -> this.newEntry(entry.getValue(), entry.getKey()))
                     .forEach(this.entryList::add);
            this.validNames = true;
        } else if (this.model == Model.Fixed) {
            // Provider Header must match the actual header
            for (Entry entry : this.entryList) {
                if (entry.getPosition() >= header.length || !this.header[entry.getPosition()].equals(entry.getName())) {
                    throw new RuntimeException(" Model.Fixed:found different header in data source : found ("
                                               + Arrays.toString(this.header)
                                               + ") configured ("
                                               + this.entryList.stream()
                                                               .sorted(Comparator.comparing(Entry::getPosition))
                                                               .map(Entry::getName)
                                                               .collect(Collectors.joining(",", "[", "]")) + ")");
                }
            }
        }
        this.initializePipes();
        logger.debugf(this.toString());
        this.validate();
    }

    private void initializePipes() {
        if (!this.validPositions) {
            return;
        }
        this.entryList = this.entryList.stream()
                                       .filter(OrthogonalEntry::isEnabled)
                                       .sorted(Comparator.comparing(OrthogonalEntry::getPosition))
                                       .collect(Collectors.toList());
        int count = this.entryList.size();
        this.positionIndex = new int[count];
        this.writePipes = new ThrowingFunction[count];
        this.readPipes = new ThrowingFunction[count];
        int index = 0;
        this.maxPosition = 0;
        for (Entry entry : this.entryList) {
            this.positionIndex[index] = entry.getPosition();
            this.readPipes[index] = entry.getReadPipe()
                                         .function();
            this.writePipes[index] = entry.getWritePipe()
                                          .function();
            if (this.maxPosition < entry.getPosition()) {
                this.maxPosition = entry.getPosition();
            }

            index++;
        }
        this.validPipes = true;
    }

    private void initializeHeader() {
        if (!this.validHeader && this.validPositions && this.validNames) {
            this.header = new String[this.entryList.size()];
            for (Entry entry : this.entryList) {
                this.header[entry.getPosition()] = entry.getName();
            }
            this.validHeader = true;
        }
    }

    /**
     * <pre>
     * Validate.
     * </pre>
     */
    public void validate() {
        if (!this.validPipes) {
            this.model.verify(this.validNames, this.validPositions);
        }
    }

    /**
     * <pre>
     * Validate line.
     * </pre>
     *
     * @param columnCount the column count
     */
    public void validateLine(int columnCount) {
        if (this.maxPosition >= columnCount) {
            throw new RuntimeException("Invalid too short line " + this.lineNumber + " has only " + columnCount + " column(s)!");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("[Naming.");
        sb.append(this.namingMethod);
        sb.append(".");
        if (!this.validNames) {
            sb.append("#in");
        }
        sb.append("valid/Positioning.");
        sb.append(this.positionMethod);
        sb.append(".");
        if (!this.validPositions) {
            sb.append("#in");
        }
        sb.append("valid.");
        sb.append(Arrays.toString(this.positionIndex));
        sb.append("/Model.");
        sb.append(this.model);
        sb.append("/header=");
        sb.append(Arrays.toString(this.header));
        sb.append("/columns=");
        sb.append(this.entryList.stream()
                                .sorted(Comparator.comparing(OrthogonalEntry::getPosition))
                                .map(OrthogonalEntry::toString)
                                .collect(Collectors.joining("|")));
        return sb.toString();
    }
}
