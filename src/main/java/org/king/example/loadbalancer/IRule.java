package org.king.example.loadbalancer;

public interface IRule {
	
	IServer choose(Object key);
	
	void setLoadBalancer(ILoadBalancer lb);
	
	ILoadBalancer getLoadBalancer(); 

}
