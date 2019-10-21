package org.thb.webshop.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Random;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.thb.webshop.model.Customer;
import org.thb.webshop.util.DataBaseUtil;

/**
 * Servlet implementation class RegisterServlet
 */
public class RegisterServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public RegisterServlet() {
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
		
		String foreName = request.getParameter("forename");
		String sureName = request.getParameter("surename");
		String zip = request.getParameter("zip");
		String place = request.getParameter("place");
		String street = request.getParameter("street");
		String houseNumber = request.getParameter("houseNumber");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		Customer customer = new Customer(new Random().nextLong(),
										foreName,
										sureName,
										place,
										zip,
										street,
										houseNumber, 
										email,
										password);
				
		boolean result;
		try {
			result = dbUtil.persistCustomer(customer);
		} catch (SQLException e) {
			throw new ServletException(e.getCause());
		}
		
		if(result){
			request.setAttribute("msg", "Registrierung war erfolgreich");
		}else{
			request.setAttribute("msg", "Registrierung war nicht erfolgreich");
		}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
		dispatcher.forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
