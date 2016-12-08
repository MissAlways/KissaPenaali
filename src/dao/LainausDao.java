package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.sun.org.apache.regexp.internal.recompile;

import bean.Asiakas;
import bean.Kirja;
import bean.Lainaus;
import bean.Nide;
import bean.NiteenLainaus;
import bean.PostinumeroAlue;

public class LainausDao extends DataAccessObject {

	// haeKaikkiLainausNumerot
	/*
	 * kun käyttäjä ottaa yhteyttä järjestelmään, kannasta haetaan kaikki
	 * lainausnumerot ja tuodaan näytölle
	 */
	public List<Integer> haeKaikkiLainausNumerot() {
		Connection connection = null; // nollataan tietoja
		PreparedStatement statement = null;
		ResultSet rst = null;
		List<Integer> lainausLista = new ArrayList<Integer>();

		try {
			connection = getConnection(); // yhteys avataan tietokantaan
			connection.setAutoCommit(false); // otetaan auto committi pois &
												// transaktio alkaa
			connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
			// sql lause joka hakee kaikki tietyn lainauksen tiedot
			String sql = "SELECT l.lainausnro FROM LAINAUS l;";
			statement = connection.prepareStatement(sql);
			;
			rst = statement.executeQuery();

			while (rst.next()) {
				int nro;
				nro = rst.getInt("l.lainausnro");
				lainausLista.add(nro);
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
	/*
	 * kun käyttäjä valitsee yhden lainausnumeron, ko. lainaus haetaan
	 * tietokannasta ja tuodaan täydellisenä näytölle
	 */
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

				asiakas.setEtunimi(rst.getString("a.etunimi"));
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
	/*
	 * kun käyttäjä valitse kaikkien lainausten haun, ne haetaan täydellisenä
	 * kannasta näytölle
	 */
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

			while (rst.next()) {
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

				asiakas.setEtunimi(rst.getString("a.etunimi"));
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

	/*
	 * Saat kaikki niteet, jotka eivät ole lainauksessa ja jotka voidaan siis
	 * lainata. Tulosta niteen isbn, numero, nimi, kirjoittaja, kustantaja ja
	 * painos.
	 */
	// haeKaikkiVapaatKirjat

	public List<Nide> haeKaikkiVapaatKirjat() {
		Connection connection = null; // nollataan tietoja
		PreparedStatement statement = null;
		ResultSet rst = null;
		List<Nide> kirjat = new ArrayList<Nide>();

		try {
			connection = getConnection(); // yhteys avataan tietokantaan
			connection.setAutoCommit(false); // otetaan auto committi pois &
												// transaktio alkaa
			connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
			// sql lause joka hakee kaikki vapaat kirjat
			String sql = "SELECT isbn, nimi, kirjoittaja, painos, kustantaja, nidenro "
					+ "FROM KIRJA NATURAL JOIN NIDE WHERE NOT EXISTS ( "
					+ "SELECT * FROM NITEENLAINAUS WHERE NITEENLAINAUS.isbn=KIRJA.isbn "
					+ "AND NITEENLAINAUS.nidenro=NIDE.nidenro AND palautuspvm IS NOT NULL); ";
			statement = connection.prepareStatement(sql);
			rst = statement.executeQuery();

			while (rst.next()) {
				Kirja kirja = new Kirja();

				kirja.setIsbn(rst.getString("isbn"));
				kirja.setKirjoittaja(rst.getString("kirjoittaja"));
				kirja.setKustantaja(rst.getString("kustantaja"));
				kirja.setNimi(rst.getString("nimi"));
				kirja.setPainos(rst.getInt("painos"));

				Nide nide = new Nide();
				nide.setKirja(kirja);
				nide.setNidenro(rst.getInt("nidenro"));

				kirjat.add(nide);

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

		return kirjat;
	}

	public List<Asiakas> haeAsiakkaat() {
		Connection connection = null; // nollataan tietoja
		PreparedStatement statement = null;
		ResultSet rst = null;
		List<Asiakas> asiakkaat = new ArrayList<Asiakas>();
		try {
			connection = getConnection();
			connection.setAutoCommit(false);
			connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
			String sql = "SELECT numero, etunimi, sukunimi FROM ASIAKAS;";
			statement = connection.prepareStatement(sql);
			rst = statement.executeQuery();

			while (rst.next()) {
				Asiakas asiakas = new Asiakas();
				asiakas.setEtunimi(rst.getString("etunimi"));
				asiakas.setSukunimi(rst.getString("sukunimi"));
				asiakas.setNumero(rst.getInt("numero"));
				asiakkaat.add(asiakas);
			}
			connection.commit();
		} catch (Exception e) {
			try {
				connection.rollback(); // transaktion perutus
			} catch (Exception e2) {
				e.printStackTrace();
			}
		} finally {
			close(statement, connection);
		}
		return asiakkaat;
	}

	public void lisaaLainaus(Lainaus lainaus) {
		Connection connection = null; // nollataan tietoja
		PreparedStatement statement = null;

		try {
			connection = getConnection(); // yhteys avataan tietokantaan
			connection.setAutoCommit(false); // otetaan auto committi pois &
												// transaktio alkaa
			connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			// sql lause joka lisaaLainauksen
			String sqlLainaus = "INSERT INTO LAINAUS (asnumero, lainauspvm) VALUES (?,?);";
			statement = connection.prepareStatement(sqlLainaus);
			statement.setInt(1, lainaus.getLainaaja().getNumero());
			java.sql.Date sqlLainausPvm = new java.sql.Date(lainaus.getLainausPvm().getTime());
			statement.setDate(2, sqlLainausPvm);
			statement.executeUpdate();

			String sqlNiteenLainaus = "INSERT INTO NITEENLAINAUS (lainausnro, isbn, nidenro) VALUES (?,?,?);";
			for (int i = 0; i < lainaus.getLista().size(); i++) {
				statement = connection.prepareStatement(sqlNiteenLainaus);
				statement.setInt(1, lainaus.getNumero());
				statement.setString(2, lainaus.getNiteenLainaus(i).getNide().getKirja().getIsbn());
				statement.setInt(3, lainaus.getNiteenLainaus(i).getNide().getNidenro());
				statement.executeUpdate();
			}

			connection.commit();

		} catch (Exception e) {
			try {
				connection.rollback(); // transaktion perutus
			} catch (Exception e2) {
				e.printStackTrace();
			}
		} finally {
			close(statement, connection);
		}
	}

	public Asiakas haeAsiakas(int id) {
		Connection connection = null; // nollataan tietoja
		PreparedStatement statement = null;
		ResultSet rst = null;
		Asiakas asiakas = new Asiakas();

		try {
			connection = getConnection(); // yhteys avataan tietokantaan
			connection.setAutoCommit(false); // otetaan auto committi pois &
												// transaktio alkaa
			connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
			// sql lause joka hakee kaikki tietyn asiakkaan tiedot
			String sql = "SELECT a.numero, a.etunimi, a.sukunimi, a.osoite, p.postinro, p.postitmp FROM ASIAKAS a JOIN POSTINUMEROALUE p ON a.postinro = p.postinro WHERE numero = ?;";
			statement = connection.prepareStatement(sql);
			statement.setInt(1, id);
			rst = statement.executeQuery();

			if (rst.next()) {
				PostinumeroAlue pa = new PostinumeroAlue();
				pa.setPostinro(rst.getInt("p.postinro"));
				pa.setPostitmp(rst.getString("p.postitmp"));

				asiakas.setEtunimi(rst.getString("a.etunimi"));
				asiakas.setNumero(rst.getInt("a.numero"));
				asiakas.setOsoite(rst.getString("a.osoite"));
				asiakas.setPosti(pa);
				asiakas.setSukunimi(rst.getString("a.sukunimi"));
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

		return asiakas;

	}

	public NiteenLainaus haeNiteenLainaus(int nidenro) {
		Connection connection = null; // nollataan tietoja
		PreparedStatement statement = null;
		ResultSet rst = null;
		NiteenLainaus nl = new NiteenLainaus();

		try {
			connection = getConnection(); // yhteys avataan tietokantaan
			connection.setAutoCommit(false); // otetaan auto committi pois &
												// transaktio alkaa
			connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
			// sql lause joka hakee kaikki tietyn nidelainauksen tiedot
			String sql = "SELECT k.isbn, n.nidenro, k.nimi, k.kirjoittaja, k.painos, k.kustantaja FROM NIDE n JOIN KIRJA k ON k.isbn = n.isbn WHERE n.nidenro = ?;";
			statement = connection.prepareStatement(sql);
			statement.setInt(1, nidenro);
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

				nl.setNide(nide);
				Date nytPlus30 = new Date();
				nytPlus30.setTime(nytPlus30.getTime() + 30 * 1000 * 60 * 60 * 24);
				nl.setPalautusPvm(nytPlus30);

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

		return nl;

	}

}
