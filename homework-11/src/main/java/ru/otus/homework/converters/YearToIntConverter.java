package ru.otus.homework.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

import java.time.Year;

@WritingConverter
public class YearToIntConverter implements Converter<Year, Integer> {

    @Override
    public Integer convert(Year source) {
        return source.getValue();
    }
}
