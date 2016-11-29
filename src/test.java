import java.util.List;

import dao.LainausDao;
import bean.Lainaus;


public class test {

	public static void main(String[] args) {
		LainausDao lainausDao = new LainausDao();
		Lainaus lainaus = new Lainaus();
		
		System.out.println("Hae kaikki lainausnumerot");
		List<Lainaus> lainauslista = lainausDao.haeKaikkiLainausNumerot();
		for(int i = 0; i < lainauslista.size(); i++){
			lainaus = lainauslista.get(i);
			System.out.println(lainaus.getNumero());
		}
		
		
		lainaus = lainausDao.haeLainaus(1);
		System.out.println("Hae kaikki tiedot yhdestä lainauksesta");
		System.out.println(lainaus.toString());

		System.out.println("Hae kaikki");
		lainauslista = lainausDao.haeKaikki();
		for(int i = 0; i < lainauslista.size(); i++){
			lainaus = lainauslista.get(i);
			System.out.println(lainaus);
		}
	}

}
