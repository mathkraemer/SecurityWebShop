package org.thb.webshop.jaxrs;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.Consumes;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.Provider;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.jboss.logging.Logger;
import org.thb.webshop.model.WebServiceItem;
import org.xml.sax.InputSource;

/**
 * 
 * @author Manuel Raddatz
 *
 */
@Provider
@Consumes("application/xml")
public class ItemMessageBodyReader implements MessageBodyReader<WebServiceItem> {
	
	private static final Logger log = Logger.getLogger(ItemMessageBodyReader.class.getName());

	@Override
	public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
		return (type == WebServiceItem.class);
	}

	@Override
	public WebServiceItem readFrom(Class<WebServiceItem> type, Type genericType, Annotation[] annotations, MediaType mediaType,
			MultivaluedMap<String, String> httpHeaders, InputStream entityStream)
			throws IOException, WebApplicationException {
		
		WebServiceItem tempItem = null;
		
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(WebServiceItem.class);
			
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			
			Reader reader = new InputStreamReader(entityStream, "UTF-8");
			
			InputSource source = new InputSource(reader);
			
			source.setEncoding("UTF-8");

			tempItem = (WebServiceItem) unmarshaller.unmarshal(source);
			
		} catch (JAXBException e) {
			log.error("JaxbException while parsing WebServiceItem-XML: "+e.getMessage());
		} 	
		return tempItem;	
	}

}
