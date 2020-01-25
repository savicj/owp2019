package main;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.UserDAO;
import model.User;

public class ProjectionsServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String loggedInUser = (String) request.getSession().getAttribute("loggedInUser");
		if(loggedInUser == null) {
			request.getRequestDispatcher(".././LogoutServlet").forward(request, response);
			return;
		}
		
		try {
			User loggedUser = UserDAO.findByUsername(loggedInUser);
			if(loggedUser == null) {
				request.getRequestDispatcher(".././LogoutServlet").forward(request, response);
				return;
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
			
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
