package org.thb.webshop.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class WebServiceItem implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/**
	* primary key which will be generated automatically in databse
	*/
	private long id;
	
	private String title;
	
	private String description;
	
	private double price;
	
	private String base64Picture;
	
	/**
	 * Foreign keys from the customer database table. the buyer_id is by default null.  
	 */
	private long seller_id;
	
	public WebServiceItem() {
		super();
	}

	/**
	 * @param id
	 * @param title
	 * @param description
	 * @param price
	 * @param base64Picture
	 * @param seller_id
	 */
	public WebServiceItem(long id, String title, String description, double price, String base64Picture,
			long seller_id) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.price = price;
		this.base64Picture = base64Picture;
		this.seller_id = seller_id;
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the price
	 */
	public double getPrice() {
		return price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(double price) {
		this.price = price;
	}

	/**
	 * @return the base64Picture
	 */
	public String getBase64Picture() {
		return base64Picture;
	}

	/**
	 * @param base64Picture the base64Picture to set
	 */
	public void setBase64Picture(String base64Picture) {
		this.base64Picture = base64Picture;
	}

	/**
	 * @return the seller_id
	 */
	public long getSeller_id() {
		return seller_id;
	}

	/**
	 * @param seller_id the seller_id to set
	 */
	public void setSeller_id(long seller_id) {
		this.seller_id = seller_id;
	}

	/**
	 * printing all values
	 */
	@Override
	public String toString() {
		return "WebServiceItem [id=" + id + ", title=" + title + ", description=" + description + ", price=" + price
				+ ", base64Picture=" + base64Picture + ", seller_id=" + seller_id + "]";
	}

}
