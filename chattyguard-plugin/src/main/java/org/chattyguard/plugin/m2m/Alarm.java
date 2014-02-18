package org.chattyguard.plugin.m2m;

/**
 * 
 * @author rwa
 *
 */
public class Alarm {
	
	public enum Severity {CRITICAL, MAJOR, MINOR, WARNING};
	
	public enum MsgGroup {SENSOR_DATA};	
	
	public enum Object {SENSOR_UPPER_LIMIT_EXCEEDED};	
	
	public enum Application {THERMOMETER(1);
       private int app;
       private Application(int value) {
               this.app = value;
       }		
	};
	
	private final Severity severity; 
	private final Application application; 
	private final String object;
	private final String msgText;
	private final MsgGroup msgGroup;
	private final String sourceID;	
	
	
	public Alarm(AlarmBuilder builder){
		this.severity 	 = builder.severity;
		this.application = builder.application;
		this.object 	 = builder.object;
		this.msgText 	 = builder.msgText;
		this.msgGroup 	 = builder.msgGroup;
		this.sourceID 	 = builder.sourceID;		
	}
	
	public Severity getSeverity() {
		return severity;
	}

	public Application getApplication() {
		return application;
	}

	public String getObject() {
		return object;
	}

	public String getMsgText() {
		return msgText;
	}

	public String getSourceID() {
		return sourceID;
	}	
	
	public MsgGroup getMsgGroup() {
		return msgGroup;
	}
		
	@Override
	public String toString() {
		return "Alarm [severity=" + severity + ", application=" + application
				+ ", object=" + object + ", msgText=" + msgText + ", msgGroup="
				+ msgGroup + "]";
	}

	public static class AlarmBuilder{
		public String sourceID;
		private final Severity severity; 
		private final Application application; 
		private String object;
		private String msgText;
		private final  MsgGroup msgGroup;
		
		public AlarmBuilder(Severity severity, Application application, MsgGroup msgGroup){
			this.severity = severity;
			this.application = application;
			this.msgGroup = msgGroup;
		}
		
		public AlarmBuilder object(String object){
			this.object = object;
			return this;
		}
		
		public AlarmBuilder msgText(String msgText){
			this.msgText = msgText;
			return this;
		}		
		
		public AlarmBuilder sourceID(String sourceID){
			this.sourceID = sourceID;
			return this;
		}			
		
		public Alarm build(){
			return new Alarm(this);
		}
	}	
}
