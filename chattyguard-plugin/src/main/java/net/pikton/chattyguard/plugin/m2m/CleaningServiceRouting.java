package net.pikton.chattyguard.plugin.m2m;

import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 
 * @author rwa
 *
 */
public class CleaningServiceRouting extends RouteBuilder {
    protected static final Logger LOG = LoggerFactory.getLogger(CleaningServiceRouting.class);
    
    @Override
    public void configure() throws Exception {
                               	    
        from("file:{{file.resources.dir}}?delete=true&filter=#cleaningFilesStrategy")   
    		.routeId("cleaningService")
    		.to("mock:devNull");       
    }
}
