package com.bellotapps.examples.spring_boot_example.web.support.annotations;

import com.bellotapps.examples.spring_boot_example.web.support.data_transfer.DateTimeFormatters;

import java.lang.annotation.*;
import java.time.format.DateTimeFormatter;

/**
 * Indicates that parameter is one of the Java 8 time types.
 */
@Target({ElementType.PARAMETER,})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Java8Time {

    /**
     * @return The {@link DateTimeFormatter} to be used to create the Java 8 type.
     */
    DateTimeFormatters formatter();
}