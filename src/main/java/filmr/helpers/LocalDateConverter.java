package filmr.helpers;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.sql.Date;
import java.time.LocalDate;


/**
 * Created by Marco on 2016-04-08.
 */
@Converter(autoApply = true)
public class LocalDateConverter implements AttributeConverter<LocalDate, Date> {

    @Override
    public Date convertToDatabaseColumn(LocalDate localDate) {
        return (localDate == null ? null: Date.valueOf(localDate));
    }

    @Override
    public LocalDate convertToEntityAttribute(Date date) {
        return (date == null ? null : date.toLocalDate());
    }
}
