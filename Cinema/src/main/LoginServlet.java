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
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		
		
		User user = UserDAO.getUser(userName, password);
		try {
			if(user==null || user.isDeleted() == true) {
				System.out.println(userName + " " + password + "  korisnik nije pronadjen");
				request.getRequestDispatcher("./FailureServlet").forward(request, response);
				return;
			}
			request.getSession().setAttribute("loggedInUserName", user.getUsername());
			request.getRequestDispatcher("./SuccessServlet").forward(request, response);
			System.out.println(userName + " " + password);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

}
