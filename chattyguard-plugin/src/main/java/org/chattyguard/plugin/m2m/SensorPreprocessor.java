package org.chattyguard.plugin.m2m;

import org.apache.camel.Body;
import org.apache.camel.Exchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * 
 * @author rwa
 *
 */
public class SensorPreprocessor {

	protected static final Logger LOG = LoggerFactory.getLogger(SensorPreprocessor.class);
	
	public SensorPreprocessor(){
	}
					
	public void prepareToMarshal(@Body List<List<String>> aRecords, Exchange exchange){
		List<Map<String, Object>> resultBody  = new LinkedList<Map<String, Object>>();
		for (List<String> record : aRecords){
			SortedMap<String,Object> row = new TreeMap<String, Object>();
			for (int i = 0; i < record.size(); i ++){	
				int d = 'a' + i/('z' - 'a');
				int dd = 'a' + i%('z' -'a');
				
				row.put("" + (char) (d) + (char)dd , record.get(i));
			}
			resultBody.add(row);
		}
		exchange.getIn().setBody(resultBody);
	}	
				
    	
	public void parseSensorValue(@Body List<List<String>> aRecords, Exchange exchange) throws Exception{		
		LOG.info("parseSensorValue:" + aRecords.size());		
		
		String value = null;
		if (aRecords.size() == 2){
			List<String> rec1 = aRecords.get(0);
			boolean isValueReady = rec1.get(rec1.size() - 1).equalsIgnoreCase("YES");
			if (isValueReady){
				List<String> rec2 = aRecords.get(1);
				String kv = rec2.get(rec2.size() - 1);	
				String[] tokens = kv.split("=");
				value = tokens[1];
			}
		}
		LOG.info("value:" + value);						
		
		Integer result = null;
		try {
			result = Integer.decode(value);
		} catch (Exception e) {
		}
		
		exchange.getIn().setBody(result);
	}
}
