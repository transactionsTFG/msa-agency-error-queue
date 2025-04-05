package domainevent.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.google.gson.Gson;

@Converter
public class JsonConverter implements AttributeConverter<Object, String> {

    private static final Gson GSON = new Gson();

    @Override
    public String convertToDatabaseColumn(Object attribute) {
        return GSON.toJson(attribute);
    }

    @Override
    public Object convertToEntityAttribute(String dbData) {
        return GSON.fromJson(dbData, Object.class);
    }
    
}
