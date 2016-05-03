package filmr.helpers;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.util.SystemPropertyUtils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.node.TextNode;

import ch.qos.logback.core.net.SyslogOutputStream;

// copied from http://blog.chris-ritchie.com/2014/09/localdate-java-8-custom-serializer.html (but changed to LocalDateTime)

public class CustomJsonDateDeserializer extends JsonDeserializer<LocalDateTime> {
	
	@Autowired
	private Environment environment;
	
	@Override
    public LocalDateTime deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
		
		LocalDateTime dateTime = LocalDateTime.now();
		
		//  test doesn't enforse write-dates-as-timestamps: false  in application-test.yml
		// so we seem to get the date as an array of numbers, instead of a string.
		// 
		String[] activeProfiles = environment.getActiveProfiles();
		if(activeProfiles.length != 0 && activeProfiles[0].equalsIgnoreCase("test")) {
			
			System.out.println("Active profile: " + activeProfiles[0]);
			
			System.out.println("deserializationContext: " + ctxt);
			
			
			System.out.println("!! TEST! in custom deserializer!! ");
			ObjectCodec oc = jp.getCodec();
			TreeNode node = oc.readTree(jp);
			String nodeString = node.toString();
			nodeString = nodeString.substring(1, nodeString.length()); // remove brackets
			
			System.out.println("\n node.toString : " + nodeString);
			String[] datenumbers = nodeString.split(",");
			// we want this format  2016-05-04T04:00:00.000Z 
			String format = "%s-%02d-%02dT%02d:%02d:%02d.000Z";
			String dateString = 
					String.format(format, 
							datenumbers[0],
							Integer.parseInt(datenumbers[1]),
							Integer.parseInt(datenumbers[2]), 
							Integer.parseInt(datenumbers[3]), 
							Integer.parseInt(datenumbers[4]), 
							Integer.parseInt(datenumbers[5]));
			                                
			System.out.println("Formatted dateString from array values: " + dateString);
			
			Instant instant = Instant.parse(dateString);
			dateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
			System.out.println("\nTime in custom deserializer TEST: " + dateTime);
			
			
		} else {
			
			System.out.println("!! in custom deserializer!! ");
			ObjectCodec oc = jp.getCodec();
			TextNode node = (TextNode) oc.readTree(jp);
			String dateString = node.textValue();
			System.out.println("\n raw javascript Date string in custom deserializer: " + dateString);
			
//        if(! dateString.contains("Z")) {
//        	dateString += "Z";
//        }
			
			Instant instant = Instant.parse(dateString);
			dateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
			System.out.println("\nTime in custom deserializer: " + dateTime);
		}
        
        return dateTime;
    }

}
