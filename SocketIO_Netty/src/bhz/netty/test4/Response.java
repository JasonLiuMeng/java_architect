package bhz.netty.test4;

import java.io.Serializable;

public class Response implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4157472697937211837L;
	
	private String id;
	private String name;
	private String responseMessage;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getResponseMessage() {
		return responseMessage;
	}
	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return " id : " + this.id
			 + "; name : " + this.name
			 + "; responseMessage : " + this.responseMessage;
	}
}
