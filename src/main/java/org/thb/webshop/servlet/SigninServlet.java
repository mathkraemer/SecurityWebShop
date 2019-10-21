package org.thb.webshop.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.thb.webshop.model.Customer;
import org.thb.webshop.model.ShoppingCart;
import org.thb.webshop.util.DataBaseUtil;

/**
 * Servlet implementation class SigninServlet
 */
public class SigninServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SigninServlet() {
        super();
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		
		response.setContentType("text/html; UTF-8");
		
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		DataBaseUtil dbUtil = null;
		try {
			dbUtil = new DataBaseUtil(request.getServletContext().getInitParameter("mysql-driver"),
					request.getServletContext().getInitParameter("mysql-url"), 
					request.getServletContext().getInitParameter("mysql-user"), 
					request.getServletContext().getInitParameter("mysql-password"));
		} catch (ClassNotFoundException e) {
			throw new ServletException(e.getCause());
		} catch (SQLException e) {
			throw new ServletException(e.getCause());
		}
		
		Customer customer = null;
		RequestDispatcher rd = null;
		
		try {
			customer = dbUtil.findCustomer(email, password);
			if(customer != null){
				ShoppingCart shoppingCart = new ShoppingCart();
				request.getSession().setAttribute("customer", customer);	
				request.getSession().setAttribute("shoppingCart", shoppingCart);	
				request.setAttribute("msg", "Login erfolgreich.");
				
				rd = request.getRequestDispatcher("index.jsp");
				rd.forward(request, response);
			}else{
				request.setAttribute("loginMsg", "Login fehlgeschlagen, bitte erneut versuchen.");
				rd = request.getRequestDispatcher("login.jsp");
				rd.forward(request, response);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new ServletException(e.getCause());
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
