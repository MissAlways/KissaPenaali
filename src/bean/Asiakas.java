package bean;

public class Asiakas {
	private int numero;
	private String etunimi;
	private String sukunimi;
	private String osoite;
	private PostinumeroAlue posti;

	public Asiakas() {
		numero = 0;
		etunimi = "";
		sukunimi = "";
		osoite = "";
		posti = null;
	}

	public Asiakas(int numero, String etunimi, String sukunimi, String osoite, PostinumeroAlue posti) {
		super();
		this.numero = numero;
		this.etunimi = etunimi;
		this.sukunimi = sukunimi;
		this.osoite = osoite;
		this.posti = posti;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public String getEtunimi() {
		return etunimi;
	}

	public void setEtunimi(String etunimi) {
		this.etunimi = etunimi;
	}

	public String getSukunimi() {
		return sukunimi;
	}

	public void setSukunimi(String sukunimi) {
		this.sukunimi = sukunimi;
	}

	public String getOsoite() {
		return osoite;
	}

	public void setOsoite(String osoite) {
		this.osoite = osoite;
	}

	public PostinumeroAlue getPosti() {
		return posti;
	}

	public void setPosti(PostinumeroAlue posti) {
		this.posti = posti;
	}

	@Override
	public String toString() {
		return "Asiakas [numero=" + numero + ", etunimi=" + etunimi + ", sukunimi=" + sukunimi + ", osoite=" + osoite
				+ ", posti=" + posti + "]";
	}

}