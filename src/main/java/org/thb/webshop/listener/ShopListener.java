package org.thb.webshop.listener;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Application Lifecycle Listener implementation class ShopListener
 *
 */
@WebListener
public class ShopListener implements HttpSessionListener {

    /**
     * Default constructor. 
     */
    public ShopListener() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see HttpSessionListener#sessionCreated(HttpSessionEvent)
     */
    public void sessionCreated(HttpSessionEvent se)  { 
         HttpSession session = se.getSession();
         session.getServletContext().log("SESSION CREATED: "+se.getSession().getId());
    }

	/**
     * @see HttpSessionListener#sessionDestroyed(HttpSessionEvent)
     */
    public void sessionDestroyed(HttpSessionEvent se)  { 
    	HttpSession session = se.getSession();
        session.getServletContext().log("SESSION DESTROYED: "+se.getSession().getId());
    }
	
}
