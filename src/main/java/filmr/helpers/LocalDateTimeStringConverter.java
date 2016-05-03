package filmr.helpers;

import org.springframework.core.convert.converter.Converter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by Marco on 2016-05-02.
 */
public class LocalDateTimeStringConverter implements Converter<String, LocalDateTime> {

        private final DateTimeFormatter formatter;

        public LocalDateTimeStringConverter(String dateFormat) {
            this.formatter = DateTimeFormatter.ISO_INSTANT.ofPattern(dateFormat);
        }

        @Override
        public LocalDateTime convert(String source) {
            if (source == null || source.isEmpty()) {
                return null;
            }

            return LocalDateTime.parse(source, formatter);
        }
    }

