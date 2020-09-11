package main;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Hall;
import dao.HallDAO;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class HallServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    public HallServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String hall = request.getParameter("hallFilter");
    	String projectionType = request.getParameter("projTypeFilter");
    	
    	List<Hall> halls = HallDAO.getAll(projectionType);
    	
    	Map<String, Object> data = new LinkedHashMap<>();
		data.put("halls", halls);
		
		request.setAttribute("data", data);
		request.getRequestDispatcher("./SuccessServlet").forward(request, response);
    }

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
