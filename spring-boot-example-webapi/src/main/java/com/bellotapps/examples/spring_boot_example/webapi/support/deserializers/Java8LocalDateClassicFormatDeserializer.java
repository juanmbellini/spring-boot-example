package com.bellotapps.examples.spring_boot_example.webapi.support.deserializers;

import com.bellotapps.examples.spring_boot_example.webapi.support.constants.Formatters;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * {@link com.fasterxml.jackson.databind.JsonDeserializer} to transform a {@link String} to a {@link LocalDate},
 * using {@link Formatters#CLASSIC_DATE_FORMATTER}.
 */
public class Java8LocalDateClassicFormatDeserializer extends StdDeserializer<LocalDate> {

    /**
     * Default constructor.
     */
    protected Java8LocalDateClassicFormatDeserializer() {
        super(LocalDate.class);
    }

    @Override
    public LocalDate deserialize(JsonParser p, DeserializationContext context)
            throws IOException {
        final String dateString = p.getText();
        try {
            return LocalDate.from(Formatters.CLASSIC_DATE_FORMATTER.parse(dateString));
        } catch (DateTimeParseException e) {
            throw new JsonParseException(p, "Unable to deserialize the date", e);
        }
    }
}
