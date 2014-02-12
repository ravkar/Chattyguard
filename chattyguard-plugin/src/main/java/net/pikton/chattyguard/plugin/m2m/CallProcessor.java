package net.pikton.chattyguard.plugin.m2m;

import net.pikton.chattyguard.plugin.Configuration;
import org.apache.camel.Body;
import org.apache.camel.Exchange;
import org.apache.camel.Header;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author rwa
 *
 */
public class CallProcessor {

	protected static final Logger LOG = LoggerFactory.getLogger(CallProcessor.class);

	private static final String CALLED_MSISDN = "call.ctrl.calledMsisdn";
	private static final String WAIT_TIME 	  = "call.ctrl.waitTime";
	private static final String RETRY_TIME 	  = "call.ctrl.retryTime";
	private static final String MAX_RETRIES   = "call.ctrl.maxRetries";
	private static final String SOURCE_ID 	  = "call.ctrl.sourceID";
	
	private static final String FILE_RESOURCE_DIR = "file.resources.dir";
	
	private static final String HEADER_GSM_FILE 	= "HEADER_GSM_FILE";
	private static final String HEADER_MP3_FILE 	= "HEADER_MP3_FILE";
	private static final String HEADER_EVENT_ID 	= "HEADER_EVENT_ID";
	private static final String HEADER_MSG_TEXT 	= "HEADER_MSG_TEXT";
	private static final String HEADER_ALARM 		= "HEADER_ALARM";
	
	Configuration cfg;	
	
	public CallProcessor(Configuration aConfig){				
		this.cfg = aConfig;
	}	
	
    public void setTTSProperties(@Header(HEADER_EVENT_ID) String anEventID, @Header(HEADER_MSG_TEXT)String aMsgText, Exchange exchange) throws Exception {
    	   String mp3FileName = cfg.getProperty(FILE_RESOURCE_DIR) + "/" + anEventID + ".mp3";
	       List<String> list = new ArrayList<String>();
	       list.add("-q");	       
	       list.add("-U \"Mozilla/5.0 (X11; U; Linux i686; pl-PL; rv:1.9.1.4) Gecko/20091027 Fedora/3.5.4-1.fc12 Firefox/3.5.4\"");
	       list.add("-O" + mp3FileName);
	       list.add("http://translate.google.com/translate_tts?tl=pl&q="+ aMsgText);		       
	       LOG.debug("TTS args list->" + list);
	       
	       exchange.getIn().setHeader(HEADER_MP3_FILE, mp3FileName);	       
	       exchange.getIn().setHeader(org.apache.camel.component.exec.ExecBinding.EXEC_COMMAND_ARGS, list);
	}
    
    public void setAlarmHeaders(@Body Alarm anAlarm, Exchange exchange) throws Exception{
 	   	   String eventID = (Math.abs(exchange.getExchangeId().hashCode()) + "").trim(); 
	       exchange.getIn().setHeader(HEADER_EVENT_ID, eventID);	 	   	   
	       exchange.getIn().setHeader(HEADER_MSG_TEXT, anAlarm.getMsgText());
	       exchange.getIn().setHeader(HEADER_ALARM, anAlarm);	       
    }
    
    public void setCurrentStateHeaders(@Body Integer aCurrSensorValue, Exchange exchange) throws Exception{
	   	   String eventID = "CURR_SENSOR_VALUE"; 
	       exchange.getIn().setHeader(HEADER_EVENT_ID, eventID);	 	   	   
	       exchange.getIn().setHeader(HEADER_MSG_TEXT, "Informacja. Źródło: " + cfg.getProperty(SOURCE_ID) + ". Aktualna temperatura w stopniach celsjusza: " + aCurrSensorValue/1000L);
    }    
    
    public void setToGsmProperties(@Header(HEADER_EVENT_ID) String anEventID, @Header(HEADER_MP3_FILE) String anInputFile, Exchange exchange) throws Exception {
    	   String gsmFileName = cfg.getProperty(FILE_RESOURCE_DIR) + "/" + anEventID + ".gsm";
    	   List<String> list = new ArrayList<String>();
	       list.add(anInputFile);    	   
	       list.add("-r8000");	       
	       list.add("-c1");	    
	       list.add(gsmFileName);	    	       
	       LOG.debug("to GSM args list->" + list);
	       LOG.debug("gsmFileName->" + gsmFileName);	       
	       
	       exchange.getIn().setHeader(HEADER_GSM_FILE, gsmFileName);
	       exchange.getIn().setHeader(org.apache.camel.component.exec.ExecBinding.EXEC_COMMAND_ARGS, list);
	}    
    
	public String toOutgoingCalls(@Header(HEADER_EVENT_ID) String anEventID, Exchange exchange){
	  exchange.getIn().setHeader(Exchange.FILE_NAME, anEventID + ".call");		    
	  StringBuilder sb = new StringBuilder();
  	  sb.append("Channel: SIP/" + cfg.getProperty(CALLED_MSISDN)  + "\n");
  	  sb.append("Application: Playback\n"); 
  	  sb.append("Data: " + cfg.getProperty(FILE_RESOURCE_DIR) + "/" + anEventID + "\n");   	  
  	  sb.append("WaitTime: " + cfg.getProperty(WAIT_TIME) + "\n");  	  
  	  sb.append("RetryTime: " + cfg.getProperty(RETRY_TIME)  + "\n"); 
  	  sb.append("MaxRetries: "+ cfg.getProperty(MAX_RETRIES) +"\n"); 	 
  	  sb.append("CallerID: \"" + cfg.getProperty(SOURCE_ID) +  "\" \n");   	  
      return sb.toString(); 
	}
}
