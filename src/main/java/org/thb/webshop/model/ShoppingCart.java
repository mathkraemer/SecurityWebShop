package org.thb.webshop.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 
 * @author Manuel
 *
 */
public class ShoppingCart implements Serializable {

	private static final long serialVersionUID = 1L;
	
	//private List<ShoppingCartItem> shoppingCartItems;
	private CopyOnWriteArrayList<ShoppingCartItem> shoppingCartItems;
	
	private int totalNumberOfItems;
	
	private double shoppingCartTotalAmount = 0;
	
	/**
	 * Empty constructor. Initializing List of Items and of amount.
	 */
	public ShoppingCart() {
		//shoppingCartItems = new ArrayList<>();
		shoppingCartItems = new CopyOnWriteArrayList<>();
		totalNumberOfItems=0;
		shoppingCartTotalAmount=0;
	}
	
	/**
	 * 
	 * @param item
	 */
	public synchronized void addItem(Item item){
		totalNumberOfItems++;
		if(shoppingCartItems.isEmpty()){
			shoppingCartItems.add(new ShoppingCartItem(item, 1));
		}
		for(ShoppingCartItem i : shoppingCartItems){
			if(item.getId() == i.getItem().getId()){
				i.incrementQuantity();
			}else{
				ShoppingCartItem newItem = new ShoppingCartItem(item, 1);
				shoppingCartItems.add(newItem);
			}
		}
		updateTotalAmount(item.getPrice());
	}
	
	/**
	 * @param d 
	 * 
	 */
	private void updateTotalAmount(double d) {
		shoppingCartTotalAmount += d; 
	}

	/**
	 * 
	 * @param id
	 */
	public synchronized void removeItem(long id){
		for(ShoppingCartItem i : shoppingCartItems){
			if(id == i.getItem().getId()){
				shoppingCartItems.remove(i);
			}
		}
	}

	/**
	 * @return the shoppingCartItems
	 */
	public CopyOnWriteArrayList<ShoppingCartItem> getShoppingCartItems() {
		return shoppingCartItems;
	}

	/**
	 * @param shoppingCartItems the shoppingCartItems to set
	 */
	public void setShoppingCartItems(CopyOnWriteArrayList<ShoppingCartItem> shoppingCartItems) {
		this.shoppingCartItems = shoppingCartItems;
	}

	/**
	 * @return the totalNumberOfItems
	 */
	public int getTotalNumberOfItems() {
		return totalNumberOfItems;
	}

	/**
	 * @param totalNumberOfItems the totalNumberOfItems to set
	 */
	public void setTotalNumberOfItems(int totalNumberOfItems) {
		this.totalNumberOfItems = totalNumberOfItems;
	}

	/**
	 * @return the shoppingCartTotalAmount
	 */
	public double getShoppingCartTotalAmount() {	
		return shoppingCartTotalAmount;
	}

	/**
	 * @param shoppingCartTotalAmount the shoppingCartTotalAmount to set
	 */
	public void setShoppingCartTotalAmount(double shoppingCartTotalAmount) {
		this.shoppingCartTotalAmount = shoppingCartTotalAmount;
	}

}
