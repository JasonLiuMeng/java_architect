package bhz.netty.test5;

import java.io.Serializable;

public class HeartBeatResponse  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1575181528386552937L;

	private String msg;
	
	public HeartBeatResponse() {
		// TODO Auto-generated constructor stub
	}

	public HeartBeatResponse(String msg) {
		super();
		this.msg = msg;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
	
}
