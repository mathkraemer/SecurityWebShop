package org.thb.webshop.util;

/**
 * Security Fail and possible Function Fail  
 * @author Manuel Raddatz
 *
 */
public class DataBaseCredentials {
	
	public static final String DB_URL = "jdbc:mysql://localhost:3306/securitywebshopdb";
	public static final String DB_DRIVER = "com.mysql.jdbc.Driver";
	public static final String DB_USER = "securitywebshopuser";
	public static final String DB_PASSWORD = "mysqlpassword";
	
	public void nr_bug(Object testref){
           if (testref == null)
	      System.out.println(testref.toString());
	   else
              System.out.println("testref is null");
	}
}
