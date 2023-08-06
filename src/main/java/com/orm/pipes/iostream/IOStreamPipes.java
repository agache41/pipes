package com.orm.pipes.iostream;

import com.orm.annotations.Annotations;
import com.orm.functional.StrongType;
import com.orm.functional.ThrowingFunction;
import com.orm.pipe.AnnotablePipe;
import com.orm.pipes.typeFile.TypeFile;

import java.io.*;

public class IOStreamPipes {
    public static class FileInputStreamPipe implements AnnotablePipe<IOStream.FileBased, File, InputStream> {

        private ThrowingFunction<File, InputStream> function;

        @Override
        public StrongType getInputType() {
            return TypeFile.strongType;
        }

        @Override
        public StrongType getOutputType() {
            return StrongType.of(InputStream.class);
        }

        @Override
        public void configure(IOStream.FileBased cfg) {
            this.function = FileInputStream::new;
            final int bufferSize = cfg.bufferSizeKB() * Annotations.KB;
            if (bufferSize != 0)
                this.function = this.function.andThen(inputStream -> new BufferedInputStream(inputStream, bufferSize));
        }

        @Override
        public ThrowingFunction<File, InputStream> function() {
            return this.function;
        }
    }

    public static class FileOutputStreamPipe implements AnnotablePipe<IOStream.FileBased, File, OutputStream> {
        private ThrowingFunction<File, OutputStream> function;

        @Override
        public StrongType getInputType() {
            return TypeFile.strongType;
        }

        @Override
        public StrongType getOutputType() {
            return StrongType.of(OutputStream.class);
        }

        @Override
        public void configure(IOStream.FileBased cfg) {
            final boolean append = cfg.append();
            this.function = file -> new FileOutputStream(file, append);
            final int bufferSize = cfg.bufferSizeKB() * Annotations.KB;
            if (bufferSize != 0)
                this.function = this.function.andThen(outputStream -> new BufferedOutputStream(outputStream, bufferSize));
        }

        @Override
        public ThrowingFunction<File, OutputStream> function() {
            return this.function;
        }
    }
}
