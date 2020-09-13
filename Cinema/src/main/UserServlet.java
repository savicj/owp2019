package main;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.ERole;
import model.Projection;
import model.Ticket;
import model.User;
import dao.ProjectionDAO;
import dao.TicketDAO;
import dao.UserDAO;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
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
			
			String userID = request.getParameter("id");
			if(userID != null && userID != "") {
				User user = UserDAO.getUser(Integer.parseInt(userID));
				Map<String, Object> data = new LinkedHashMap<>();
				data.put("user", user);
				request.setAttribute("data", data);
				request.getRequestDispatcher("./SuccessServlet").forward(request, response);
			}
			
			String action = request.getParameter("action");	
			if(action != null && action != "") {
				
				Map<String, Object> data = new LinkedHashMap<>();
				
				switch (action) {
					case "loggedInUserRole": {
						data.put("loggedInUserRole", loggedInUser.getRole());
						request.setAttribute("data", data);
						request.getRequestDispatcher("./SuccessServlet").forward(request, response);
						break;
					}case  "loggedInUserId" : {
						data.put("loggedInUserId", loggedInUser.getId());
						request.setAttribute("data", data);
						request.getRequestDispatcher("./SuccessServlet").forward(request, response);
						break;
					}case "allUsers" : {
						if (loggedInUser.getRole() == ERole.ADMIN) {
							data.put("allUsers", UserDAO.getAll("", ""));
							System.out.println(UserDAO.getAll("", ""));
							request.setAttribute("data", data);
							request.getRequestDispatcher("./SuccessServlet").forward(request, response);
							break;
						}else {
							request.getRequestDispatcher("./LogoutServlet").forward(request, response);
							break;
						}
					}case "roles" : {
						List<ERole> roles = new ArrayList<ERole>();
						for(ERole role : ERole.values()) {
							roles.add(role);
						}
						data.put("eroles", roles);
						request.getRequestDispatcher("./SuccessServlet").forward(request, response);
					}
				}

				
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}


	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
			if(action != null && action != "") {
				switch (action) {
				case "update": {
					int id;
					String username = request.getParameter("username");
					String role = request.getParameter("role");
					ERole erole = ERole.valueOf(role);
					String userid = request.getParameter("id");
					if(userid != null && userid != "") {
						id = Integer.parseInt(userid);
						User u = UserDAO.getUser(id);
						u.setUsername(username);
						u.setRole(erole);
						UserDAO.update(u);
					}
					request.getRequestDispatcher("./SuccessServlet").forward(request, response);
					break;
				}
				case "delete" : {
					int id;
					String userid = request.getParameter("id");
					if(userid != null && userid != "") {
						id = Integer.parseInt(userid);
						User u = UserDAO.getUser(id);
						if(u != null) {
							List<Ticket> ticket = TicketDAO.findByUser(u.getUsername());
							System.out.println(ticket);
							if(ticket.isEmpty())
								UserDAO.delete(u.getUsername());
							else {
								u.setDeleted(true);
								UserDAO.update(u);
							}
						}
					}
					request.getRequestDispatcher("./SuccessServlet").forward(request, response);
					break;
				}
				}

			}
		}catch (Exception e){
			e.printStackTrace();
		}
	}
}