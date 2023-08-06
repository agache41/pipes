package com.orm.pipes.encoding;

import com.glaforge.i18n.io.CharsetToolkit;
import com.ibm.icu.text.CharsetDetector;
import com.ibm.icu.text.CharsetMatch;
import com.orm.functional.ThrowingFunction;
import com.orm.pipe.AnnotablePipe;
import org.apache.commons.io.ByteOrderMark;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.input.BOMInputStream;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class EncodingPipes {

    public enum BOMCharset {

        UTF_8(StandardCharsets.UTF_8, ByteOrderMark.UTF_8), //
        UTF_16BE(StandardCharsets.UTF_16BE, ByteOrderMark.UTF_16BE), //
        UTF_16LE(StandardCharsets.UTF_16LE, ByteOrderMark.UTF_16LE), //
        UTF_32BE(java.nio.charset.Charset.forName("UTF_32BE"), ByteOrderMark.UTF_32BE), //
        UTF_32LE(java.nio.charset.Charset.forName("UTF_32LE"), ByteOrderMark.UTF_32LE); //

        public static final ByteOrderMark[] byteOrderMarks = {
                ByteOrderMark.UTF_8,
                ByteOrderMark.UTF_16BE,
                ByteOrderMark.UTF_16LE,
                ByteOrderMark.UTF_32BE,
                ByteOrderMark.UTF_32LE};
        private final java.nio.charset.Charset charset;
        private final ByteOrderMark byteOrderMark;

        BOMCharset(java.nio.charset.Charset charset, ByteOrderMark byteOrderMark) {
            this.charset = charset;
            this.byteOrderMark = byteOrderMark;
        }

        public Charset getCharset() {
            return this.charset;
        }

        public ByteOrderMark getByteOrderMark() {
            return this.byteOrderMark;
        }
    }

    public static class CharsetDecoder implements AnnotablePipe<IOEncoding.Charset, InputStream, Reader> {
        private Charset configuredCharset;

        private ThrowingFunction<InputStream, Reader> function;

        @Override
        public void configure(IOEncoding.Charset cfg) {
            this.configuredCharset = cfg.value()
                                        .isEmpty() ? Charset.defaultCharset() : Charset.forName(cfg.value());
            this.function = inputStream -> {
                logger.info("read using {}",
                        this.configuredCharset);
                return new InputStreamReader(inputStream,
                        this.configuredCharset);
            };
        }

        @Override
        public ThrowingFunction<InputStream, Reader> function() {
            return this.function;
        }
    }

    public static class CharsetEncoder implements AnnotablePipe<IOEncoding.Charset, OutputStream, Writer> {
        private Charset configuredCharset;

        @Override
        public void configure(IOEncoding.Charset cfg) {
            this.configuredCharset = cfg.value()
                                        .isEmpty() ? Charset.defaultCharset() : Charset.forName(cfg.value());
        }

        @Override
        public ThrowingFunction<OutputStream, Writer> function() {
            return outputStream -> {
                logger.info("write using {}",
                        this.configuredCharset);
                return new OutputStreamWriter(outputStream,
                        this.configuredCharset);
            };
        }
    }

    public static class BOMDecoder implements AnnotablePipe<IOEncoding.BOM, InputStream, Reader> {

        private boolean includeBOM;
        private Charset configuredCharset;

        @Override
        public void configure(IOEncoding.BOM cfg) {
            this.includeBOM = cfg.includeBOM();
            this.configuredCharset = cfg.value()
                                        .getCharset();
        }

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
                        logger.debug("BOM confirmed {}",
                                bomDetectedCharset);
                    } else {
                        logger.warn("Encountered different BOM Charset {} for configured {}",
                                bomDetectedCharset, this.configuredCharset);
                    }
                    logger.debug("read using {}",
                            bomDetectedCharset);
                    return new InputStreamReader(bOMInputStream,
                            bomDetectedCharset);
                } else {
                    logger.warn("BOM was not detected !");
                    logger.debug("read using {}",
                            this.configuredCharset);
                    return new InputStreamReader(bOMInputStream,
                            this.configuredCharset);
                }
            };
        }
    }

    public static class BOMEncoder implements AnnotablePipe<IOEncoding.BOM, OutputStream, Writer> {

        private ByteOrderMark byteOrderMark;
        private Charset configuredCharset;

        @Override
        public void configure(IOEncoding.BOM cfg) {
            this.byteOrderMark = cfg.value()
                                    .getByteOrderMark();
            this.configuredCharset = cfg.value()
                                        .getCharset();
        }

        @Override
        public ThrowingFunction<OutputStream, Writer> function() {
            return outputStream -> {
                try {
                    outputStream.write(this.byteOrderMark.getBytes());
                    logger.debug("BOM added for charset {}", this.configuredCharset.displayName());
                } catch (IOException e) {
                    this.handleException(IOEncoding.BOM.class,
                            EncodingPipes.BOMEncoder.class,
                            e,
                            outputStream);
                }
                logger.debug("write using {}",
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
                        logger.debug("ICU4j:Detected charset :{} with {}%",
                                chm.getName(),
                                chm.getConfidence());
                    }
                    if (detect.length > 0) {
                        charset = Charset.forName(detect[0].getName());
                        logger.debug("ICU4j:Detected charset :{} ",
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
                logger.debug("read using {}",
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
                    logger.info("Guessencoding:Detected charset :{} ",
                            charset.displayName());
                } catch (IOException e) {
                    this.handleException(IOEncoding.Automatic.class,
                            AutomaticIcu4jDecoder.class,
                            e,
                            inputStream);
                } finally {
                    try {
                        inputStream.close();
                        logger.debug("InputStream closed.");
                    } catch (IOException io) {
                        logger.warn("Error closing inputStream", io.getMessage());
                    }
                }
                if (null == charset) {
                    logger.error("Guessencoding:No charset detected!");
                    charset = this.configuredCharset;
                }
                logger.debug("read using {}",
                        charset);
                return new InputStreamReader(new ByteArrayInputStream(rawBytes),
                        charset);
            };
        }
    }

    public static class AutomaticCharsetEncoder implements AnnotablePipe<IOEncoding.Automatic, OutputStream, Writer> {
        private Charset configuredCharset;

        @Override
        public void configure(IOEncoding.Automatic cfg) {
            this.configuredCharset = cfg.value()
                                        .isEmpty() ? Charset.defaultCharset() : Charset.forName(cfg.value());
        }

        @Override
        public ThrowingFunction<OutputStream, Writer> function() {
            return outputStream -> {
                logger.info("write using {}",
                        this.configuredCharset);
                return new OutputStreamWriter(outputStream,
                        this.configuredCharset);
            };
        }
    }
}
