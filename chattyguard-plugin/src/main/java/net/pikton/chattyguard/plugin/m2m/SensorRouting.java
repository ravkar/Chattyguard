package net.pikton.chattyguard.plugin.m2m;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.dataformat.csv.CsvDataFormat;
import org.apache.commons.csv.CSVStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 
 * @author rwa
 *
 */
public class SensorRouting extends RouteBuilder {
    protected static final Logger LOG = LoggerFactory.getLogger(SensorRouting.class);
    
    @Override
    public void configure() throws Exception {
        getContext().setTracing(true); 
                        
        errorHandler(deadLetterChannel("file:{{file.errors.dir}}").useOriginalMessage());           
        
        CSVStrategy strategy = CSVStrategy.DEFAULT_STRATEGY;        
        strategy.setEncapsulator('%');
        strategy.setEscape('\'');
        CsvDataFormat csv = new CsvDataFormat();        
        csv.setDelimiter(" "); 
        csv.setStrategy(strategy);
                
                 
        from("file:{{file.input.dir}}&sendEmptyMessageWhenIdle=false")
        	.routeId("sensorPreprocessInput")
	      		.log(LoggingLevel.INFO, "net.pikton","Preprocess sensor input")
	        	.unmarshal(csv)
	        	.beanRef("sensorPreprocessor", "parseSensorValue")
	      	    .multicast().to("vm:sensorMonitoring", "vm:sensorCurrentStateInfo"); 
                
	    from("vm:sensorMonitoring")
      	    .routeId("sensorMonitoring")
	      		.log(LoggingLevel.INFO, "net.pikton","Monitoring sensor input")
	      		.setHeader("sensorUpperThreshold",simple("${properties:monitor.sensorUpperThreshold}"))      		
	      		.beanRef("sensorMonitoring", "init")	 
	      		.beanRef("sensorMonitoring", "verifySensorValue")
	        	.split().method("sensorMonitoring", "getAlarms")
	        	.to("vm:alarms");        
	    
	    from("vm:sensorCurrentStateInfo")
  	    	.routeId("sensorCurrentStateInfo")
	      		.log(LoggingLevel.INFO, "net.pikton","Produce sensor current state info")
	      		.beanRef("callProcessor", "setCurrentStateHeaders")	 
	        	.to("vm:voiceCreation");   	    
    }
}
