package org.thb.webshop.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.thb.webshop.model.Item;
import org.thb.webshop.model.ShoppingCart;
import org.thb.webshop.util.DataBaseUtil;

/**
 * The Servlet is responsible for updating the items table in the databse of shop. if the user clicks
 * on the "kaufen" button he inserts his id in the "buyer_id" column. The database automatically inserts a
 * timestamp in the "sold" column.
 * @author Manuel Raddatz
 *
 */
public class BuyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BuyServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
				
		String itemId = request.getParameter("item");
		
		long id;
		DataBaseUtil dbUtil = null;
		try {
			
			dbUtil = new DataBaseUtil(request.getServletContext().getInitParameter("mysql-driver"),
					request.getServletContext().getInitParameter("mysql-url"), 
					request.getServletContext().getInitParameter("mysql-user"), 
					request.getServletContext().getInitParameter("mysql-password"));
			id = Long.valueOf(itemId);
			
		}catch(SQLException | ClassNotFoundException | NumberFormatException e){
			throw new ServletException(e.initCause(e));
		}
		
		Item item = null;
		try {
			 item = dbUtil.findItemById(id);
		} catch (SQLException e) {
			throw new ServletException(e.initCause(e));
		}
		
		ShoppingCart cart = (ShoppingCart) request.getSession().getAttribute("shoppingCart");
		
		if(cart!=null){
			if(item!=null){
				cart.addItem(item);
				request.getSession().setAttribute("shoppingCart", cart);
			}
		}
	}
/*
	private void oldBuyAction(HttpServletRequest request, HttpServletResponse response){
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		response.setContentType("text/html; UTF-8");
		
		DataBaseUtil dbUtil = null;
		try {
			dbUtil = new DataBaseUtil(request.getServletContext().getInitParameter("mysql-driver"),
					request.getServletContext().getInitParameter("mysql-url"), 
					request.getServletContext().getInitParameter("mysql-user"), 
					request.getServletContext().getInitParameter("mysql-password"));
		} catch (ClassNotFoundException e) {
			try {
				throw new ServletException(e.getCause());
			} catch (ServletException e1) {
				e1.printStackTrace();
			}
		} catch (SQLException e) {
			try {
				throw new ServletException(e.getCause());
			} catch (ServletException e1) {
				e1.printStackTrace();
			}
		}

		String itemId = request.getParameter("itemId");
		
		Customer customer = (Customer) request.getSession().getAttribute("customer");
		
		RequestDispatcher rd = null;
		try {
			if(itemId!=null && customer.getId()!=0){
				boolean result = dbUtil.updateItem(itemId, customer.getId());
				if(!result){
						request.setAttribute("msg", "Sie erfolgreich den Artikel mit der ID: "+itemId+" gekauft.");
				}else{
						request.setAttribute("msg", "Der Einkauf des Artikels mit der ID: "+itemId+" ist fehlgeschlagen.");
				}
				rd= request.getRequestDispatcher("index.jsp");
				rd.forward(request, response);
			}else{
				rd= request.getRequestDispatcher("search.jsp");
				rd.forward(request, response);
			}
		} catch (SQLException e) {
			try {
				throw new ServletException(e.getCause());
			} catch (ServletException e1) {
				e1.printStackTrace();
			}
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
*/
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		doGet(request, response);
	}

}
