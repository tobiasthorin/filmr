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

		System.out.println("deserializationContext: " + ctxt);


		System.out.println("!! TEST! in custom deserializer!! ");
		ObjectCodec oc = jp.getCodec();
		TreeNode node = oc.readTree(jp);
		String nodeString = node.toString();
		nodeString = nodeString.substring(1, nodeString.length()-1); // remove brackets

		System.out.println("\n node.toString : " + nodeString);

		System.out.println("Formatted dateString from array values: " + nodeString);
		System.out.println("Adding the stoff");

		System.out.println(nodeString.substring(nodeString.length()-5));

		int timeZoneIndex = nodeString.length()-5;
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
			System.out.println("Formatted dateString from array values: " + dateString);
			nodeString = dateString;//TODO probably a new variable for both
		}
		else if (!nodeString.substring(timeZoneIndex).equals(".000Z")) {
			nodeString += ".000Z";
		}
//		else if (nodeString.length() < 17) { TODO breaks test
//			nodeString += ".00.000Z";
//		}

		Instant instant = Instant.parse(nodeString);
		dateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
		System.out.println("\nTime in custom deserializer TEST: " + dateTime);


		return dateTime;
    }

}
