package ru.otus.homework.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

import java.time.Year;

@ReadingConverter
public class IntToYearConverter implements Converter<Integer, Year> {

    @Override
    public Year convert(Integer source) {
        return Year.of(source);
    }
}
