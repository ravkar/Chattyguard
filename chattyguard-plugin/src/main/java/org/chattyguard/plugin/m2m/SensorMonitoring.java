package org.chattyguard.plugin.m2m;

import org.chattyguard.plugin.Configuration;
import org.chattyguard.plugin.m2m.Alarm.Application;
import org.chattyguard.plugin.m2m.Alarm.MsgGroup;
import org.chattyguard.plugin.m2m.Alarm.Severity;
import org.apache.camel.Body;
import org.apache.camel.Exchange;
import org.apache.camel.Header;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 
 * @author rwa
 *
 */
public class SensorMonitoring{ 

	private static final String ALARMS_RAISED = "alarmsRaised";
	
	private static final String ALARMS_SENSOR_UPPER_THRESHOLD_VALUE = "sensorUpperThreshold";	
	
	private static final String SOURCE_ID = "call.ctrl.sourceID";
	
	protected static final Logger LOG = LoggerFactory.getLogger(SensorMonitoring.class);


	
	Date mAppStartDate;
	Configuration cfg;				
	public SensorMonitoring(Configuration aCfg){
		mAppStartDate = new Date();	
		this.cfg = aCfg; 
	}
			
	public void verifySensorValue(@Header(ALARMS_RAISED)List<Alarm> anInputAlarms, @Body Integer aCurrSensorValue, @Header(ALARMS_SENSOR_UPPER_THRESHOLD_VALUE)Integer aSensorUpperTresholdValue, Exchange exchange){	       		       
	       List<Alarm> alarms = new ArrayList<Alarm>();	       
	       if (aCurrSensorValue > aSensorUpperTresholdValue){
	    	    alarms.add(new Alarm.AlarmBuilder(Severity.MAJOR, Application.THERMOMETER, MsgGroup.SENSOR_DATA).object(Alarm.Object.SENSOR_UPPER_LIMIT_EXCEEDED.name()).sourceID(cfg.getProperty(SOURCE_ID)).msgText("Alarm. Źródło: " + cfg.getProperty(SOURCE_ID) + " Przekroczony górny próg temperatury. Obecna wartość w stopniach celsjusza:" + aCurrSensorValue/1000L ).build());
	       	    LOG.warn("Current alarms: "+ alarms);	    	    	    	    	   
	       }
	       
	 	   if (alarms.size() > 0){
	 		   anInputAlarms.addAll(alarms);
	 	   }
	}	
	
		
	public void init(Exchange exchange){
		exchange.getIn().setHeader(ALARMS_RAISED, new ArrayList<Alarm>());
	}	
	
	public List<Alarm> getAlarms(@Header(ALARMS_RAISED) List<Alarm> alarms){
		return alarms;
	}
}
