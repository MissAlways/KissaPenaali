import dao.LainausDao;
import bean.Lainaus;


public class test {

	public static void main(String[] args) {
		LainausDao lainausDao = new LainausDao();
		Lainaus lainaus = new Lainaus();
		
		lainaus = lainausDao.haeLainaus(1);
		
		System.out.println(lainaus.toString());

	}

}
