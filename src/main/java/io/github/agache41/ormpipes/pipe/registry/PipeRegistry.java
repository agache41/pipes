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

package io.github.agache41.ormpipes.pipe.registry;

import io.github.agache41.annotator.annotations.Extends;
import io.github.agache41.annotator.annotator.Annotate;
import io.github.agache41.annotator.annotator.Annotator;
import io.github.agache41.annotator.matcher.AnExtendsValue;
import io.github.agache41.annotator.matcher.HaveAnnotation;
import io.github.agache41.annotator.predicate.AreInDefaultOrInView;
import io.github.agache41.ormpipes.pipe.AnnotablePipe;
import io.github.agache41.ormpipes.pipe.DualPipe;
import org.jboss.logging.Logger;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collector;
import java.util.stream.Stream;

public class PipeRegistry {
    private static final Logger logger = Logger.getLogger(PipeRegistry.class);

    private static final Map<Key, AnnotablePipe<?, ?, ?>> cache = new HashMap<>();

    public static <I, O> AnnotablePipe<?, I, O> getCachedPipeFrom(Object varInput,
                                                                  String view,
                                                                  String method,
                                                                  boolean reversed) {
        Key key = new Key(varInput, view, method, reversed);
        // check cache
        AnnotablePipe<?, I, O> pipe = (AnnotablePipe<?, I, O>) cache.get(key);
        if (pipe == null) {
            pipe = buildPipeFrom(varInput, view, method, reversed);
            cache.put(key, pipe);
        }
        return pipe;
    }

    public static <I, O> AnnotablePipe<?, I, O> buildPipeFrom(Object varInput,
                                                              String view,
                                                              String method,
                                                              boolean reversed) {
        Annotate<Object> annotator = Annotator.of(varInput);
        AnnotablePipe<?, I, O> pipe = (AnnotablePipe<?, I, O>) reverse(annotator.getAnnotationsThat(HaveAnnotation.ofType(Extends.class)
                                                                                                                  .having(AnExtendsValue.of(DualPipe.class)))
                                                                                .filter(AreInDefaultOrInView.of(view)), reversed).map(annot -> Registry.createAndConfigureFromMethod(AnnotablePipe.class, annot, method, varInput))
                                                                                                                                 .filter(Objects::nonNull)
                                                                                                                                 .reduce(AnnotablePipe::compose)
                                                                                                                                 .orElseThrow(() -> new RuntimeException("No Annotations Found"));
        logger.debugf("Pipe %s:%s:%s:%s", annotator, view, method, pipe.getDescription());
        logger.tracef("Pipe %s:%s:%s:%s", annotator, view, method, pipe.getDetailedDescription());
        return pipe;
    }

    //todo: move to utils
    public static <T> Stream<T> reverse(Stream<T> stream,
                                        boolean reverse) {
        if (!reverse) {
            return stream;
        }
        return stream.collect(Collector.of(() -> new LinkedList(), LinkedList::addFirst, (a, b) -> a))
                     .stream();
    }
}
