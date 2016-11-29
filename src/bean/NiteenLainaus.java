package bean;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NiteenLainaus {
	private Nide nide;
	private Date palautusPvm;
	
	public NiteenLainaus(Nide nide, Date palautusPvm) {
		this.nide = nide;
		this.palautusPvm = palautusPvm;
	}

	public NiteenLainaus() {
		nide = null;
		palautusPvm = null;
	}

	public Nide getNide() {
		return nide;
	}

	public void setNide(Nide nide) {
		this.nide = nide;
	}

	public Date getPalautusPvm() {
		return palautusPvm;
	}

	public void setPalautusPvm(Date palautusPvm) {
		this.palautusPvm = palautusPvm;
	}

	@Override
	public String toString() {
		return "NiteenLainaus [nide=" + nide + ", palautusPvm=" + palautusPvm + "]";
	}

}