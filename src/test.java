import java.util.ArrayList;
import java.util.List;

import dao.LainausDao;
import bean.Asiakas;
import bean.Lainaus;
import bean.Nide;


public class test {

	public static void main(String[] args) {
		LainausDao lainausDao = new LainausDao();
		Lainaus lainaus = new Lainaus();
		
		System.out.println("Hae kaikki lainausnumerot");
		List<Integer> lainausNrolista = lainausDao.haeKaikkiLainausNumerot();
		for(int i = 0; i < lainausNrolista.size(); i++){
			int nro = lainausNrolista.get(i);
			System.out.println(nro);
		}
		
		List<Lainaus> lainauslista = new ArrayList<Lainaus>();
		lainaus = lainausDao.haeLainaus(1);
		System.out.println("Hae kaikki tiedot yhdestä lainauksesta");
		System.out.println(lainaus.toString());

		System.out.println("Hae kaikki");
		lainauslista = lainausDao.haeKaikki();
		for(int i = 0; i < lainauslista.size(); i++){
			lainaus = lainauslista.get(i);
			System.out.println(lainaus);
		}
		
		System.out.println("Hae kaikki vapaat kirjat");
		List<Nide> kirjat = lainausDao.haeKaikkiVapaatKirjat();
		for(int i = 0; i < kirjat.size(); i++){
			Nide nide = kirjat.get(i);
			System.out.println(nide);
		}
		System.out.println("Hae asiakkaat");
		List<Asiakas> asiakkaat = lainausDao.haeAsiakkaat();
		for (int i = 0; i < asiakkaat.size(); i++) {
			Asiakas asiakas = asiakkaat.get(i);
			System.out.println(asiakas);
		}
	}

}
