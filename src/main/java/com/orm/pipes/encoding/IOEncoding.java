package com.orm.pipes.encoding;

import com.orm.annotations.Extends;
import com.orm.pipe.AnnotablePipe;
import com.orm.pipe.DualPipe;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.lang.annotation.*;

import static com.orm.annotations.Annotations.DEFAULT;


@Repeatable(IOEncodings.class)
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Extends(DualPipe.class)
public @interface IOEncoding {
    @Repeatable(IOEncodings.Charsets.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends(DualPipe.class)
    @interface Charset {
        String value();

        String[] enabledOn() default {"read", "write"};

        Class<? extends AnnotablePipe<Charset, InputStream, Reader>> read() default EncodingPipes.CharsetDecoder.class;

        Class<? extends AnnotablePipe<Charset, OutputStream, Writer>> write() default EncodingPipes.CharsetEncoder.class;

        String view() default DEFAULT;
    }

    @Repeatable(IOEncodings.BOMs.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends(DualPipe.class)
    @interface BOM {
        EncodingPipes.BOMCharset value() default EncodingPipes.BOMCharset.UTF_8;

        boolean includeBOM() default false;

        String[] enabledOn() default {"read", "write"};

        Class<? extends AnnotablePipe<BOM, InputStream, Reader>> read() default EncodingPipes.BOMDecoder.class;

        Class<? extends AnnotablePipe<BOM, OutputStream, Writer>> write() default EncodingPipes.BOMEncoder.class;

        String view() default DEFAULT;
    }

    @Repeatable(IOEncodings.Automatics.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends(DualPipe.class)
    @interface Automatic {
        String value() default "";

        String[] enabledOn() default {"read", "write"};

        //Class<? extends AnnotablePipe<Automatic, InputStream, Reader>> read() default EncodingPipes.AutomaticIcu4jDecoder.class;

        Class<? extends AnnotablePipe<Automatic, InputStream, Reader>> read() default EncodingPipes.AutomaticGuessencodingDecoder.class;

        Class<? extends AnnotablePipe<Automatic, OutputStream, Writer>> write() default EncodingPipes.AutomaticCharsetEncoder.class;

        String view() default DEFAULT;
    }
}

