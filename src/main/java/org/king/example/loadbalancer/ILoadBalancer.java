package org.king.example.loadbalancer;

import java.util.List;

public interface ILoadBalancer {
	
	void addServers(IServer newServers);
	
	IServer chooseServer(Object key);
	
	void markServerDown(IServer server);
	
	List<IServer> getAllServers();
	
	List<IServer> getReachableServers();
	
	IRule getRule();
	
	void setRule(IRule rule);
}
