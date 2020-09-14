package main;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ProjectionDAO;
import dao.TicketDAO;
import dao.UserDAO;
import model.Projection;
import model.Ticket;
import model.User;


public class TicketServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
     
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String ticketID = request.getParameter("ticketID");
		String projectionID = request.getParameter("projectionID");
		String userID = request.getParameter("userID");
		int id;
		if(ticketID != null && ticketID != "") {
			id = Integer.parseInt(ticketID);
			Map<String, Object> data = new LinkedHashMap<>();
			data.put("ticket", TicketDAO.get(id));
			System.out.println(TicketDAO.get(id));
			request.setAttribute("data", data);
			request.getRequestDispatcher("./SuccessServlet").forward(request, response);
		}else if(userID != null && userID != "") {
				id = Integer.parseInt(userID);
				User user = UserDAO.getUser(id);
				List<Ticket> tickets = TicketDAO.findByUser(user.getUsername());
				System.out.println(tickets);

				Map<String, Object> data = new LinkedHashMap<>();
				data.put("tickets", tickets);
				
				request.setAttribute("data", data);
				request.getRequestDispatcher("./SuccessServlet").forward(request, response);
		}else if(projectionID != null && projectionID != "") {
				id= Integer.parseInt(projectionID);
				Projection p = ProjectionDAO.get(id);
				List<Ticket> tickets = TicketDAO.findByProjection(p);
				System.out.println(tickets);

				Map<String, Object> data = new LinkedHashMap<>();
				data.put("tickets", tickets);
				
				request.setAttribute("data", data);
				request.getRequestDispatcher("./SuccessServlet").forward(request, response);
		}
		String p = request.getParameter("projection");
		String user = request.getParameter("user");
		int projection;
		if(p != "" && p != null) {
			projection = Integer.parseInt(p);
		}else {
			projection = 0;
		}
		List<Ticket> allTickets = TicketDAO.getAll(projection, user);
		Map<String, Object> data = new LinkedHashMap<>();
		data.put("tickets", allTickets);
		
		request.setAttribute("data", data);
		request.getRequestDispatcher("./SuccessServlet").forward(request, response);
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
