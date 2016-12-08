package control;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Asiakas;
import bean.Lainaus;
import bean.NiteenLainaus;
import dao.LainausDao;

/**
 * Servlet implementation class varmistusServlet
 */
@WebServlet("/varmistus")
public class varmistusServlet extends HttpServlet {
	private RequestDispatcher jsp;
	private static final long serialVersionUID = 1L;
       
	/**
	 * @see Servlet#init(ServletConfig)
	 */
	@Override
	public void init(ServletConfig config) throws ServletException {
		ServletContext context = config.getServletContext();
		jsp = context.getRequestDispatcher("/view/varmistus.jsp");
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LainausDao lainausDao = new LainausDao();
		int asiakasNro = Integer.parseInt(request.getParameter("asiakas"));
		Asiakas asiakas = lainausDao.haeAsiakas(asiakasNro);
		
		String[] niteet = request.getParameterValues("nide");
		
		Lainaus lainaus = new Lainaus();
		
		for(String nidenroString : niteet){
			int nideNro = Integer.parseInt(nidenroString);
			NiteenLainaus nl = lainausDao.haeNiteenLainaus(nideNro);
			lainaus.addNiteenLainaus(nl);
		}


		Date nyt = new Date();
		
		lainaus.setLainaaja(asiakas);
		lainaus.setLainausPvm(nyt);
		
		
		request.setAttribute("lainaus", lainaus);
		
		
		jsp.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LainausDao lainausDao = new LainausDao();
		int asiakasNro = Integer.parseInt(request.getParameter("asiakas"));
		Asiakas asiakas = lainausDao.haeAsiakas(asiakasNro);
		
		String[] niteet = request.getParameterValues("nide");
		
		Lainaus lainaus = new Lainaus();
		lainaus.setLainaaja(asiakas);
		Date nyt = new Date();
		lainaus.setLainausPvm(nyt);
		
		for(String nidenroString : niteet){
			int nideNro = Integer.parseInt(nidenroString);
			NiteenLainaus nl = new NiteenLainaus();
			System.out.println(nideNro);
			nl = lainausDao.haeNiteenLainaus(nideNro);
			lainaus.addNiteenLainaus(nl);
			System.out.println(lainaus);
			lainausDao.lisaaLainaus(lainaus);
			
		}
		jsp.forward(request, response);
	}

}
