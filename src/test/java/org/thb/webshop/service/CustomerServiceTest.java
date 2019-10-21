package org.thb.webshop.service;

import java.io.StringWriter;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.junit.Test;
import org.thb.webshop.model.Customer;

import org.junit.Assert; 

public class CustomerServiceTest {
	
	@Test
	public void outOfTheBoxXML() throws JAXBException{
		
		Customer customer = new Customer();
		customer.setId(999);
		customer.setForeName("JUnit");
		customer.setSureName("Test");
		customer.setEmail("JunitTest@SecurityWebShop.de");
		customer.setStreet("Magdeburger Straße");
		customer.setHouseNumber("50");
		customer.setZip("14770");
		customer.setPlace("Brandenburg an der Havel");
		
		String xmlProlog = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?> ";

		String dtdEntity = "<!DOCTYPE lolz [<!ENTITY lol \"abcdefghijklmnopqrstuvwxyz\">"
				+ "<!ELEMENT lolz (#PCDATA)>"
				+ "<!ENTITY lol1 \"&lol;\">"
				+ "<!ENTITY lol2 \"&lol1;&lol1;&lol1;\">"
				+ "<!ENTITY lol3 \"&lol2;&lol2;&lol2;\">"
				+ "<!ENTITY lol4 \"&lol3;&lol3;&lol3;&lol3;\">"
				+ "<!ENTITY lol5 \"&lol4;&lol4;&lol4;&lol4;\">"
				+ "<!ENTITY lol6 \"&lol5;&lol5;&lol5;&lol5;\">"
				+ "<!ENTITY lol7 \"&lol6;&lol6;&lol6;&lol6;\">"
				+ "<!ENTITY lol8 \"&lol7;&lol7;&lol7;&lol7;\">"
				+ "<!ENTITY lol9 \"&lol8;&lol8;&lol8;&lol8;\">]> ";
		
		JAXBContext jaxbContext = JAXBContext.newInstance(Customer.class);
		StringWriter writer = new StringWriter();
		Marshaller marshaller = jaxbContext.createMarshaller();
		marshaller.setProperty("jaxb.fragment", Boolean.TRUE);
		marshaller.marshal(customer, writer);
		
		StringBuilder sb = new StringBuilder();
		sb.append(xmlProlog);
		sb.append(dtdEntity);
		sb.append(writer.toString());
		
		String xmlString = sb.toString();
		
		Client client = ClientBuilder.newClient();
		
		WebTarget target = client.target("http://localhost:8080/SecurityWebShop/rest/Customer/create");
		
		Response response = target.request("text/plain").post(Entity.entity(xmlString, "application/xml"));
		
		int status = response.getStatus();
		
		if(status==200){
			Assert.fail("CustomerService allows parsing XML-Doctype-Definition-Entity");
		}
		
	}

}
