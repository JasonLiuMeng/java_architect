package bhz.netty.test5;

import java.io.Serializable;
import java.util.Map;

public class HeartBeatRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8534201076040524323L;
	private String ip;
	private Map<String, Object> cupMap;
	private Map<String, Object> memoryMap;
	
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public Map<String, Object> getCupMap() {
		return cupMap;
	}
	public void setCupMap(Map<String, Object> cupMap) {
		this.cupMap = cupMap;
	}
	public Map<String, Object> getMemoryMap() {
		return memoryMap;
	}
	public void setMemoryMap(Map<String, Object> memoryMap) {
		this.memoryMap = memoryMap;
	}
	
	
}
