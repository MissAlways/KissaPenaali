package control;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Lainaus;
import dao.LainausDao;

/**
 * Servlet implementation class yksiLainausServlet
 */
@WebServlet("/yksiLainaus")
public class yksiLainausServlet extends HttpServlet {
	private RequestDispatcher jsp;
	private static final long serialVersionUID = 1L;
       
	/**
	 * @see Servlet#init(ServletConfig)
	 */
	@Override
	public void init(ServletConfig config) throws ServletException {
		ServletContext context = config.getServletContext();
		jsp = context.getRequestDispatcher("/view/yksiLainaus.jsp");
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		LainausDao lainausDao = new LainausDao();
		int lainausNro = Integer.parseInt(request.getParameter("lainausnumero"));
		System.out.println(lainausNro);
		Lainaus lainaus = lainausDao.haeLainaus(lainausNro);
		
		request.setAttribute("lainaus", lainaus);
		jsp.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
