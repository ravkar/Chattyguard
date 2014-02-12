package net.pikton.chattyguard.plugin;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;


/**
 * 
 * @author rwa
 *
 */
public class Configuration extends Properties{	
	private static final long serialVersionUID = -8877226667474521806L;
	
	protected static final Logger LOG = LoggerFactory.getLogger(Configuration.class);	
	    
    public Configuration() {
	}
	
    public Configuration(Resource aLocation) throws Exception{
        LOG.debug("cfg file exists->" + aLocation.exists());
		LOG.debug("loading cfg file->" + aLocation.getFilename());
    	InputStream is = aLocation.getInputStream();
        try {
            super.load(is);
            LOG.info("cfg file->" + this);
        } catch (IOException e) {
            LOG.error("cfg load failed", e);
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } finally {
            is.close();
        }
    }	
    
    public Configuration(Configuration aClonedConf){
    	super(aClonedConf);
    }
    
    public Configuration getSubConfiguration(String prefix) {
        Configuration c = new Configuration();
        Enumeration keys = propertyNames();
        while (keys.hasMoreElements()) {
            String key = (String) keys.nextElement();

            if (key.startsWith(prefix + ".")) {
                c.setProperty(key.substring(prefix.length() + 1), getProperty(key));
            }
        }
        return c;
    }    
    
    String toStringPrefix(List<String> list, String separator){
    	String result = "";
    	Iterator<String> it = list.iterator();
    	for (int i = 0 ; i < list.size(); i++){
    		result += list.get(i);
    		if (i < (list.size() -1) )
    			result += separator;
    	}
    	return result;
    }
    
    public List<Configuration> getIndexedSubconfigurations(String prefix) {    	
    	Map<String,Configuration> result = new TreeMap<String, Configuration>();
        String prefixWithDot = prefix + "."; 
        
        for (Enumeration en = propertyNames(); en.hasMoreElements();) {
            String key = (String) en.nextElement();
            
            if (key.startsWith(prefixWithDot)) {
            	String[] splited = key.split("\\.");
            	if (splited.length > 2){
            		String idx = splited[splited.length - 2];	
            		if (result.get(idx) == null){
            			result.put(idx, new Configuration());
            		}
            		List<String> elems=  Arrays.asList(splited);            		
        			result.get(idx).put(toStringPrefix(elems.subList(splited.length - 1, elems.size()), "."), getProperty(key));
            	}    	
            } 	            
        }
        List<Configuration> ret = new ArrayList<Configuration>(result.values());
        return ret;
    }
    
    public String getProperty(String key) {
    	String v = super.getProperty(key);
    	return (v!=null?v.trim():v);
    }    
    
}
