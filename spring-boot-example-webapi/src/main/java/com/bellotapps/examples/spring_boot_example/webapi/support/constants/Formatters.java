package com.bellotapps.examples.spring_boot_example.webapi.support.constants;

import java.time.format.DateTimeFormatter;

/**
 * Class containing {@link DateTimeFormatter}s to be reused.
 */
public class Formatters {

    /**
     * Contains a {@link DateTimeFormatter} whose pattern is "yyyy-MM-dd".
     */
    public static final DateTimeFormatter CLASSIC_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
}
