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
		String userID = request.getParameter("userID");
		String projectionID = request.getParameter("projectionID");
		String projection = request.getParameter("projection");
		String ticketID = request.getParameter("ticketID");
		String user = request.getParameter("user");
		int proj;
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
				User u = UserDAO.getUser(id);
				List<Ticket> tickets = TicketDAO.findByUser(u.getUsername());
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
		}else if(projection != "" && projection != null) {
			proj = Integer.parseInt(projection);
			List<Ticket> allTickets = TicketDAO.getAll(proj, user);
			Map<String, Object> data = new LinkedHashMap<>();
			data.put("tickets", allTickets);
			
			request.setAttribute("data", data);
			request.getRequestDispatcher("./SuccessServlet").forward(request, response);
		
		}else if(projection == "" || projection == "") {
			proj = 0;
			List<Ticket> allTickets = TicketDAO.getAll(proj, user);
			Map<String, Object> data = new LinkedHashMap<>();
			data.put("tickets", allTickets);
			
			request.setAttribute("data", data);
			request.getRequestDispatcher("./SuccessServlet").forward(request, response);
		}
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
