package net.pikton.chattyguard.plugin;

import org.apache.camel.spring.Main;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 	
 * @author rwa
 *
 */
public class Mediation {	
	
    protected static final Logger LOG = LoggerFactory.getLogger(Mediation.class);
        
    Mediation() throws Exception{
    	final Object lock = new Object();  	
    	
        final Main main = new Main();        
    	main.setApplicationContextUri("mediation.xml");        
        main.enableHangupSupport();
		if (LOG.isDebugEnabled()){
			main.enableTrace();
		}

		try {						
			main.start();			
	        LOG.warn("Chattyguard module has been started.");
			synchronized(lock){
				lock.wait();
			}
		} catch (Exception e) {
			 LOG.error("Chattyguard module can't be started.");
		}finally{
	         LOG.warn("Chattyguard module has been stopped.");
		}  	
    }
            
    public static void main(String[] args) throws Exception {   
    	new Mediation();
    }	
    
}
