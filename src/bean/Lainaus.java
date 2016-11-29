package bean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Lainaus {
	private int numero;
	private Date lainausPvm;
	private Asiakas lainaaja;
	private ArrayList<NiteenLainaus> lista;

	public Lainaus() {
		numero = 0;
		lainausPvm = null;
		lainaaja = null;
		lista = null;
	}

	public Lainaus(int numero, Date lainausPvm, Asiakas lainaaja, ArrayList<NiteenLainaus> lista) {
		super();
		this.numero = numero;
		this.lainausPvm = lainausPvm;
		this.lainaaja = lainaaja;
		this.lista = lista;
	}

	public void addNiteenLainaus(NiteenLainaus nl) {
		if (nl != null) {
			if (lista == null)
				lista = new ArrayList<NiteenLainaus>();

			lista.add(nl);
		}
	}

	public NiteenLainaus getNiteenLainaus(int i) {
		NiteenLainaus paluu = null;
		if (lista != null && i < lista.size())
			paluu = lista.get(i);
		return paluu;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public Date getLainausPvm() {
		return lainausPvm;
	}

	public void setLainausPvm(Date lainausPvm) {
		this.lainausPvm = lainausPvm;
	}

	public Asiakas getLainaaja() {
		return lainaaja;
	}

	public void setLainaaja(Asiakas lainaaja) {
		this.lainaaja = lainaaja;
	}

	public ArrayList<NiteenLainaus> getLista() {
		return lista;
	}

	public void setLista(ArrayList<NiteenLainaus> lista) {
		this.lista = lista;
	}

	@Override
	public String toString() {
		return "Lainaus [numero=" + numero + ", lainausPvm=" + lainausPvm + ", lainaaja=" + lainaaja + ", lista="
				+ lista + "]";
	}

}