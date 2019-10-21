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
import org.thb.webshop.model.Customer;
import org.xml.sax.InputSource;

/**
 * Special MessagBodyReader for a specific unmarshalling action for a customer object
 * @author Manuel
 *
 */
@Provider
@Consumes("application/xml")
public class CustomerMessageBodyReader implements MessageBodyReader<Customer> {

	private static final Logger log = Logger.getLogger(CustomerMessageBodyReader.class.getName());
	
	@Override
	public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
		return (type == Customer.class);
	}

	/**
	 * Method to override for defining a custom unmarshaller
	 */
	@Override
	public Customer readFrom(Class<Customer> type, Type genericType, Annotation[] annotations, MediaType mediaType,
			MultivaluedMap<String, String> httpHeaders, InputStream entityStream)
			throws IOException, WebApplicationException {
		
		Customer c = null;
		
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(Customer.class);
			
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			
			Reader reader = new InputStreamReader(entityStream, "UTF-8");
			
			InputSource source = new InputSource(reader);
			
			source.setEncoding("UTF-8");

			c = (Customer) unmarshaller.unmarshal(source);
			
		} catch (JAXBException e) {
			log.error("Jaxb Exception while parsing Customer-XML: "+e.getMessage());
		} 
		return c;
	}
}
