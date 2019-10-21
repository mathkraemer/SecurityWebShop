package org.thb.webshop.service;

import java.sql.SQLException;

import javax.annotation.PostConstruct;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.jboss.logging.Logger;
import org.thb.webshop.model.Customer;
import org.thb.webshop.util.DataBaseCredentials;
import org.thb.webshop.util.DataBaseUtil;

/**
 * Webservice for customer specific functions.
 * @author Manuel Raddatz
 *
 */
@Path("/Customer")
public class CustomerService {
	
	private static final Logger log = Logger.getLogger(CustomerService.class.getName());

	private DataBaseUtil dbUtil;
	
	private String errorMsg;

	@PostConstruct
	public void init(){
		try {
			dbUtil = new DataBaseUtil(DataBaseCredentials.DB_DRIVER,
									  DataBaseCredentials.DB_URL,
									  DataBaseCredentials.DB_USER,
									  DataBaseCredentials.DB_PASSWORD);
		} catch (ClassNotFoundException | SQLException e) {
			errorMsg = e.toString();
		}
	}
	
    @GET
    @Path("/get")
    @Produces({"application/xml","application/json"})
    public Response getCustomer(@QueryParam("email") String email,
    							@QueryParam("password") String password) {
		
    	Customer customer = null;
    	
    	try {
			customer = dbUtil.findCustomer(email, password);
		} catch (SQLException e) {
			errorMsg = e.toString();
		}
    	
    	Response response = null;
    	
    	if(customer != null){
    		response = Response.status(200).entity(customer).build();
    	}else{
    		errorMsg = "Customer not Found.";
    		response = Response.status(404).entity(errorMsg).build();
    	}
    	return response;
    }
	
	
	@POST
	@Path("/create")
	@Consumes("application/xml")
	@Produces("text/plain")
	public Response createCustomer(Customer customer){
		
		if(customer!=null){
			try {
				dbUtil.persistCustomer(customer);
			} catch (SQLException e) {
				return Response.status(500).entity(e.toString()).build();
			}
			String emailFromCustomer = customer.getEmail();
			log.info("Creating Customer in DB: "+customer.getSureName() +", "+customer.getForeName());
			return Response.status(200).entity("ok: Length of Email-String: "+emailFromCustomer.length()).build();
		}else{
			return Response.status(400).entity("error customer to create is null:").build();
		}
	}

}
