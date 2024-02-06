package io.github.agache41.ormpipes.pipes.encoding;

import com.glaforge.i18n.io.CharsetToolkit;
import com.ibm.icu.text.CharsetDetector;
import com.ibm.icu.text.CharsetMatch;
import io.github.agache41.ormpipes.functional.ThrowingFunction;
import io.github.agache41.ormpipes.pipe.AnnotablePipe;
import org.apache.commons.io.ByteOrderMark;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.input.BOMInputStream;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * <pre>
 * The type Encoding pipes.
 * </pre>
 */
public class EncodingPipes {

    /**
     * <pre>
     * The enum Bom charset.
     * </pre>
     */
    public enum BOMCharset {

        /**
         * <pre>
         * Utf 8 bom charset.
         * </pre>
         */
        UTF_8(StandardCharsets.UTF_8, ByteOrderMark.UTF_8), //
        /**
         * <pre>
         * Utf 16 be bom charset.
         * </pre>
         */
        UTF_16BE(StandardCharsets.UTF_16BE, ByteOrderMark.UTF_16BE), //
        /**
         * <pre>
         * Utf 16 le bom charset.
         * </pre>
         */
        UTF_16LE(StandardCharsets.UTF_16LE, ByteOrderMark.UTF_16LE), //
        /**
         * <pre>
         * Utf 32 be bom charset.
         * </pre>
         */
        UTF_32BE(java.nio.charset.Charset.forName("UTF_32BE"), ByteOrderMark.UTF_32BE), //
        /**
         * <pre>
         * Utf 32 le bom charset.
         * </pre>
         */
        UTF_32LE(java.nio.charset.Charset.forName("UTF_32LE"), ByteOrderMark.UTF_32LE); //

        /**
         * <pre>
         * The constant byteOrderMarks.
         * </pre>
         */
        public static final ByteOrderMark[] byteOrderMarks = {
                ByteOrderMark.UTF_8,
                ByteOrderMark.UTF_16BE,
                ByteOrderMark.UTF_16LE,
                ByteOrderMark.UTF_32BE,
                ByteOrderMark.UTF_32LE};
        private final java.nio.charset.Charset charset;
        private final ByteOrderMark byteOrderMark;

        BOMCharset(java.nio.charset.Charset charset,
                   ByteOrderMark byteOrderMark) {
            this.charset = charset;
            this.byteOrderMark = byteOrderMark;
        }

        /**
         * <pre>
         * Gets charset.
         * </pre>
         *
         * @return the charset
         */
        public Charset getCharset() {
            return this.charset;
        }

        /**
         * <pre>
         * Gets byte order mark.
         * </pre>
         *
         * @return the byte order mark
         */
        public ByteOrderMark getByteOrderMark() {
            return this.byteOrderMark;
        }
    }

    /**
     * <pre>
     * The type Charset decoder.
     * </pre>
     */
    public static class CharsetDecoder implements AnnotablePipe<IOEncoding.Charset, InputStream, Reader> {
        private Charset configuredCharset;

        private ThrowingFunction<InputStream, Reader> function;

        /**
         * {@inheritDoc}
         */
        @Override
        public void configure(IOEncoding.Charset cfg) {
            this.configuredCharset = cfg.value()
                                        .isEmpty() ? Charset.defaultCharset() : Charset.forName(cfg.value());
            this.function = inputStream -> {
                logger.infof("read using %s",
                             this.configuredCharset);
                return new InputStreamReader(inputStream,
                                             this.configuredCharset);
            };
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public ThrowingFunction<InputStream, Reader> function() {
            return this.function;
        }
    }

    /**
     * <pre>
     * The type Charset encoder.
     * </pre>
     */
    public static class CharsetEncoder implements AnnotablePipe<IOEncoding.Charset, OutputStream, Writer> {
        private Charset configuredCharset;

        /**
         * {@inheritDoc}
         */
        @Override
        public void configure(IOEncoding.Charset cfg) {
            this.configuredCharset = cfg.value()
                                        .isEmpty() ? Charset.defaultCharset() : Charset.forName(cfg.value());
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public ThrowingFunction<OutputStream, Writer> function() {
            return outputStream -> {
                logger.infof("write using %s",
                             this.configuredCharset);
                return new OutputStreamWriter(outputStream,
                                              this.configuredCharset);
            };
        }
    }

    /**
     * <pre>
     * The type Bom decoder.
     * </pre>
     */
    public static class BOMDecoder implements AnnotablePipe<IOEncoding.BOM, InputStream, Reader> {

        private boolean includeBOM;
        private Charset configuredCharset;

        /**
         * {@inheritDoc}
         */
        @Override
        public void configure(IOEncoding.BOM cfg) {
            this.includeBOM = cfg.includeBOM();
            this.configuredCharset = cfg.value()
                                        .getCharset();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public ThrowingFunction<InputStream, Reader> function() {
            return inputStream -> {
                BOMInputStream bOMInputStream = new BOMInputStream(inputStream, this.includeBOM, BOMCharset.byteOrderMarks);
                ByteOrderMark bom = null;
                try {
                    bom = bOMInputStream.getBOM();
                } catch (IOException e) {
                    this.handleException(IOEncoding.BOM.class,
                                         EncodingPipes.BOMDecoder.class,
                                         e,
                                         inputStream);
                }
                if (bom != null) {
                    Charset bomDetectedCharset = Charset.forName(bom.getCharsetName());
                    if (bomDetectedCharset.equals(this.configuredCharset)) {
                        logger.debugf("BOM confirmed %s",
                                      bomDetectedCharset);
                    } else {
                        logger.warnf("Encountered different BOM Charset %s for configured %s",
                                     bomDetectedCharset, this.configuredCharset);
                    }
                    logger.debugf("read using %s",
                                  bomDetectedCharset);
                    return new InputStreamReader(bOMInputStream,
                                                 bomDetectedCharset);
                } else {
                    logger.warnf("BOM was not detected !");
                    logger.debugf("read using %s",
                                  this.configuredCharset);
                    return new InputStreamReader(bOMInputStream,
                                                 this.configuredCharset);
                }
            };
        }
    }

    /**
     * <pre>
     * The type Bom encoder.
     * </pre>
     */
    public static class BOMEncoder implements AnnotablePipe<IOEncoding.BOM, OutputStream, Writer> {

        private ByteOrderMark byteOrderMark;
        private Charset configuredCharset;

        /**
         * {@inheritDoc}
         */
        @Override
        public void configure(IOEncoding.BOM cfg) {
            this.byteOrderMark = cfg.value()
                                    .getByteOrderMark();
            this.configuredCharset = cfg.value()
                                        .getCharset();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public ThrowingFunction<OutputStream, Writer> function() {
            return outputStream -> {
                try {
                    outputStream.write(this.byteOrderMark.getBytes());
                    logger.debugf("BOM added for charset %s", this.configuredCharset.displayName());
                } catch (IOException e) {
                    this.handleException(IOEncoding.BOM.class,
                                         EncodingPipes.BOMEncoder.class,
                                         e,
                                         outputStream);
                }
                logger.debugf("write using %s",
                              this.configuredCharset.displayName());
                return new OutputStreamWriter(outputStream,
                                              this.configuredCharset);
            };
        }
    }


    /**
     * https://unicode-org.github.io/icu/userguide/conversion/detection.html
     * Warning : fails with -Dfile.encoding=windows-1252 by AUTOBeanTest
     */
    public static class AutomaticIcu4jDecoder implements AnnotablePipe<IOEncoding.Automatic, InputStream, Reader> {
        private Charset configuredCharset;

        private boolean detectAndRemoveBOM;

        /**
         * {@inheritDoc}
         */
        @Override
        public void configure(IOEncoding.Automatic cfg) {
            this.configuredCharset = cfg.value()
                                        .isEmpty() ? Charset.defaultCharset() : Charset.forName(cfg.value());
        }

        /**
         * Creates a reader on the given Input, auto-detecting the given charset from the inputStream.
         * A default charset can be provided in case no charset can be detected or an exception my occur.
         * The method does not close the inputStream.
         * The method does read the entire inputStream in memory, so it must be used with caution on large data sets.
         */
        /**
         * {@inheritDoc}
         */
        @Override
        public ThrowingFunction<InputStream, Reader> function() {
            return inputStream -> {
                // autodetect section
                byte[] rawBytes = null;
                Charset charset = null;
                try {
                    rawBytes = IOUtils.toByteArray(inputStream);
                    final CharsetDetector charsetDetector = new CharsetDetector();
                    charsetDetector.setDeclaredEncoding(this.configuredCharset.displayName());
                    charsetDetector.setText(rawBytes);
                    final CharsetMatch[] detect = charsetDetector.detectAll();
                    for (CharsetMatch chm : detect) {
                        logger.debugf("ICU4j:Detected charset :%s with %s%",
                                      chm.getName(),
                                      chm.getConfidence());
                    }
                    if (detect.length > 0) {
                        charset = Charset.forName(detect[0].getName());
                        logger.debugf("ICU4j:Detected charset :%s ",
                                      charset.displayName());
                    }
                } catch (Exception e) {
                    this.handleException(IOEncoding.Automatic.class,
                                         AutomaticIcu4jDecoder.class,
                                         e,
                                         inputStream);
                }
                if (null == charset) {
                    logger.error("ICU4j:No charset detected!");
                    charset = this.configuredCharset;
                }
                logger.debugf("read using %s",
                              charset);
                return new InputStreamReader(new ByteArrayInputStream(rawBytes),
                                             charset);
            };
        }
    }


    /**
     * https://github.com/codehaus/guessencoding
     */
    public static class AutomaticGuessencodingDecoder implements AnnotablePipe<IOEncoding.Automatic, InputStream, Reader> {

        private Charset configuredCharset;


        /**
         * {@inheritDoc}
         */
        @Override
        public void configure(IOEncoding.Automatic cfg) {
            this.configuredCharset = cfg.value()
                                        .isEmpty() ? Charset.defaultCharset() : Charset.forName(cfg.value());
        }

        /**
         * Creates a reader on the given Input, autodetecting the given charset from the inputStream.
         * A default charset can be provided in case no charset can be detected or an exception my occur.
         * The method does not close the inputStream.
         * The method does read the entire inputStream in memory, so it must be used with caution on large data sets.
         *
         * @return Reader
         */
        /**
         * {@inheritDoc}
         */
        @Override
        public ThrowingFunction<InputStream, Reader> function() {
            return inputStream -> {
                // autodetect section
                byte[] rawBytes = null;
                Charset charset = null;
                try {
                    rawBytes = IOUtils.toByteArray(inputStream);
                    CharsetToolkit charsetToolkit = new CharsetToolkit(rawBytes,
                                                                       this.configuredCharset);
                    charset = charsetToolkit.guessEncoding();
                    logger.infof("Guessencoding:Detected charset :%s ",
                                 charset.displayName());
                } catch (IOException e) {
                    this.handleException(IOEncoding.Automatic.class,
                                         AutomaticIcu4jDecoder.class,
                                         e,
                                         inputStream);
                } finally {
                    try {
                        inputStream.close();
                        logger.debugf("InputStream closed.");
                    } catch (IOException io) {
                        logger.warnf("Error closing inputStream : %s", io.getMessage());
                    }
                }
                if (null == charset) {
                    logger.error("Guessencoding:No charset detected!");
                    charset = this.configuredCharset;
                }
                logger.debugf("read using %s",
                              charset);
                return new InputStreamReader(new ByteArrayInputStream(rawBytes),
                                             charset);
            };
        }
    }

    /**
     * <pre>
     * The type Automatic charset encoder.
     * </pre>
     */
    public static class AutomaticCharsetEncoder implements AnnotablePipe<IOEncoding.Automatic, OutputStream, Writer> {
        private Charset configuredCharset;

        /**
         * {@inheritDoc}
         */
        @Override
        public void configure(IOEncoding.Automatic cfg) {
            this.configuredCharset = cfg.value()
                                        .isEmpty() ? Charset.defaultCharset() : Charset.forName(cfg.value());
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public ThrowingFunction<OutputStream, Writer> function() {
            return outputStream -> {
                logger.infof("write using %s",
                             this.configuredCharset);
                return new OutputStreamWriter(outputStream,
                                              this.configuredCharset);
            };
        }
    }
}
