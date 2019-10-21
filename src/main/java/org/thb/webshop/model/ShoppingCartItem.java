package org.thb.webshop.model;

import java.io.Serializable;

/**
 * 
 * @author Manuel
 *
 */
public class ShoppingCartItem implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Item item;
	
	private int quantity;
	
	
	/**
	 * Empty constructor
	 */
	public ShoppingCartItem() {
		super();
	}

	/**
	 * @param item
	 * @param quantity
	 */
	public ShoppingCartItem(Item item, int quantity) {
		super();
		this.item = item;
		this.quantity = quantity;
	}

	/**
	 * @return the item
	 */
	public Item getItem() {
		return item;
	}

	/**
	 * @param item the item to set
	 */
	public void setItem(Item item) {
		this.item = item;
	}

	/**
	 * @return the quantity
	 */
	public int getQuantity() {
		return quantity;
	}

	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	/**
	 * 
	 */
	public void incrementQuantity(){
		this.quantity++;
	}
	
	/**
	 * 
	 */
	public void decrementQuantity(){
		this.quantity--;
	}
	
	/**
	 * 
	 * @return
	 */
	public double getTotalPrice(){
		double totalPrice = 0;	
		totalPrice = (this.item.getPrice() * this.quantity);
		return totalPrice;
	}

}
