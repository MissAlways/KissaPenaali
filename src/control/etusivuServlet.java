package control;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.LainausDao;

/**
 * Servlet implementation class etusivuServlet
 */
@WebServlet("/etusivu")
public class etusivuServlet extends HttpServlet {
	private RequestDispatcher jsp;
	private static final long serialVersionUID = 1L;
       
    @Override
    public void init(ServletConfig config) throws ServletException {
    	ServletContext context = config.getServletContext();
		jsp = context.getRequestDispatcher("/view/etusivu.jsp");
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LainausDao lainausDao = new LainausDao();
		List<Integer> lainausnumeroLista = new ArrayList<Integer>();
		lainausnumeroLista = lainausDao.haeKaikkiLainausNumerot();
		request.setAttribute("lainausnumerot", lainausnumeroLista);
		
		jsp.forward(request, response);	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		
		doGet(request, response);
	}

}
