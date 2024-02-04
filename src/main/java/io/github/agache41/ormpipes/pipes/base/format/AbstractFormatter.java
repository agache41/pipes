package io.github.agache41.ormpipes.pipes.base.format;

import io.github.agache41.annotator.annotator.Annotate;
import io.github.agache41.annotator.annotator.Annotator;
import io.github.agache41.ormpipes.config.Configuration;
import io.github.agache41.ormpipes.config.Settings;
import io.github.agache41.ormpipes.pipe.AnnotablePipe;

import java.lang.annotation.Annotation;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class AbstractFormatter<A extends Annotation, Input, Output> implements AnnotablePipe<A, Input, Output> {

    private static final Set<String> AVAILABLE_LANGUAGE_TAGS = Stream.of(Locale.getAvailableLocales())
                                                                     .map(Locale::toLanguageTag)
                                                                     .map(String::toLowerCase)
                                                                     .collect(Collectors.toSet());

    private static final Map<String, DateTimeFormatter> staticDateTimeFormatters = new HashMap<>();
    protected Boolean simple;
    protected Boolean nullSafe;
    protected Boolean blankSafe;
    protected Boolean noException;
    protected String format;
    protected String languageTag;

    {
        staticDateTimeFormatters.put("BASIC_ISO_DATE", DateTimeFormatter.BASIC_ISO_DATE);
        staticDateTimeFormatters.put("ISO_LOCAL_DATE", DateTimeFormatter.ISO_LOCAL_DATE);
        staticDateTimeFormatters.put("ISO_OFFSET_DATE", DateTimeFormatter.ISO_OFFSET_DATE);
        staticDateTimeFormatters.put("ISO_DATE", DateTimeFormatter.ISO_DATE);
        staticDateTimeFormatters.put("ISO_LOCAL_TIME", DateTimeFormatter.ISO_LOCAL_TIME);
        staticDateTimeFormatters.put("ISO_OFFSET_TIME", DateTimeFormatter.ISO_OFFSET_TIME);
        staticDateTimeFormatters.put("ISO_TIME", DateTimeFormatter.ISO_TIME);
        staticDateTimeFormatters.put("ISO_LOCAL_DATE_TIME", DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        staticDateTimeFormatters.put("ISO_OFFSET_DATE_TIME", DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        staticDateTimeFormatters.put("ISO_ZONED_DATE_TIME", DateTimeFormatter.ISO_ZONED_DATE_TIME);
        staticDateTimeFormatters.put("ISO_DATE_TIME", DateTimeFormatter.ISO_DATE_TIME);
        staticDateTimeFormatters.put("ISO_ORDINAL_DATE", DateTimeFormatter.ISO_ORDINAL_DATE);
        staticDateTimeFormatters.put("ISO_WEEK_DATE", DateTimeFormatter.ISO_WEEK_DATE);
        staticDateTimeFormatters.put("ISO_INSTANT", DateTimeFormatter.ISO_INSTANT);
        staticDateTimeFormatters.put("RFC_1123_DATE_TIME", DateTimeFormatter.RFC_1123_DATE_TIME);
    }

    @Override
    public void configure(A cfg) {
        Annotate<A> cfgAnnotator = Annotator.of(cfg);
        this.simple = cfgAnnotator.getAccessor("simple")
                                  .getAs(cfg, Boolean.class, Boolean.FALSE);
        this.nullSafe = cfgAnnotator.getAccessor("nullSafe")
                                    .getAs(cfg, Boolean.class, Boolean.FALSE);
        this.blankSafe = cfgAnnotator.getAccessor("blankSafe")
                                     .getAs(cfg, Boolean.class, Boolean.FALSE);
        this.noException = cfgAnnotator.getAccessor("noException")
                                       .getAs(cfg, Boolean.class, Boolean.FALSE);
    }


    /**
     * Returns a NumberFormat instance configured for the given format and language Tag
     * for Integer parsing.
     *
     * @param format
     * @param languageTag
     * @return
     */
    protected NumberFormat getIntegerNumberFormat(String format, String languageTag) {
        NumberFormat numberFormat = this.getNumberFormat(format, languageTag);
        numberFormat.setParseIntegerOnly(true);
        return numberFormat;
    }

    /**
     * Returns a NumberFormat instance configured for the given format and language Tag.
     * If they are null or empty, the configuration will be queried.
     * If then the configured values are still null or empty,
     * the method will try to use the default initialization.
     * The pattern is considered to be non-localized.
     *
     * @param format
     * @param languageTag
     * @return
     */
    protected NumberFormat getNumberFormat(String format, String languageTag) {
        final Locale locale = this.getLocale(languageTag);
        final String _format = this.getOrDefault(format, Settings.NUMERIC_INTEGER_FORMAT);
        if (locale != null) {
            if (this.isNullEmptyOrDefault(_format)) return NumberFormat.getInstance(locale);
            else
                return new DecimalFormat(_format, DecimalFormatSymbols.getInstance(locale));
        } else {
            if (this.isNullEmptyOrDefault(_format)) return new DecimalFormat();
            return new DecimalFormat(_format);
        }
    }

    /**
     * Returns a NumberFormat instance configured for the given format and language Tag.
     * If they are null or empty, the configuration will be queried.
     * If then the configured values are still null or empty,
     * the method will try to use the default initialization.
     * The pattern is considered to be non-localized.
     *
     * @param format
     * @param languageTag
     * @return
     */
    protected DecimalFormat getBigDecimalNumberFormat(String format, String languageTag) {
        DecimalFormat decimalFormat;
        final String _format = this.getOrDefault(format, Settings.NUMERIC_INTEGER_FORMAT);
        final Locale locale = this.getLocale(languageTag);
        if (this.isNullEmptyOrDefault(_format)) decimalFormat = new DecimalFormat();
        else if (locale != null) {
            decimalFormat = new DecimalFormat(_format, DecimalFormatSymbols.getInstance(locale));
        } else {
            decimalFormat = new DecimalFormat(_format);
        }
        decimalFormat.setParseBigDecimal(true);
        return decimalFormat;
    }

    /**
     * Returns a configured DateTimeFormatter using the format, languageTag and zoneId
     * If any of the parameter is empty, then the configuration value for him will be queried.
     * The format parameter can supplementary have one of the values
     * "BASIC_ISO_DATE"
     * "ISO_LOCAL_DATE"
     * "ISO_OFFSET_DATE"
     * "ISO_DATE"
     * "ISO_LOCAL_TIME"
     * "ISO_OFFSET_TIME"
     * "ISO_TIME"
     * "ISO_LOCAL_DATE_TIME"
     * "ISO_OFFSET_DATE_TIME"
     * "ISO_ZONED_DATE_TIME"
     * "ISO_DATE_TIME"
     * "ISO_ORDINAL_DATE"
     * "ISO_WEEK_DATE"
     * "ISO_INSTANT"
     * "RFC_1123_DATE_TIME"
     * which will return the corresponding static field from the DateTimeFormatter class.
     * If no languageTag or zoneId are provided (empty) then no localization and no zoneId will be used.
     *
     * @param format
     * @param languageTag
     * @param zoneId
     * @return
     */
    protected DateTimeFormatter getDateTimeFormatter(String format, String languageTag, String zoneId) {
        format = this.getOrDefault(format, Settings.DATE_TIME_FORMAT);
        if (staticDateTimeFormatters.containsKey(format))
            return staticDateTimeFormatters.get(format);
        DateTimeFormatter result = DateTimeFormatter.ofPattern(format);
        Locale locale = this.getLocale(languageTag);
        if (locale != null) result = result.withLocale(locale);
        ZoneId zoneID = this.getZoneId(zoneId);
        if (zoneID != null) result = result.withZone(zoneID);
        return result;
    }

    /**
     * Returns a configured SimpleDateFormat using the format and languageTag.
     * If any of the parameter is empty, then the configuration value for him will be queried.
     * If the Locale is not specified, only the format will be used.
     *
     * @param format
     * @param languageTag
     * @return
     */
    protected SimpleDateFormat getSimpleDateFormat(String format, String languageTag) {
        format = this.getOrDefault(format, Settings.DATE_TIME_FORMAT);
        Locale locale = this.getLocale(languageTag);
        if (locale == null) return new SimpleDateFormat(format);
        return new SimpleDateFormat(format, locale);
    }

    protected DecimalFormatSymbols getDecimalFormatSymbols(String languageTag) {
        return DecimalFormatSymbols.getInstance(this.getLocale(languageTag));
    }

    /**
     * Returns the locale for the given String.
     * If no value is passed, then the default configuration values will be queried.
     * If the values is then null or empty, null will be returned, so no localization.
     * If the value is DEFAULT (="default"), then the default machine instance will be returned.
     * If the value is not empty, it will be checked against the available values and an IllegalArgumentException will be thrown if unknown.
     * If the value is valid, the specified Locale will be returned.
     *
     * @param languageTag
     * @return
     */
    protected Locale getLocale(String languageTag) {
        languageTag = this.getOrDefault(languageTag, Settings.LANGUAGE_TAG);
        if (languageTag.isEmpty()) return null; // no localization
        if (Settings.DEFAULT.equalsIgnoreCase(languageTag)) return Locale.getDefault();
        if (!AVAILABLE_LANGUAGE_TAGS.contains(languageTag.toLowerCase())) {
            throw new IllegalArgumentException("languageTag " + languageTag + " is not implemented on this platform.");
        }
        return Locale.forLanguageTag(languageTag);
    }

    /**
     * Returns the ZoneId for the given String.
     * If no value is passed, then the default configuration values will be queried.
     * If the value is then null or empty, null will be returned, so no localization.
     * If the value is DEFAULT (="default"), then the default machine instance will be returned.
     *
     * @param zoneId
     * @return
     */
    protected ZoneId getZoneId(String zoneId) {
        zoneId = this.getOrDefault(zoneId, Settings.ZONE_ID);
        if (zoneId.isEmpty()) return null; // no Zone
        if (Settings.DEFAULT.equalsIgnoreCase(zoneId)) return ZoneId.systemDefault();
        return ZoneId.of(zoneId);
    }

    protected String getOrDefault(String value, Settings key) {
        if (this.isNullOrEmpty(value)) value = Configuration.getInstance()
                                                            .get(Settings.NUMERIC_INTEGER_FORMAT);
        if (this.isNullOrEmpty(value)) return "";
        return value;
    }

    private boolean isNullOrEmpty(String value) {
        return value == null || value.trim()
                                     .isEmpty();
    }

    private boolean isNullEmptyOrDefault(String value) {
        return this.isNullOrEmpty(value) || Settings.DEFAULT.equalsIgnoreCase(value);
    }
}
