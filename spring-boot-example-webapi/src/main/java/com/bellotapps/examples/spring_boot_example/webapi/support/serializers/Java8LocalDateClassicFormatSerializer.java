package com.bellotapps.examples.spring_boot_example.webapi.support.serializers;

import com.bellotapps.examples.spring_boot_example.webapi.support.constants.Formatters;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.time.LocalDate;

/**
 * {@link com.fasterxml.jackson.databind.JsonSerializer} to transform a {@link LocalDate} to a {@link String},
 * using {@link Formatters#CLASSIC_DATE_FORMATTER}.
 */
public class Java8LocalDateClassicFormatSerializer extends StdSerializer<LocalDate> {

    /**
     * Default constructor.
     */
    public Java8LocalDateClassicFormatSerializer() {
        super(LocalDate.class);
    }

    @Override
    public void serialize(LocalDate value, JsonGenerator gen, SerializerProvider provider)
            throws IOException {
        gen.writeString(Formatters.CLASSIC_DATE_FORMATTER.format(value));

    }
}
