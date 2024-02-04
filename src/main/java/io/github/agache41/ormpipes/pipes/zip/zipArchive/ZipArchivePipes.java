package io.github.agache41.ormpipes.pipes.zip.zipArchive;

import io.github.agache41.ormpipes.functional.StrongType;
import io.github.agache41.ormpipes.functional.ThrowingFunction;
import io.github.agache41.ormpipes.pipe.AnnotablePipe;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZipArchivePipes {
    public static class ArchiveInputStreamPipe implements AnnotablePipe<Zip.Archive, InputStream, ZipInputStream> {

        private ThrowingFunction<InputStream, ZipInputStream> function;

        @Override
        public StrongType getInputType() {
            return StrongType.of(InputStream.class);
        }

        @Override
        public StrongType getOutputType() {
            return StrongType.of(ZipInputStream.class);
        }

        @Override
        public void configure(Zip.Archive cfg) {
            this.function = ZipInputStream::new;
        }

        @Override
        public ThrowingFunction<InputStream, ZipInputStream> function() {
            return this.function;
        }
    }

    public static class ArchiveOutputStreamPipe implements AnnotablePipe<Zip.Archive, OutputStream, ZipOutputStream> {
        private ThrowingFunction<OutputStream, ZipOutputStream> function;

        @Override
        public StrongType getInputType() {
            return StrongType.of(OutputStream.class);
        }

        @Override
        public StrongType getOutputType() {
            return StrongType.of(ZipOutputStream.class);
        }

        @Override
        public void configure(Zip.Archive cfg) {
            this.function = ZipOutputStream::new;
        }

        @Override
        public ThrowingFunction<OutputStream, ZipOutputStream> function() {
            return this.function;
        }
    }

    public static class ZipEntryInputStreamPipe implements AnnotablePipe<Zip.Entry, ZipInputStream, InputStream> {

        private ThrowingFunction<ZipInputStream, InputStream> function;

        @Override
        public StrongType getInputType() {
            return StrongType.of(ZipInputStream.class);
        }

        @Override
        public StrongType getOutputType() {
            return StrongType.of(InputStream.class);
        }

        @Override
        public void configure(Zip.Entry cfg) {
            this.function = zipInputStream -> {
                ZipEntry zipEntry;
                while ((zipEntry = zipInputStream.getNextEntry()) != null) {
                    if (zipEntry.getName()
                                .equals(cfg.fileName())) {
                        return zipInputStream;
                    }
                }
                throw new RuntimeException("Archive does not contain file" + cfg.fileName());
            };
        }

        @Override
        public ThrowingFunction<ZipInputStream, InputStream> function() {
            return this.function;
        }
    }

    public static class ZipEntryOutputStreamPipe implements AnnotablePipe<Zip.Entry, ZipOutputStream, OutputStream> {
        private ThrowingFunction<ZipOutputStream, OutputStream> function;

        @Override
        public StrongType getInputType() {
            return StrongType.of(ZipOutputStream.class);
        }

        @Override
        public StrongType getOutputType() {
            return StrongType.of(OutputStream.class);
        }

        @Override
        public void configure(Zip.Entry cfg) {
            this.function = zipOutputStream -> {
                ZipEntry entry = new ZipEntry(cfg.fileName());
                zipOutputStream.putNextEntry(entry);
                return zipOutputStream;
            };
        }

        @Override
        public ThrowingFunction<ZipOutputStream, OutputStream> function() {
            return this.function;
        }
    }
}
