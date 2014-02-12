package net.pikton.chattyguard.plugin.m2m;

import org.apache.camel.Exchange;
import org.apache.camel.component.file.GenericFile;
import org.apache.camel.component.file.GenericFileEndpoint;
import org.apache.camel.component.file.GenericFileOperations;
import org.apache.camel.component.file.GenericFileProcessStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author rwa
 *
 * @param <T>
 */
public class SensorFilesProcessStrategy <T> implements GenericFileProcessStrategy<T> {

   protected static final Logger LOG = LoggerFactory.getLogger(SensorFilesProcessStrategy.class);

	
   public SensorFilesProcessStrategy(){
   }

	@Override
	public void prepareOnStartup(GenericFileOperations<T> operations,GenericFileEndpoint<T> endpoint) throws Exception {
	}
	
	@Override
	public boolean begin(GenericFileOperations<T> operations,GenericFileEndpoint<T> endpoint, Exchange exchange, GenericFile<T> file) throws Exception {
		return true;
	}
	
	@Override
	public void abort(GenericFileOperations<T> operations,GenericFileEndpoint<T> endpoint, Exchange exchange, GenericFile<T> file)throws Exception {
	}
	
	@Override
	public void commit(GenericFileOperations<T> operations,GenericFileEndpoint<T> endpoint, Exchange exchange, GenericFile<T> file)throws Exception {
	}
	
	@Override
	public void rollback(GenericFileOperations<T> operations,GenericFileEndpoint<T> endpoint, Exchange exchange, GenericFile<T> file)throws Exception {
	}

}