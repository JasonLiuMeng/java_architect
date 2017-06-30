package bhz.netty.test5;

import java.io.Serializable;

public class AuthRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public AuthRequest() {
		// TODO Auto-generated constructor stub
	}
	
	public AuthRequest(String auth) {
		super();
		this.auth = auth;
	}

	private String auth;

	public String getAuth() {
		return auth;
	}

	public void setAuth(String auth) {
		this.auth = auth;
	}
	
	
	
}
