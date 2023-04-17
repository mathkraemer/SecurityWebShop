package org.thb.webshop.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.xml.bind.DatatypeConverter;

import org.jboss.logging.Logger;
import org.thb.webshop.model.Item;
import org.thb.webshop.model.WebServiceItem;
import org.thb.webshop.util.DataBaseCredentials;
import org.thb.webshop.util.DataBaseUtil;

/**
 * 
 * @author Manuel Raddatz
 *
 */
@Path("/Item")
public class ItemService {
	
	private static final Logger log = Logger.getLogger(ItemService.class.getName());

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
			log.error("Error connection to DataBase by dbUtil-Class: "+errorMsg);
		}
	}
	
	@GET
    @Path("/find")
    @Produces({"application/xml","application/json"})
	public Response getItem(@QueryParam("searchQuery")String query){
		
		List<Item> items = null;
		
		Response response = null;
		
		try {
			items = dbUtil.findItemByText(query);
		} catch (SQLException e) {
			errorMsg = e.toString();
			response = Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorMsg).build();
			log.error("Error getting Item by query: "+errorMsg);
		}
		
		if(items!=null){
			response = Response.status(Status.NOT_FOUND).entity("No items for given search string: "+query).build();
		}else{
			response = Response.status(Status.OK).entity(items).build();
		}	
		return response;
	}
	
	@POST
	@Path("/create")
	@Consumes("application/xml")
	@Produces("text/plain")
	public Response createItem(WebServiceItem tempItem){
		
		Item item  = null;
		
		if(tempItem!=null){
			try {
				item = new Item();
				item.setTitle(tempItem.getTitle());
				item.setDescription(tempItem.getDescription());
				item.setPrice(tempItem.getPrice());
				item.setPicture(DatatypeConverter.parseBase64Binary(tempItem.getBase64Picture()));
				item.setSeller_id(tempItem.getSeller_id());
		
				dbUtil.persistItem(item);
				
				log.info("Creating Item in DB: "+item.getTitle() +" -Buyer:  "+item.getBuyer_id());
				return Response.status(200).entity("ok: item created: ").build();
			} catch (SQLException e) {
				return Response.status(500).entity(e.toString()).build();
			} catch (IOException e) {
				return Response.status(500).entity(e.toString()).build();
			}
		}else{
			return Response.status(400).entity("error item to create is null:").build();
		}
	}

	public String testme(Object o){
		if (o != null){
			return o.toString();
		} else {
			return "null-ref";
		}
	}
}
