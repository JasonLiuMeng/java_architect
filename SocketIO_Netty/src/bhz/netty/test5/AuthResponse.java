package bhz.netty.test5;

import java.io.Serializable;

public class AuthResponse implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String result;

	public AuthResponse() {
		// TODO Auto-generated constructor stub
	}
	
	public AuthResponse(String result) {
		super();
		this.result = result;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
	
	
}
