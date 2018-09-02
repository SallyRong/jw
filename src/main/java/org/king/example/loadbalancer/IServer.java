package org.king.example.loadbalancer;

import java.util.List;

public interface IServer {
	
	int getServerId();
	
	String getServerName();
	
	boolean isAlive();
	
	void setServerStatus(boolean serverStatus);
	
	List<AppEngine> getAppEngines();
	
	void initAppEngine(List<AppEngine> appEngines);

}
