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

package io.github.agache41.ormpipes.pipes.zip.zipArchive;

import io.github.agache41.ormpipes.functional.StrongType;
import io.github.agache41.ormpipes.functional.ThrowingFunction;
import io.github.agache41.ormpipes.pipe.AnnotablePipe;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * <pre>
 * The type Zip archive pipes.
 * </pre>
 */
public class ZipArchivePipes {
    /**
     * <pre>
     * The type Archive input stream pipe.
     * </pre>
     */
    public static class ArchiveInputStreamPipe implements AnnotablePipe<Zip.Archive, InputStream, ZipInputStream> {

        private ThrowingFunction<InputStream, ZipInputStream> function;

        /**
         * {@inheritDoc}
         */
        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getInputType() {
            return StrongType.of(InputStream.class);
        }

        /**
         * {@inheritDoc}
         */
        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getOutputType() {
            return StrongType.of(ZipInputStream.class);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void configure(Zip.Archive cfg) {
            this.function = ZipInputStream::new;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public ThrowingFunction<InputStream, ZipInputStream> function() {
            return this.function;
        }
    }

    /**
     * <pre>
     * The type Archive output stream pipe.
     * </pre>
     */
    public static class ArchiveOutputStreamPipe implements AnnotablePipe<Zip.Archive, OutputStream, ZipOutputStream> {
        private ThrowingFunction<OutputStream, ZipOutputStream> function;

        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getInputType() {
            return StrongType.of(OutputStream.class);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getOutputType() {
            return StrongType.of(ZipOutputStream.class);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void configure(Zip.Archive cfg) {
            this.function = ZipOutputStream::new;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public ThrowingFunction<OutputStream, ZipOutputStream> function() {
            return this.function;
        }
    }

    /**
     * <pre>
     * The type Zip entry input stream pipe.
     * </pre>
     */
    public static class ZipEntryInputStreamPipe implements AnnotablePipe<Zip.Entry, ZipInputStream, InputStream> {

        private ThrowingFunction<ZipInputStream, InputStream> function;

        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getInputType() {
            return StrongType.of(ZipInputStream.class);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getOutputType() {
            return StrongType.of(InputStream.class);
        }

        /**
         * {@inheritDoc}
         */
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

        /**
         * {@inheritDoc}
         */
        @Override
        public ThrowingFunction<ZipInputStream, InputStream> function() {
            return this.function;
        }
    }

    /**
     * <pre>
     * The type Zip entry output stream pipe.
     * </pre>
     */
    public static class ZipEntryOutputStreamPipe implements AnnotablePipe<Zip.Entry, ZipOutputStream, OutputStream> {
        private ThrowingFunction<ZipOutputStream, OutputStream> function;

        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getInputType() {
            return StrongType.of(ZipOutputStream.class);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getOutputType() {
            return StrongType.of(OutputStream.class);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void configure(Zip.Entry cfg) {
            this.function = zipOutputStream -> {
                ZipEntry entry = new ZipEntry(cfg.fileName());
                zipOutputStream.putNextEntry(entry);
                return zipOutputStream;
            };
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public ThrowingFunction<ZipOutputStream, OutputStream> function() {
            return this.function;
        }
    }
}
