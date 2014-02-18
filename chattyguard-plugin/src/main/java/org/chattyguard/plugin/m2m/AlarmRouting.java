package org.chattyguard.plugin.m2m;

import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 
 * @author rwa
 *
 */
public class AlarmRouting extends RouteBuilder {
    protected static final Logger LOG = LoggerFactory.getLogger(AlarmRouting.class);
            
    @Override
    public void configure() throws Exception {
                               
	    from("vm:alarms")
	      	.routeId("alarmExecutionContext")
	      	    .beanRef("callProcessor", "setAlarmHeaders")
	      	    .to("vm:voiceCreation"); 
              	    
        from("vm:voiceCreation")
        	.routeId("voiceCreation")
	      		.beanRef("callProcessor", "setTTSProperties")	
	        	.to("exec:wget")    
	      		.beanRef("callProcessor", "setToGsmProperties")        	
	        	.to("exec:sox")
	        	.choice().when(header("HEADER_ALARM").isNotNull())
	        		.to("vm:callCreation")
	        	.end(); 	        			
	        			
	        	
        from("vm:callCreation")	  
        	.routeId("callCreation")        
	      		.beanRef("callProcessor", "toOutgoingCalls")
	    		.to("file:{{file.output.dir}}");         
        
    }
}
