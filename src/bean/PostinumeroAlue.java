package bean;

public class PostinumeroAlue {
	private int postinro;
	private String postitmp;
	
	public PostinumeroAlue()
	{
		postinro=0;
		postitmp = "";
	}
	
	public PostinumeroAlue(int postinro, String postitmp) {
		super();
		this.postinro = postinro;
		this.postitmp = postitmp;
	}

	public int getPostinro() {
		return postinro;
	}
	public String getPostitmp() {
		return postitmp;
	}
	public void setPostitmp(String postitmp) {
		this.postitmp = postitmp;
	}
	public void setPostinro(int postinro) {
		this.postinro = postinro;
	}

	@Override
	public String toString() {
		return "PostinumeroAlue [postinro=" + postinro + ", postitmp="
				+ postitmp + "]";
	}
	
}