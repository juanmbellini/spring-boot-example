package com.bellotapps.examples.spring_boot_example.webapi.support.constants;

/**
 * Class containing default values.
 */
public class DefaultValues {

    /**
     * The default page number (in string format).
     */
    public static final String DEFAULT_PAGE_NUMBER_STRING = "0";
    /**
     * The default page size (in string format).
     */
    public static final String DEFAULT_PAGE_SIZE_STRING = "25";

    /**
     * The default page number.
     */
    public static final Integer DEFAULT_PAGE_NUMBER = Integer.valueOf(DEFAULT_PAGE_NUMBER_STRING);
    /**
     * The default page size.
     */
    public static final int DEFAULT_PAGE_SIZE = Integer.valueOf(DEFAULT_PAGE_SIZE_STRING);
}
