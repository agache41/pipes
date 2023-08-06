package com.orm.pipe.registry;

import com.orm.annotations.Extends;
import com.orm.pipe.AnnotablePipe;
import com.orm.pipe.DualPipe;
import com.orm.reflection.annotator.Annotator;
import com.orm.reflection.annotator.base.Annotate;
import com.orm.reflection.matcher.AnExtendsValue;
import com.orm.reflection.matcher.HaveAnnotation;
import com.orm.reflection.predicate.AreInDefaultOrInView;
import com.orm.reflection.registry.Key;
import com.orm.reflection.registry.Registry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collector;
import java.util.stream.Stream;

public class PipeRegistry {
    private static final Logger logger = LogManager.getLogger(PipeRegistry.class);

    private static final Map<Key, AnnotablePipe<?, ?, ?>> cache = new HashMap<>();

    public static <I, O> AnnotablePipe<?, I, O> getCachedPipeFrom(Object varInput, String view, String method, boolean reversed) {
        Key key = new Key(varInput, view, method, reversed);
        // check cache
        AnnotablePipe<?, I, O> pipe = (AnnotablePipe<?, I, O>) cache.get(key);
        if (pipe == null) {
            pipe = buildPipeFrom(varInput, view, method, reversed);
            cache.put(key, pipe);
        }
        return pipe;
    }

    public static <I, O> AnnotablePipe<?, I, O> buildPipeFrom(Object varInput, String view, String method, boolean reversed) {
        Annotate<Object> annotator = Annotator.of(varInput);
        AnnotablePipe<?, I, O> pipe = (AnnotablePipe<?, I, O>) reverse(annotator.getAnnotationsThat(HaveAnnotation.ofType(Extends.class)
                                                                                                                  .having(AnExtendsValue.of(DualPipe.class)))
                                                                                .filter(AreInDefaultOrInView.of(view)), reversed).map(annot -> Registry.createAndConfigureFromMethod(AnnotablePipe.class, annot, method, varInput))
                                                                                                                                 .filter(Objects::nonNull)
                                                                                                                                 .reduce(AnnotablePipe::compose)
                                                                                                                                 .orElseThrow(() -> new RuntimeException("No Annotations Found"));
        logger.info("Pipe {}:{}:{}:{}", annotator, view, method, pipe.getDescription());
        logger.debug("Pipe {}:{}:{}:{}", annotator, view, method, pipe.getDetailedDescription());
        return pipe;
    }

    //todo: move to utils
    public static <T> Stream<T> reverse(Stream<T> stream, boolean reverse) {
        if (!reverse) return stream;
        return stream.collect(Collector.of(() -> new LinkedList(), LinkedList::addFirst, (a, b) -> a))
                     .stream();
    }
}
