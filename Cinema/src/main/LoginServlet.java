package main;


import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.UserDAO;
import model.User;

public class LoginServlet extends HttpServlet {
  

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		try {
			User user = UserDAO.getUser(username, password);
			if(user==null) {
				request.getRequestDispatcher("./FailureServlet").forward(request, response);
				return;
			}
			request.getSession().setAttribute("loggedInUser", user.getUsername());
			request.getRequestDispatcher("./SuccessServlet").forward(request, response);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

}
