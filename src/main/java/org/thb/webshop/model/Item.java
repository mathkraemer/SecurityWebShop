package org.thb.webshop.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

/**
 * Model claas of item object
 * @author Manuel
 *
 */
public class Item implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	* primary key which will be generated automatically in databse
	*/
	private long id;
	
	private String title;
	
	private String description;
	
	private double price;
	
	private byte[] picture;
	
	/**
	 * Foreign keys from the customer database table. the buyer_id is by default null.  
	 */
	private long seller_id;
	private long buyer_id;
	
	private Date sold;

	/**
	 * Empty constructor
	 */
	public Item() {
		super();
	}

	/**
	 * @param id
	 * @param title
	 * @param description
	 * @param price
	 * @param picture
	 * @param seller_id
	 * @param buyer_id
	 * @param sold
	 */
	public Item(long id, String title, String description, double price, byte[] picture, long seller_id, long buyer_id,
			Date sold) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.price = price;
		this.picture = picture;
		this.seller_id = seller_id;
		this.buyer_id = buyer_id;
		this.sold = sold;
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
	 * @return the picture
	 */
	public byte[] getPicture() {
		return picture;
	}

	/**
	 * @param picture the picture to set
	 */
	public void setPicture(byte[] picture) {
		this.picture = picture;
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
	 * @return the buyer_id
	 */
	public long getBuyer_id() {
		return buyer_id;
	}

	/**
	 * @param buyer_id the buyer_id to set
	 */
	public void setBuyer_id(long buyer_id) {
		this.buyer_id = buyer_id;
	}

	/**
	 * @return the sold
	 */
	public Date getSold() {
		return sold;
	}

	/**
	 * @param sold the sold to set
	 */
	public void setSold(Date sold) {
		this.sold = sold;
	}

	/**
	 * Returning the values of the object-attributes.
	 */
	@Override
	public String toString() {
		return "Item [id=" + id + ", title=" + title + ", description=" + description + ", price=" + price
				+ ", picture=" + Arrays.toString(picture) + ", seller_id=" + seller_id + ", buyer_id=" + buyer_id
				+ ", sold=" + sold + "]";
	}

}
