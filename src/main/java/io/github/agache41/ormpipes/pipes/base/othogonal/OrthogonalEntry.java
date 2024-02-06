package io.github.agache41.ormpipes.pipes.base.othogonal;

import io.github.agache41.annotator.accessor.Accessor;
import io.github.agache41.annotator.annotator.Annotate;
import io.github.agache41.annotator.annotator.Annotator;
import io.github.agache41.ormpipes.config.Constants;
import io.github.agache41.ormpipes.functional.ThrowingFunction;
import io.github.agache41.ormpipes.pipe.AnnotablePipe;
import io.github.agache41.ormpipes.pipe.registry.PipeRegistry;
import io.github.agache41.ormpipes.pipes.base.othogonal.enums.NamingMethod;
import io.github.agache41.ormpipes.pipes.base.othogonal.enums.PositionMethod;

import java.lang.annotation.Annotation;

/**
 * <pre>
 * The type Orthogonal entry.
 * </pre>
 *
 * @param <A>           the type parameter
 * @param <InputRead>   the type parameter
 * @param <OutputRead>  the type parameter
 * @param <InputWrite>  the type parameter
 * @param <OutputWrite> the type parameter
 */
public class OrthogonalEntry<A extends Annotation, InputRead, OutputRead, InputWrite, OutputWrite> {
    /**
     * <pre>
     * The Cfg.
     * </pre>
     */
    protected final A cfg;
    private final boolean required;
    /**
     * <pre>
     * The Read pipe.
     * </pre>
     */
    protected AnnotablePipe<? extends Annotation, InputRead, OutputRead> readPipe;
    /**
     * <pre>
     * The Write pipe.
     * </pre>
     */
    protected AnnotablePipe<? extends Annotation, InputWrite, OutputWrite> writePipe;

    private String name;
    private int position;
    private boolean enabled = true;

    /**
     * <pre>
     * Instantiates a new Orthogonal entry.
     * </pre>
     *
     * @param name     the name
     * @param position the position
     */
    public OrthogonalEntry(String name,
                           int position) {
        this.cfg = null;
        this.name = name;
        this.position = position;
        this.required = false;
        this.readPipe = new AnnotablePipe<Annotation, InputRead, OutputRead>() {
            /**
             * {@inheritDoc}
             */
            @Override
            public ThrowingFunction<InputRead, OutputRead> function() {
                return input -> null;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public void configure(Annotation cfg) {
            }
        };

        this.writePipe = new AnnotablePipe<Annotation, InputWrite, OutputWrite>() {
            /**
             * {@inheritDoc}
             */
            @Override
            public ThrowingFunction<InputWrite, OutputWrite> function() {
                return input -> null;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public void configure(Annotation cfg) {
            }
        };
    }

    /**
     * <pre>
     * Instantiates a new Orthogonal entry.
     * </pre>
     *
     * @param cfgClass       the cfg class
     * @param accessor       the accessor
     * @param namingMethod   the naming method
     * @param positionMethod the position method
     */
    public OrthogonalEntry(Class<A> cfgClass,
                           Accessor<?> accessor,
                           NamingMethod namingMethod,
                           PositionMethod positionMethod) {
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
            this.readPipe = PipeRegistry.buildPipeFrom(accessor, Constants.DEFAULT, "read", false);
            this.writePipe = PipeRegistry.buildPipeFrom(accessor, Constants.DEFAULT, "write", true);
        } catch (Throwable cause) {
            throw new RuntimeException(" Error piping " + accessor.getDeclaringClass()
                                                                  .getSimpleName() + "." + accessor.getName(), cause);
        }
    }

    /**
     * <pre>
     * Gets read pipe.
     * </pre>
     *
     * @return the read pipe
     */
    public AnnotablePipe<? extends Annotation, InputRead, OutputRead> getReadPipe() {
        return this.readPipe;
    }

    /**
     * <pre>
     * Gets write pipe.
     * </pre>
     *
     * @return the write pipe
     */
    public AnnotablePipe<? extends Annotation, InputWrite, OutputWrite> getWritePipe() {
        return this.writePipe;
    }

    /**
     * <pre>
     * Is enabled boolean.
     * </pre>
     *
     * @return the boolean
     */
    public boolean isEnabled() {
        return this.enabled;
    }

    /**
     * <pre>
     * Disable.
     * </pre>
     */
    public void disable() {
        if (this.required) {
            throw new RuntimeException("Header does not contain required column " + this.name);
        }
        this.enabled = false;
    }

    /**
     * <pre>
     * Gets name.
     * </pre>
     *
     * @return the name
     */
    public String getName() {
        return this.name;
    }

    /**
     * <pre>
     * Sets name.
     * </pre>
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * <pre>
     * Gets position.
     * </pre>
     *
     * @return the position
     */
    public int getPosition() {
        return this.position;
    }

    /**
     * <pre>
     * Sets position.
     * </pre>
     *
     * @param position the position
     */
    public void setPosition(int position) {
        this.position = position;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("['");
        sb.append(this.name);
        sb.append("' pos=");
        sb.append(this.position);
        sb.append(this.enabled ? " on" : " off");
        if (this.required) {
            sb.append(" required");
        }
        sb.append("]");
        return sb.toString();
    }
}
