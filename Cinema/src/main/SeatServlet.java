package main;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Hall;
import model.Seat;
import dao.HallDAO;
import dao.SeatDAO;

public class SeatServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String h = request.getParameter("hall");
		
		System.out.println(h);
		if(h != "" && h != null) {
			Hall hall = HallDAO.findByName(h);
			List<Seat> seats = SeatDAO.getSeatsFromHall(hall.getId());
			System.out.println(seats);
			Map<String, Object> data = new LinkedHashMap<>();
			data.put("seats", seats);
			
			request.setAttribute("data", data);
			request.getRequestDispatcher("./SuccessServlet").forward(request, response);
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
