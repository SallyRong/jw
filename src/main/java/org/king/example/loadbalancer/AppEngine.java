package org.king.example.loadbalancer;

public class AppEngine {
	
	private IServer server;
	
	private int instanceId;
	
	public AppEngine(IServer server,int instanceId) {
		this.server = server;
		this.instanceId = instanceId;
	}
	
	public void init() {
		System.out.println("serverName:" + server.getServerName()+",instanceId:"+instanceId +"初始化成功！");
	}
	
	public String service() {
		
		System.out.println(Thread.currentThread().getName()+",serverName:" + server.getServerName()+",instanceId:"+instanceId);
		
		return "";
	}
	
	public String destory() {
		
		System.out.println("serverName:" + server.getServerName()+",instanceId:"+instanceId);
		
		return "";
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null){
            return false;
        }
        if (this == obj){
            return true;
        }
        if (obj instanceof AppEngine){
        	AppEngine o = (AppEngine) obj;
            return this.instanceId == o.getInstanceId();
        }
		return false;
	}
	
	public IServer getServer() {
		return server;
	}

	public void setServer(IServer server) {
		this.server = server;
	}

	public int getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(int instanceId) {
		this.instanceId = instanceId;
	}
	
	
}
