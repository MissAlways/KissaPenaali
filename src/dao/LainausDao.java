package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.Asiakas;
import bean.Kirja;
import bean.Lainaus;
import bean.Nide;
import bean.NiteenLainaus;
import bean.PostinumeroAlue;

public class LainausDao extends DataAccessObject {

	// haeKaikkiLainausNumerot
	/* kun käyttäjä ottaa yhteyttä järjestelmään, kannasta haetaan kaikki lainausnumerot ja tuodaan näytölle */
	public List<Lainaus> haeKaikkiLainausNumerot(){
		Connection connection = null; // nollataan tietoja
		PreparedStatement statement = null;
		ResultSet rst = null;
		List<Lainaus> lainausLista = new ArrayList<Lainaus>();
			
		try {
			connection = getConnection(); // yhteys avataan tietokantaan
			connection.setAutoCommit(false); // otetaan auto committi pois &
												// transaktio alkaa
			connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
			// sql lause joka hakee kaikki tietyn lainauksen tiedot
			String sql = "SELECT l.lainausnro FROM LAINAUS l;";
			statement = connection.prepareStatement(sql);;
			rst = statement.executeQuery();
			
			while(rst.next()){
				Lainaus lainaus = new Lainaus();
				lainaus.setNumero(rst.getInt("l.lainausnro"));
				lainausLista.add(lainaus);
			}
			
			connection.commit(); // transaktion varmistus

		} catch (Exception e) {
			try {
				connection.rollback(); // transaktion perutus
			} catch (Exception e2) {
				e.printStackTrace();
			}
		} finally {
			close(statement, connection);
			
		}
		
		
		return lainausLista;
	}
	
	// haeLainaus
	/* kun käyttäjä valitsee yhden lainausnumeron, ko. lainaus haetaan tietokannasta ja tuodaan täydellisenä näytölle */
	public Lainaus haeLainaus(int lainausNro) {
		Connection connection = null; // nollataan tietoja
		PreparedStatement statement = null;
		ResultSet rst = null;
		Lainaus lainaus = new Lainaus();
		
		try {
			connection = getConnection(); // yhteys avataan tietokantaan
			connection.setAutoCommit(false); // otetaan auto committi pois &
												// transaktio alkaa
			connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
			// sql lause joka hakee kaikki tietyn lainauksen tiedot
			String sql = "SELECT l.lainausnro, l.lainausPvm, l.asnumero, a.etunimi, a.sukunimi, "
					+ "a.osoite, a.postinro, p.postitmp, k.isbn, k.nimi, k.kirjoittaja, k.painos, "
					+ "k.kustantaja, n.nidenro, nl.palautusPvm FROM KIRJA k JOIN NIDE n ON n.isbn=k.isbn "
					+ "JOIN NITEENLAINAUS nl ON n.nidenro=nl.nidenro JOIN LAINAUS l ON nl.lainausnro=l.lainausnro "
					+ "JOIN ASIAKAS a ON a.numero=l.asnumero JOIN POSTINUMEROALUE p ON a.postinro=p.postinro "
					+ "WHERE l.lainausnro = ?;";
			statement = connection.prepareStatement(sql);
			statement.setInt(1, lainausNro);
			rst = statement.executeQuery();
			

			if (rst.next()) {
				Kirja kirja = new Kirja();

				kirja.setIsbn(rst.getString("k.isbn"));
				kirja.setKirjoittaja(rst.getString("k.kirjoittaja"));
				kirja.setKustantaja(rst.getString("k.kustantaja"));
				kirja.setNimi(rst.getString("k.nimi"));
				kirja.setPainos(rst.getInt("k.painos"));

				Nide nide = new Nide();

				nide.setKirja(kirja);
				nide.setNidenro(rst.getInt("n.nidenro"));

				NiteenLainaus niteenLainaus = new NiteenLainaus();

				niteenLainaus.setNide(nide);
				niteenLainaus.setPalautusPvm(rst.getDate("nl.palautusPvm"));

				PostinumeroAlue postinumeroAlue = new PostinumeroAlue();
				postinumeroAlue.setPostinro(rst.getInt("a.postinro"));
				postinumeroAlue.setPostitmp(rst.getString("p.postitmp"));

				Asiakas asiakas = new Asiakas();

				asiakas.setEtunimi(rst.getString("a.sukunimi"));
				asiakas.setNumero(rst.getInt("l.asnumero"));
				asiakas.setOsoite(rst.getString("a.osoite"));
				asiakas.setPosti(postinumeroAlue);
				asiakas.setSukunimi(rst.getString("a.sukunimi"));

				lainaus.setLainaaja(asiakas);
				lainaus.setLainausPvm(rst.getDate("l.lainausPvm"));
				lainaus.setNumero(rst.getInt("l.lainausnro"));
				lainaus.addNiteenLainaus(niteenLainaus);
			}
			connection.commit(); // transaktion varmistus

		} catch (Exception e) {
			try {
				connection.rollback(); // transaktion perutus
			} catch (Exception e2) {
				e.printStackTrace();
			}
		} finally {
			close(statement, connection);
			
		}

		return lainaus;
	}
	// haeKaikki
	/* kun käyttäjä valitse kaikkien lainausten haun, ne haetaan täydellisenä kannasta näytölle */
	public List<Lainaus> haeKaikki() {
		Connection connection = null; // nollataan tietoja
		PreparedStatement statement = null;
		ResultSet rst = null;
		List<Lainaus> lainausLista = new ArrayList<Lainaus>();
		
		try {
			connection = getConnection(); // yhteys avataan tietokantaan
			connection.setAutoCommit(false); // otetaan auto committi pois &
												// transaktio alkaa
			connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
			// sql lause joka hakee kaikki tietyn lainauksen tiedot
			String sql = "SELECT l.lainausnro, l.lainausPvm, l.asnumero, a.etunimi, a.sukunimi, "
					+ "a.osoite, a.postinro, p.postitmp, k.isbn, k.nimi, k.kirjoittaja, k.painos, "
					+ "k.kustantaja, n.nidenro, nl.palautusPvm FROM KIRJA k JOIN NIDE n ON n.isbn=k.isbn "
					+ "JOIN NITEENLAINAUS nl ON n.nidenro=nl.nidenro JOIN LAINAUS l ON nl.lainausnro=l.lainausnro "
					+ "JOIN ASIAKAS a ON a.numero=l.asnumero JOIN POSTINUMEROALUE p ON a.postinro=p.postinro;";
			statement = connection.prepareStatement(sql);
			rst = statement.executeQuery();
			
			while(rst.next()){
				Kirja kirja = new Kirja();

				kirja.setIsbn(rst.getString("k.isbn"));
				kirja.setKirjoittaja(rst.getString("k.kirjoittaja"));
				kirja.setKustantaja(rst.getString("k.kustantaja"));
				kirja.setNimi(rst.getString("k.nimi"));
				kirja.setPainos(rst.getInt("k.painos"));

				Nide nide = new Nide();

				nide.setKirja(kirja);
				nide.setNidenro(rst.getInt("n.nidenro"));

				NiteenLainaus niteenLainaus = new NiteenLainaus();

				niteenLainaus.setNide(nide);
				niteenLainaus.setPalautusPvm(rst.getDate("nl.palautusPvm"));

				PostinumeroAlue postinumeroAlue = new PostinumeroAlue();
				postinumeroAlue.setPostinro(rst.getInt("a.postinro"));
				postinumeroAlue.setPostitmp(rst.getString("p.postitmp"));

				Asiakas asiakas = new Asiakas();

				asiakas.setEtunimi(rst.getString("a.sukunimi"));
				asiakas.setNumero(rst.getInt("l.asnumero"));
				asiakas.setOsoite(rst.getString("a.osoite"));
				asiakas.setPosti(postinumeroAlue);
				asiakas.setSukunimi(rst.getString("a.sukunimi"));

				Lainaus lainaus = new Lainaus();
				
				lainaus.setLainaaja(asiakas);
				lainaus.setLainausPvm(rst.getDate("l.lainausPvm"));
				lainaus.setNumero(rst.getInt("l.lainausnro"));
				lainaus.addNiteenLainaus(niteenLainaus);
				
				lainausLista.add(lainaus);
			}
			
			connection.commit(); // transaktion varmistus

		} catch (Exception e) {
			try {
				connection.rollback(); // transaktion perutus
			} catch (Exception e2) {
				e.printStackTrace();
			}
		} finally {
			close(statement, connection);
		}
		return lainausLista;
	}
}
