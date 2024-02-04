package io.github.agache41.ormpipes.pipes.typeString;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class AutoClosingBufferedReader extends BufferedReader {
    Logger logger = LogManager.getLogger(AutoClosingBufferedReader.class);

    public AutoClosingBufferedReader(Reader in, int sz) {
        super(in, sz);
    }

    public AutoClosingBufferedReader(Reader in) {
        super(in);
    }


    /**
     * Wrapper over {@link BufferedReader#lines() BufferedReader.lines()}
     * that ensures closing of the stream when the last line was read
     * See {@link BufferedReader#lines() BufferedReader.lines()}
     */
    public Stream<String> lines() {
        Iterator<String> iter = new Iterator<String>() {
            String nextLine = null;

            @Override
            public boolean hasNext() {
                if (this.nextLine != null) {
                    return true;
                } else {
                    try {
                        this.nextLine = AutoClosingBufferedReader.this.readLine();
                        if (this.nextLine != null)
                            return true;
                        else {
                            try {
                                AutoClosingBufferedReader.super.close();
                                AutoClosingBufferedReader.this.logger.debug("Reader closed.");
                            } catch (IOException io) {
                                AutoClosingBufferedReader.this.logger.warn("Closing the Reader issued {}", io.getMessage());
                            }
                            return false;
                        }
                    } catch (IOException e) {
                        throw new UncheckedIOException(e);
                    }
                }
            }

            @Override
            public String next() {
                if (this.nextLine != null || this.hasNext()) {
                    String line = this.nextLine;
                    this.nextLine = null;
                    return line;
                } else {
                    throw new NoSuchElementException();
                }
            }
        };
        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(
                iter, Spliterator.ORDERED | Spliterator.NONNULL), false);
    }

}
