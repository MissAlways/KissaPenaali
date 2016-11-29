package bean;

public class Kirja {
	private String isbn;
	private String nimi;
	private String kirjoittaja;
	private int painos;
	private String kustantaja;
	
	
	public Kirja() {
		isbn = "";
		nimi = "";
		kirjoittaja = "";
		painos = 0;
		kustantaja = "";
	}
	public Kirja (String isbn, String nimi, String kirjoittaja, int painos, String kustantaja)
	{
		this.setIsbn(isbn);
		this.setKirjoittaja(kirjoittaja);
		this.setNimi(nimi);
		this.setKustantaja(kustantaja);
		this.setPainos(painos);
	}
	public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	public String getNimi() {
		return nimi;
	}
	public void setNimi(String nimi) {
		this.nimi = nimi;
	}
	public String getKirjoittaja() {
		return kirjoittaja;
	}
	public void setKirjoittaja(String kirjoittaja) {
		this.kirjoittaja = kirjoittaja;
	}
	public int getPainos() {
		return painos;
	}
	public void setPainos(int painos) {
		this.painos = painos;
	}
	public String getKustantaja() {
		return kustantaja;
	}
	public void setKustantaja(String kustantaja) {
		this.kustantaja = kustantaja;
	}
	@Override
	public String toString() {
		return "Kirja [isbn=" + isbn + ", nimi=" + nimi + ", kirjoittaja=" + kirjoittaja + ", painos=" + painos
				+ ", kustantaja=" + kustantaja + "]";
	}
	
	
}