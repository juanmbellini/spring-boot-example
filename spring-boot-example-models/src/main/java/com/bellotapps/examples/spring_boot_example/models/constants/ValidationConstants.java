package com.bellotapps.examples.spring_boot_example.models.constants;

import java.time.LocalDate;

/**
 * Created by Juan Marcos Bellini on 12/8/17.
 * Questions at jbellini@bellotsapps.com
 */
public class ValidationConstants {

    // ==================================
    // Minimum values
    // ==================================

    public final static int NAME_MIN_LENGTH = 1;
    public final static LocalDate MIN_BIRTH_DATE = LocalDate.MIN;
    public final static int USERNAME_MIN_LENGTH = 4;
    public final static int EMAIL_MIN_LENGTH = 4;
    public final static int PASSWORD_MIN_LENGTH = 8;


    // ==================================
    // Maximum values
    // ==================================

    public final static int NAME_MAX_LENGTH = 64;
    public final static int USERNAME_MAX_LENGTH = 64;
    public final static int EMAIL_MAX_LENGTH = 254;
    public final static int PASSWORD_MAX_LENGTH = 1024;
}
