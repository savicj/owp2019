package main;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Hall;
import dao.HallDAO;
import model.Projection;
import dao.ProjectionDAO;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class HallServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
   
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String projection = request.getParameter("projection");
    	String projectionType = request.getParameter("projTypeFilter");
    	if(projectionType != null && projectionType != "") {
			switch (projectionType) {
			case "2D":
				projectionType = "twodim";
			case "3D":
				projectionType = "threedim";
			case "4D":
				projectionType = "fourdim";
			}
			List<Hall> halls = HallDAO.getAll(projectionType);
			System.out.println(halls);
			Map<String, Object> data = new LinkedHashMap<>();
			data.put("halls", halls);
			
			request.setAttribute("data", data);
			request.getRequestDispatcher("./SuccessServlet").forward(request, response);
		}else if(projection != null && projection != "") {
			Projection p = ProjectionDAO.get(Integer.parseInt(projection));
			Hall hall = HallDAO.get(p.getHall().getId());
			System.out.println(hall);
			Map<String, Object> data = new LinkedHashMap<>();
			data.put("hall", hall);
			
			request.setAttribute("data", data);
			request.getRequestDispatcher("./SuccessServlet").forward(request, response);
		}
    }

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
