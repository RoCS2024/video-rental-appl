package org.rocs.vra.utils.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Arrays;
import java.util.List;

import static java.util.Collections.emptyList;

/**
 * JPA attribute converter that transforms a list of strings into a
 * semicolon-delimited string for database persistence, and vice versa.
 * This converter is useful for storing collections of strings in a single database column
 * when a separate relationship table is not desired. The delimiter used is a semicolon
 */
@Converter
public class StringListConverter implements AttributeConverter<List<String>, String> {

    /**
     * The delimiter character used to join and split string lists.
     */
    private static final String SPLIT_CHAR = ";";

    /**
     * Converts a list of strings into a semicolon-separated string for database storage.
     */
    @Override
    public String convertToDatabaseColumn(List<String> stringList) {
        return stringList != null ? String.join(SPLIT_CHAR, stringList) : "";
    }

    /**
     * Converts a semicolon-separated string from the database back into a list of strings.
     */
    @Override
    public List<String> convertToEntityAttribute(String string) {
        return string != null ? Arrays.asList(string.split(SPLIT_CHAR)) : emptyList();
    }

}
