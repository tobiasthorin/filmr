package filmr.helpers;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.node.TextNode;

// copied from http://blog.chris-ritchie.com/2014/09/localdate-java-8-custom-serializer.html (but changed to LocalDateTime)

public class CustomJsonDateDeserializer extends JsonDeserializer<LocalDateTime> {
	 @Override
    public LocalDateTime deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
 
		System.out.println("!! in custom deserializer!! ");
        ObjectCodec oc = jp.getCodec();
        TextNode node = (TextNode) oc.readTree(jp);
        String dateString = node.textValue();
        System.out.println("raw javascript Date string in custom deserializer: " + dateString);
 
        Instant instant = Instant.parse(dateString);
        LocalDateTime dateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        System.out.println("Time in custom deserializer: " + dateTime);
        
        return dateTime;
    }

}
