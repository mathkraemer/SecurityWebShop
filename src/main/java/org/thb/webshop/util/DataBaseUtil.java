package org.thb.webshop.util;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.imageio.ImageIO;

import org.jboss.logging.Logger;
import org.thb.webshop.model.Customer;
import org.thb.webshop.model.Item;


/**
 * 
 * @author Manuel Raddatz
 *
 */
public class DataBaseUtil {
	
	private static final Logger log = Logger.getLogger(DataBaseUtil.class.getName());
	
	private static final int MAX_IMG_LENGTH_= 300;
	
	private String dbDriver;
	private String dbUrl;
	private String dbUser;
	private String dbPassword;
	
	private Connection con = null;
	private Statement objStmt = null;
	
	private static final String URLADDITION ="?characterEncoding=UTF8&autoReconnect=true&useSSL=false";
	
	
	/**
	 * Empty Constructor
	 */
	public DataBaseUtil() {
		super();
	}

	/**
	 * @param dbDriver
	 * @param dbUrl
	 * @param dbUser
	 * @param dbPassword
	 * @throws ClassNotFoundException 
	 */
	public DataBaseUtil(String dbDriver, String dbUrl, String dbUser, String dbPassword) throws ClassNotFoundException, SQLException {
		super();
		this.dbDriver = dbDriver;
		this.dbUrl = dbUrl+URLADDITION;
		this.dbUser = dbUser;
		this.dbPassword = dbPassword;
			
		Class.forName(dbDriver);
		
		con = DriverManager.getConnection(this.dbUrl, this.dbUser, this.dbPassword);
		
		log.info("DataBaseUtil-Instance with Connecton will be created.");
	}
	
	/**
	 * saving given customer in database. the structure with object-creation and -invocation
	 * passes the findbugs "BadPractice" evaluation.
	 * @param customer
	 * @return boolean
	 * @throws SQLException 
	 */
	public boolean persistCustomer(Customer customer) throws SQLException{
		
		boolean result = false;
		
		if(con==null || con.isClosed()){
			con = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
		}
		
		objStmt = con.createStatement();
			
		String insertCustomerSQL = "insert into securitywebshopdb.customer ("
									+ "forename, "
									+ "surename, "
									+ "zip, "
									+ "place, "
									+ "street, "
									+ "housenumber, "
									+ "email, "
									+ "passwd" +
									") VALUES ("+
									"'" + customer.getForeName() + "',"+
									"'" + customer.getSureName() + "',"+
									"'" + customer.getZip() + "',"+
									"'" + customer.getPlace() + "',"+
									"'" + customer.getStreet() + "',"+
									"'" + customer.getHouseNumber() + "',"+
									"'" + customer.getEmail() + "',"+
									"'" + customer.getPassword() + "')";
		
		int res = objStmt.executeUpdate(insertCustomerSQL);
		
		if(res==1){
			result = true;
			log.info("Customer will be added to database: "+customer.toString());
		}
		objStmt.close();
		con.close();
		
		objStmt = null;
		con = null;
	
		return result;
	}
	
	/**
	 * Method for finding the Customer by email and password
	 * @param email
	 * @param password
	 * @return
	 * @throws SQLException 
	 */
	public Customer findCustomer(String email, String password) throws SQLException{
		
		log.info("Trying to find Customer by Email: "+email+ " and Password: "+password);
		
		Customer customer = null;
		
		if(con==null || con.isClosed()){
			con = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
		}
			
		Statement stmt = con.createStatement();
		
		/*	
		String findCustomerSQL = "select * from securitywebshopdb.customer "+
									"where email='"+email +"'"+
									"AND passwd='"+password +"'";
		*/
		
		String findCustomerSQL = "select * from securitywebshopdb.customer where email='"+email+"'AND passwd='"+password+"'";
		
		log.info("Executing Login SQL-Command: "+findCustomerSQL);
		
		ResultSet result = stmt.executeQuery(findCustomerSQL);
		
		if(result.next()){
			customer = new Customer();
			customer.setId(result.getLong("id"));
			customer.setForeName(result.getString("forename"));
			customer.setSureName(result.getString("surename"));
			customer.setZip(result.getString("zip"));
			customer.setPlace(result.getString("place"));
			customer.setStreet(result.getString("street"));
			customer.setHouseNumber(result.getString("housenumber"));
			customer.setEmail(result.getString("email"));
			customer.setPassword(result.getString("passwd"));
		}
		stmt.close();
		con.close();
	
		return customer;
	}
	
	/**
	 * Saving the Item from sell view to database
	 * @param item
	 * @return
	 * @throws SQLException 
	 * @throws IOException 
	 */
	public boolean persistItem(Item item) throws SQLException, IOException{
		boolean result = false;
		
		if(con==null || con.isClosed()){
			con = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
		}
				
		String insertItemSQL =  "insert into securitywebshopdb.item "
								+ "("
								+ "title, "
								+ "description, "
								+ "price, "
								+ "picture, "
								+ "seller_id "
								+ ")"
								+ " VALUES (?, ?, ?, ?, ?)" ;

		PreparedStatement stmt = con.prepareStatement(insertItemSQL, Statement.RETURN_GENERATED_KEYS);
		stmt.setString(1, item.getTitle());
		stmt.setString(2, item.getDescription());
		stmt.setDouble(3, item.getPrice());
		stmt.setBytes(4, this.scalePicture(item.getPicture()));
		stmt.setLong(5, item.getSeller_id());
		
		int res = stmt.executeUpdate();
		if(res==1){
			result = true;
			ResultSet keysResult = stmt.getGeneratedKeys();
			while(keysResult.next()){
				item.setId(keysResult.getLong(1));
			}
		}
		stmt.close();
		con.close();
		
		return result;
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 * @throws SQLException 
	 */
	public Item findItemById(long id) throws SQLException {
		
		Item item = new Item();
		
		if(con==null || con.isClosed()){
			con = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
		}
		
		Statement stmt = con.createStatement();
		
		String findItemSQL = "select id, "
				+ "title, "
				+ "description, "
				+ "price, "
				+ "picture, "
				+ "seller_id, "
				+ "buyer_id, "
				+ "sold "
				+ "from securitywebshopdb.item where id = "+id;
		
		ResultSet result = stmt.executeQuery(findItemSQL);
		
		while(result.next()){
			item.setId(result.getLong("id"));
			item.setTitle(result.getString("title"));
			item.setDescription(result.getString("description"));
			item.setPrice(result.getDouble("price"));
			item.setPicture(result.getBytes("picture"));
			item.setSeller_id(result.getLong("seller_id"));
			item.setBuyer_id(result.getLong("buyer_id"));
			if(result.getTimestamp("sold")!=null){
				Date soldDate = result.getTimestamp("sold");
				item.setSold(soldDate);
			}else{
				item.setSold(null);
			}
		}
		
		return item;
	}
	
	/**
	 * The method is responsible for searching/finding all items from the given search request.
	 * @param text		the provided String from the search input field of the html page
	 * @return 			a list of items which will be found by the request.
	 * @throws SQLException 
	 */
	public List<Item> findItemByText(String text) throws SQLException{
		
		List<Item> items = new ArrayList<>();
		
		if(con==null || con.isClosed()){
			con = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
		}
		
		Statement stmt = con.createStatement();
		
		String findItemSQL = null;
		if(text!=null & text.length()>0){
			findItemSQL = "select id, "
					+ "title, "
					+ "description, "
					+ "price, "
					+ "seller_id, "
					+ "buyer_id, "
					+ "sold "
					+ "from securitywebshopdb.item "+
					"where title like'"+text +"'";
		}else{
			findItemSQL = "select id, "
					+ "title, "
					+ "description, "
					+ "price, "
					+ "seller_id, "
					+ "buyer_id, "
					+ "sold "
					+ "from securitywebshopdb.item";
		}
		
		log.info("Executing FindItembyText SQL-Command: "+findItemSQL);
		
		ResultSet result = stmt.executeQuery(findItemSQL);
		
		while(result.next()){
			
			Item item = new Item();
		
			item.setId(result.getLong("id"));
			item.setTitle(result.getString("title"));
			item.setDescription(result.getString("description"));
			item.setPrice(result.getDouble("price"));
			item.setSeller_id(result.getLong("seller_id"));
			item.setBuyer_id(result.getLong("buyer_id"));
			if(result.getTimestamp("sold")!=null){
				Date soldDate = result.getTimestamp("sold");
				item.setSold(soldDate);
			}else{
				item.setSold(null);
			}
			
			items.add(item);
		}
		
		stmt.close();
		con.close();
		return items;
	}
	
	
	/**
	 * The method returns all item-titles which will be found by the given search request.
	 * The function is used to auto complete the request-text in the hmtl search input field.
	 * @param searchRequest	typed in text in the search input field 
	 * @return suggestions	a Set of Strings which contains the possible titles of found items
	 * @throws SQLException 
	 */
	public Set<String> getItemsFromRequest(String searchRequest) throws SQLException {
		
		Set<String> suggestions = new HashSet<>();
		
		if(con==null || con.isClosed()){
			con = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
		}
		
		String findItemSQL = "SELECT title FROM securitywebshopdb.item WHERE title LIKE ?";
		
		PreparedStatement stmt = con.prepareStatement(findItemSQL);	
		stmt.setString(1, searchRequest+ "%");
		
		ResultSet result = stmt.executeQuery();
		
		while(result.next()){
			String item = result.getString("title");
			
			suggestions.add(item);			
		}
		
		stmt.close();
		con.close();
		
		return suggestions;
	}
	
	/**
	 * 
	 * @param itemId
	 * @param id
	 * @throws SQLException 
	 */
	public boolean updateItem(String itemId, long customerId) throws SQLException {
		
		if(con==null || con.isClosed()){
			con = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
		}
		
		Statement stmt = con.createStatement();
		
		String updateItemSQL = "UPDATE securitywebshopdb.item SET "
								+ "buyer_id = " +customerId 
								+ " WHERE id = " +Long.valueOf(itemId);
				
		boolean result = stmt.execute(updateItemSQL);
			
		stmt.close();
		con.close();
		
		return result;
	}
	
	/**
	 * scaling the uploaded picture into uniform size
	 * @param originalPicture
	 * @return
	 * @throws IOException 
	 */
	private byte[] scalePicture(byte[] originalPicture) throws IOException {
		
		ByteArrayInputStream in = new ByteArrayInputStream(originalPicture);
		
		BufferedImage originalImage = ImageIO.read(in);
		
		double originalWidth = originalImage.getWidth();
		double originalHeigth = originalImage.getHeight();
		
		//which one of the given number is the higher one
		//the higher one is the more relevant Size for scaling action
		double relevantLength = originalWidth > originalHeigth ?
								originalWidth : originalHeigth;
		
		//the scaling factor is calculated from maximum length / relevantLength 
		double scalingFactor = MAX_IMG_LENGTH_ / relevantLength;
		
		int width = (int) Math.round(scalingFactor * originalWidth);
		int height = (int) Math.round(scalingFactor * originalHeigth);
		
		BufferedImage scaledImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		
		Graphics2D g2d = scaledImage.createGraphics();
		g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		
		AffineTransform transform = AffineTransform.getScaleInstance(scalingFactor, scalingFactor);
		
		g2d.drawRenderedImage(originalImage, transform);
		
		ByteArrayOutputStream bOut = new ByteArrayOutputStream();
		ImageIO.write(scaledImage, "PNG", bOut);
		return bOut.toByteArray();
	}

	/**
	 * @return the con
	 */
	public Connection getCon() {
		return con;
	}

	/**
	 * Deliberate bug
         * with another 4rd irrelevant change
	 * @return the con
	 */
	Connection getCon01(String url) throws SQLException {
                return DriverManager.getConnection(url, 
                                    /*username*/ "leroy", 
                                    /*password*/ "jenkins");
	}
}
