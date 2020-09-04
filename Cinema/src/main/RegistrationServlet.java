package main;

import java.io.IOException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.UserDAO;
import model.ERole;
import model.User;


public class RegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
  
    public RegistrationServlet() {
        super();
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
			String userName = request.getParameter("userName");
			if (UserDAO.findByUsername(userName) != null)
				throw new Exception("Username already taken!");
			if ("".equals(userName))
				throw new Exception("Username can't be empty!");

			String password = request.getParameter("password");
			if ("".equals(password))
				throw new Exception("Password can't be empty!");

			String repeatedPassword = request.getParameter("repeatPassword");
			if (!password.equals(repeatedPassword))
				throw new Exception("Passwords don't match!");
			
			User user = new User();
			Date regDate = new Date();
			ERole role = ERole.USER;
			
			user.setUsername(userName);
			user.setPassword(password);
			user.setRegistrationDate(regDate);
			user.setRole(role);
			user.setDeleted(false);

			UserDAO.add(user);
			request.getRequestDispatcher("./SuccessServlet").forward(request, response);
		} catch (Exception ex) {
			String message = ex.getMessage();
			if (message == null) {
				message = "Unpredicted error!";
				ex.printStackTrace();
			}

			Map<String, Object> data = new LinkedHashMap<>();
			data.put("message", message);

			request.setAttribute("data", data);
			request.getRequestDispatcher("./FailureServlet").forward(request, response);
		}
	}

}
