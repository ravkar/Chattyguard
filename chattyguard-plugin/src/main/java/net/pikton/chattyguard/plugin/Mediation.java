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
	        LOG.warn("M2M plugin has been started.");   
			synchronized(lock){
				lock.wait();
			}
		} catch (Exception e) {
			 LOG.error("M2M plugin can't be started.");        
		}finally{
	         LOG.warn("M2M plugin has been stopped.");  
		}  	
    }
            
    public static void main(String[] args) throws Exception {   
    	new Mediation();
    }	
    
}
