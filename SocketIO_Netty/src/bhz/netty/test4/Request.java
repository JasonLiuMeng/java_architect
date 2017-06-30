package bhz.netty.test4;

import java.io.Serializable;

/**
 * 请求实体，注意需要实现Serializable接口，因为Marshalling相当于对Serializable的一个补充，在传输的时候仍然是利用Serializable进行序列化的
 * @author jliu10
 *
 */
public class Request implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5538517398618869423L;

	private String id;
	private String name;
	private String requestMessage;
	private byte[] attachment;
	
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
	public String getRequestMessage() {
		return requestMessage;
	}
	public void setRequestMessage(String requestMessage) {
		this.requestMessage = requestMessage;
	}
	public byte[] getAttachment() {
		return attachment;
	}
	public void setAttachment(byte[] attachment) {
		this.attachment = attachment;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return " id : " + this.id
			 + "; name : " + this.name
			 + "; requestMessage : " + this.requestMessage;
	}
}
