package main;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.User;
import dao.UserDAO;

import java.util.LinkedHashMap;
import java.util.Map;

public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String loggedInUserName = (String) request.getSession().getAttribute("loggedInUserName");
		if (loggedInUserName == null) {
			request.getRequestDispatcher("./LogoutServlet").forward(request, response);
			return;
		}
		try {
			User loggedInUser = UserDAO.findByUsername(loggedInUserName);
			if (loggedInUser == null) {
				request.getRequestDispatcher("./LogoutServlet").forward(request, response);
				return;
			}
			
	
			String action = request.getParameter("action");
			
//			if(action != null && action != "") {
//				Map<String, Object> data = new LinkedHashMap<>();
//				switch (action) {
//				case "loggedInUserRole": {
//					data.put("loggedInUserRole", loggedInUser.getRole());
//					System.out.println(loggedInUser.getRole());
//					break;
//				}case  "loggedInUserId" : {
//					data.put("loggedInUserId", loggedInUser.getId());
//					break;
//				}
//				
//				}				
//				request.setAttribute("data", data);
//				request.getRequestDispatcher("./SuccessServlet").forward(request, response);
//			}else {
//				System.out.println("action == null");
//			}
	
			
			if(action != null && action != "") {
				
				Map<String, Object> data = new LinkedHashMap<>();
				
				switch (action) {
					case "loggedInUserRole": {
						data.put("loggedInUserRole", loggedInUser.getRole());
						break;
					}case  "loggedInUserId" : {
						data.put("loggedInUserId", loggedInUser.getId());
						break;
					}
				}

				request.setAttribute("data", data);
				request.getRequestDispatcher("./SuccessServlet").forward(request, response);
			}
//			else if(user != null && korisnikID != "") {
//				Map<String, Object> data = new LinkedHashMap<>();
//				data.put("trazeniKorisnik", KorisniciDAO.getById(korisnikID));
//
//				request.setAttribute("data", data);
//				request.getRequestDispatcher("./SuccessServlet").forward(request, response);
//			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
