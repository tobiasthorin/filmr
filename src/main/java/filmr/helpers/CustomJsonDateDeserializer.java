package filmr.helpers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import filmr.controllers.TheaterController;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;


// copied from http://blog.chris-ritchie.com/2014/09/localdate-java-8-custom-serializer.html (but changed to LocalDateTime)

public class CustomJsonDateDeserializer extends JsonDeserializer<LocalDateTime> {


	private final static org.apache.log4j.Logger logger = Logger.getLogger(TheaterController.class);

	@Override
    public LocalDateTime deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {

		LocalDateTime dateTime = null;
		
		try {
			
			//  test doesn't enforse write-dates-as-timestamps: false  in application-test.yml
			// so we seem to get the date as an array of numbers, instead of a string.
			//
			
			logger.debug("deserializationContext: " + ctxt);
			
			
			logger.debug("!! TEST! in custom deserializer!! ");
			ObjectCodec oc = jp.getCodec();
			TreeNode node = oc.readTree(jp);
			String nodeString = node.toString(); //should be named better
			nodeString = nodeString.substring(1, nodeString.length()-1); // remove brackets
			
			logger.debug("\n node.toString : " + nodeString);
			
			logger.debug("Formatted dateString from array values: " + nodeString);
			
			logger.debug(nodeString.substring(nodeString.length()-5));
			
			//Convert the string as needed
			if (nodeString.contains(",")){
				String[] datenumbers = nodeString.split(",");
				String format = "%s-%02d-%02dT%02d:%02d:%02d.000Z";
				String dateString =
						String.format(format,
								datenumbers[0],
								Integer.parseInt(datenumbers[1]),
								Integer.parseInt(datenumbers[2]),
								Integer.parseInt(datenumbers[3]),
								Integer.parseInt(datenumbers[4]),
								Integer.parseInt(datenumbers[5]));
				logger.debug("Formatted dateString from array values: " + dateString);
				nodeString = dateString;
			}
			//If string is less than 17, return as LocalDateTime, Timezone has been handled when creating
			else if(nodeString.length()<17){
				if(nodeString.contains(" ")){
					logger.debug("Contains whitespace: "+nodeString);
					nodeString = nodeString.substring(0,10) + "T" + nodeString.substring(11,nodeString.length());
//				String temp = nodeString.substring(0,10);
//				temp = temp + "T"+ nodeString.substring(11,nodeString.length());
//				nodeString = temp;
					logger.info("Datestring after parsing: "+nodeString);
				}
				dateTime = LocalDateTime.parse(nodeString);
				return dateTime;
			}
			else if (!nodeString.substring(nodeString.length()-5).equals(".000Z")) {
				nodeString += ".000Z";
			}
			
			
			Instant instant = Instant.parse(nodeString);
			dateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
			logger.info("Returned String dateTime:" + dateTime.toString());
			logger.debug("\nTime in custom deserializer TEST: " + dateTime);
			
		} catch (Exception e) {
			logger.warn("Date json deserializer exception caught. ");
			// workaround to let the controller know that the date parsing caused an error.
			dateTime = LocalDateTime.of(6, 6, 6, 6, 6); 
		}

		return dateTime;
    }

}
