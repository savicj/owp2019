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
					}case "getUser" : {
						String id = request.getParameter("userId");
						int userID;
						User user;
						if(id != null && id != "") {
							userID = Integer.parseInt(id);
							if(UserDAO.getUser(userID) != null) {
								
								user = UserDAO.getUser(userID);
								data.put("user", user);
								request.getRequestDispatcher("./SuccessServlet").forward(request, response);
							}else {
								request.getRequestDispatcher("./LogoutServlet").forward(request, response);
								break;
							}
								
						}else {
							request.getRequestDispatcher("./LogoutServlet").forward(request, response);
							System.out.println("User id is empty.");
							break;
						}
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
				
				Map<String, Object> data = new LinkedHashMap<>();
				
				switch (action) {
				case "add" : {
					String username = request.getParameter("username");
					String password = request.getParameter("password");
					String role = request.getParameter("role");
					
					if(username != null && username != "") 
						if(UserDAO.findByUsername(username) != null) 
							throw new Exception("Username is taken");
					
					ERole erole = ERole.valueOf("USER");

					if(role != null && role != "") {
						try {
							erole = ERole.valueOf(role);
						} catch (Exception e) {
							System.out.println("Invalid role");
						}
					}
					String date = request.getParameter("registrationDate");	
					Timestamp d = new Timestamp(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date).getTime());
					
					User user = new User(username, password, d, erole, false);
					UserDAO.add(user);
					request.getRequestDispatcher("./SuccessServlet").forward(request, response);
					break;
				}
				case "delete" : {
					int id;
					String userid = request.getParameter("id");
					if(userid != null && userid != "") {
						id = Integer.parseInt(userid);
						User u = UserDAO.getUser(id);
						if(u == null || u.isDeleted() == true) {
							throw new Exception("No such user");
						}else {
							List<Ticket> ticket = TicketDAO.findByUser(u.getUsername());
							System.out.println(ticket);
							if(ticket.isEmpty()) {
								
								UserDAO.delete(u.getUsername());
							}

							else
								u.setDeleted(true);
								UserDAO.update(u);
								System.out.println("trebalo bi da je apdejtovano ");
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