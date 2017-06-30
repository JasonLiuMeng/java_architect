package bhz.netty.test5;

import java.io.Serializable;

public class Error implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Error() {
		// TODO Auto-generated constructor stub
	}
	
	private String error;

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public Error(String error) {
		super();
		this.error = error;
	}
	
}
