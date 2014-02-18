package org.chattyguard.plugin.m2m;

import org.chattyguard.plugin.Configuration;
import org.apache.camel.component.file.GenericFile;
import org.apache.camel.component.file.GenericFileFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Comparator;

/**
 * 
 * @author rwa
 *
 * @param <T>
 */
public class CleaningFilesStrategy <T> implements Comparator<GenericFile<T>>,  GenericFileFilter<T> {
	static final String CFG_FILE_CLEANING_FILTER_MAX_MINUTES 	= "file.cleaning.maxMinutes";

	protected static final Logger LOG = LoggerFactory.getLogger(CleaningFilesStrategy.class);
	
	private int cleaningFilterMaxMinutes;
	
   public CleaningFilesStrategy(Configuration aCfg){
	   String val = aCfg.getProperty(CFG_FILE_CLEANING_FILTER_MAX_MINUTES, "5");
	   cleaningFilterMaxMinutes = Integer.parseInt(val);
   }
	/**
	 * File sorter by names desc
	 */
	public int compare(GenericFile<T> o1, GenericFile<T> o2) {
        return o2.getFileName().compareToIgnoreCase(o1.getFileName());
    }
	
	/**
	 * File filter by time 
	 */
    public boolean accept(GenericFile<T> file) {
 	    boolean accept = file.getLastModified() < (System.currentTimeMillis() - cleaningFilterMaxMinutes*60*1000L);
 	    return accept;
    }	
}