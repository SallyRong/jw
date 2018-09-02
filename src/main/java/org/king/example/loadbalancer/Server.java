package org.king.example.loadbalancer;

import java.util.List;

public class Server implements IServer {
	
	private int serverId;
	
	private String serverName;
	
	private boolean serverStatus;
	
	private List<AppEngine> appEngines;
	
	public Server() {
		this.serverStatus = true;
	}
	
	public Server(String serverName,int serverId) {
		this();
		this.serverId = serverId;
		this.serverName = serverName;
	}
	
	public Server(String serverName,int serverId,List<AppEngine> appEngines) {
		this(serverName,serverId);
		this.appEngines = appEngines;
	}
	
	public synchronized void addAppEngines(AppEngine appEngine) {
		if(appEngine == null) {
			return;
		}
		if(!appEngines.contains(appEngine)) {
			appEngines.add(appEngine);
		}
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null){
            return false;
        }
        if (this == obj){
            return true;
        }
        if (obj instanceof Server){
        	Server o = (Server) obj;
            return this.serverId == o.getServerId() && this.serverName.equals(o.getServerName());
        }
		return false;
	}
	
	public void initAppEngine(List<AppEngine> appEngines) {
		
		appEngines.forEach((appEngine) -> {
			appEngine.init();
		});

	}
	
	public int getServerId() {
		return this.serverId;
	}

	public String getServerName() {
		return this.serverName;
	}

	public boolean isAlive() {
		return this.serverStatus;
	}

	public List<AppEngine> getAppEngines() {
		return this.appEngines;
	}
	
	public void setServerId(int serverId) {
		this.serverId = serverId;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public void setAppEngines(List<AppEngine> appEngines) {
		this.appEngines = appEngines;
	}

	public void setServerStatus(boolean serverStatus) {
		this.serverStatus = serverStatus;
	}
	
	
}
