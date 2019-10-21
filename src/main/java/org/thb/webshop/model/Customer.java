package org.thb.webshop.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Model class for customer object
 * @author Manuel Raddatz
 *
 */
@XmlRootElement
public class Customer implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * primary key which will be generated automatically in databse
	 */
	private long id;
	
	private String foreName;
	
	private String sureName;
	
	private String place;
	
	private String zip;
	
	private String street;
	
	private String houseNumber;
	
	private String email;
	
	private String password;

	/**
	 * Empty constructor
	 */
	public Customer() {
		super();
	}

	/**
	 * @param id
	 * @param foreName
	 * @param sureName
	 * @param place
	 * @param zip
	 * @param street
	 * @param houseNumber
	 * @param email
	 * @param password
	 */
	public Customer(long id, String foreName, String sureName, String place, String zip, String street,
			String houseNumber, String email, String password) {
		super();
		this.id = id;
		this.foreName = foreName;
		this.sureName = sureName;
		this.place = place;
		this.zip = zip;
		this.street = street;
		this.houseNumber = houseNumber;
		this.email = email;
		this.password = password;
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
	 * @return the foreName
	 */
	public String getForeName() {
		return foreName;
	}

	/**
	 * @param foreName the foreName to set
	 */
	public void setForeName(String foreName) {
		this.foreName = foreName;
	}

	/**
	 * @return the sureName
	 */
	public String getSureName() {
		return sureName;
	}

	/**
	 * @param sureName the sureName to set
	 */
	public void setSureName(String sureName) {
		this.sureName = sureName;
	}

	/**
	 * @return the place
	 */
	public String getPlace() {
		return place;
	}

	/**
	 * @param place the place to set
	 */
	public void setPlace(String place) {
		this.place = place;
	}

	/**
	 * @return the zip
	 */
	public String getZip() {
		return zip;
	}

	/**
	 * @param zip the zip to set
	 */
	public void setZip(String zip) {
		this.zip = zip;
	}

	/**
	 * @return the street
	 */
	public String getStreet() {
		return street;
	}

	/**
	 * @param street the street to set
	 */
	public void setStreet(String street) {
		this.street = street;
	}

	/**
	 * @return the houseNumber
	 */
	public String getHouseNumber() {
		return houseNumber;
	}

	/**
	 * @param houseNumber the houseNumber to set
	 */
	public void setHouseNumber(String houseNumber) {
		this.houseNumber = houseNumber;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Returning the values of the object-attributes.
	 */
	@Override
	public String toString() {
		return "Customer [id=" + id + ", foreName=" + foreName + ", sureName=" + sureName + ", place=" + place
				+ ", zip=" + zip + ", street=" + street + ", houseNumber=" + houseNumber + ", email=" + email
				+ ", password=" + password + "]";
	}
	
	

}
