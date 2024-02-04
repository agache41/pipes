package io.github.agache41.ormpipes.pipes.base.othogonal;

import io.github.agache41.annotator.annotator.Annotate;
import io.github.agache41.ormpipes.config.Annotations;
import io.github.agache41.ormpipes.functional.ThrowingFunction;
import io.github.agache41.ormpipes.pipe.AnnotablePipe;
import io.github.agache41.ormpipes.pipe.registry.PipeRegistry;
import io.github.agache41.ormpipes.pipes.base.othogonal.enums.NamingMethod;
import io.github.agache41.ormpipes.pipes.base.othogonal.enums.PositionMethod;
import io.github.agache41.annotator.accessor.Accessor;
import io.github.agache41.annotator.annotator.Annotator;


import java.lang.annotation.Annotation;

public class OrthogonalEntry<A extends Annotation, InputRead, OutputRead, InputWrite, OutputWrite> {
    protected final A cfg;
    private final boolean required;
    protected AnnotablePipe<? extends Annotation, InputRead, OutputRead> readPipe;
    protected AnnotablePipe<? extends Annotation, InputWrite, OutputWrite> writePipe;

    private String name;
    private int position;
    private boolean enabled = true;

    public OrthogonalEntry(String name, int position) {
        this.cfg = null;
        this.name = name;
        this.position = position;
        this.required = false;
        this.readPipe = new AnnotablePipe<Annotation, InputRead, OutputRead>() {
            @Override
            public ThrowingFunction<InputRead, OutputRead> function() {
                return input -> null;
            }

            @Override
            public void configure(Annotation cfg) {
            }
        };

        this.writePipe = new AnnotablePipe<Annotation, InputWrite, OutputWrite>() {
            @Override
            public ThrowingFunction<InputWrite, OutputWrite> function() {
                return input -> null;
            }

            @Override
            public void configure(Annotation cfg) {
            }
        };
    }

    public OrthogonalEntry(Class<A> cfgClass, Accessor<?> accessor, NamingMethod namingMethod, PositionMethod positionMethod) {
        this.cfg = accessor.getAnnotation(cfgClass, false);
        if (this.cfg == null) {
            throw new IllegalArgumentException("Can not process field " + accessor.getName() + " without the " + cfgClass.getSimpleName() + " Annotation!");
        }
        Annotate<Annotation> cfgAnnotator = Annotator.of(this.cfg);
        this.required = cfgAnnotator.getAccessor("required")
                                    .getAs(this.cfg, Boolean.class);
        this.name = namingMethod.apply(cfgAnnotator.getAccessor("name")
                                                   .getAs(this.cfg, String.class), accessor.getName());
        this.position = positionMethod.apply(cfgAnnotator.getAccessor("position")
                                                         .getAs(this.cfg, Integer.class), accessor.getPosition());
        try {
            this.readPipe = PipeRegistry.buildPipeFrom(accessor, Annotations.DEFAULT, "read", false);
            this.writePipe = PipeRegistry.buildPipeFrom(accessor, Annotations.DEFAULT, "write", true);
        } catch (Throwable cause) {
            throw new RuntimeException(" Error piping " + accessor.getDeclaringClass()
                                                                  .getSimpleName() + "." + accessor.getName(), cause);
        }
    }

    public AnnotablePipe<? extends Annotation, InputRead, OutputRead> getReadPipe() {
        return this.readPipe;
    }

    public AnnotablePipe<? extends Annotation, InputWrite, OutputWrite> getWritePipe() {
        return this.writePipe;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public void disable() {
        if (this.required) throw new RuntimeException("Header does not contain required column " + this.name);
        this.enabled = false;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPosition() {
        return this.position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("['");
        sb.append(this.name);
        sb.append("' pos=");
        sb.append(this.position);
        sb.append(this.enabled ? " on" : " off");
        if (this.required) sb.append(" required");
        sb.append("]");
        return sb.toString();
    }
}
