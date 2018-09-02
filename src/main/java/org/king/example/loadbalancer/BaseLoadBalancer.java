package org.king.example.loadbalancer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BaseLoadBalancer implements ILoadBalancer {
	
	private final static IRule DEFAULT_RULE = new RandomRule();
	
	private IRule rule = DEFAULT_RULE;
	
	private volatile List<IServer> allServerList = Collections.synchronizedList(new ArrayList<IServer>());
	
	private volatile List<IServer> upServerList = Collections.synchronizedList(new ArrayList<IServer>());
	
	public BaseLoadBalancer() {
		DEFAULT_RULE.setLoadBalancer(this);
		setRule(DEFAULT_RULE);
	}
	
	public BaseLoadBalancer(IRule rule) {
		rule.setLoadBalancer(this);
		this.rule = rule;
	}
	
	@Override
	public void addServers(IServer newServer) {
        if (newServer != null && !allServerList.contains(newServer)) {
        	allServerList.add(newServer);
        	upServerList.add(newServer);
        }
    }
	
	@Override
	public IServer chooseServer(Object key) {
		if(rule == null) return null;
		return rule.choose(key);
	}

	@Override
	public void markServerDown(IServer server) {
		server.setServerStatus(false);
	}

	@Override
	public List<IServer> getAllServers() {
		return allServerList;
	}

	@Override
	public List<IServer> getReachableServers() {
		return upServerList;
	}
	
	@Override
	public void setRule(IRule rule) {
		this.rule = rule;
	}

	@Override
	public IRule getRule() {
		return this.rule;
	}
	
	
}
