package org.king.example.loadbalancer;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class RoundRobinRule implements IRule {
	
	private static AtomicInteger nextServerCyclicCounter;
	
	private ILoadBalancer loadBalancer;
	
	public RoundRobinRule() {
		nextServerCyclicCounter = new AtomicInteger(0);
    }
	
	public RoundRobinRule(ILoadBalancer lb) {
		this();
        setLoadBalancer(lb);
    }
	
	public IServer choose(ILoadBalancer lb,Object key) {
		
		if (loadBalancer == null) {
            return null;
        }
		
		List<IServer> reachableServers = lb.getReachableServers();
        List<IServer> allServers = lb.getAllServers();
        int upCount = reachableServers.size();
        int serverCount = allServers.size();
        
        if ((upCount == 0) || (serverCount == 0)) {
            return null;
        }
		
        int next = incrementAndGetModulo(upCount);
        IServer server = allServers.get(next);
        
		return server;
	}
	
	private int incrementAndGetModulo(int modulo) {
        for (;;) {
            int current = nextServerCyclicCounter.get();
            int next = (current + 1) % modulo;
            if (nextServerCyclicCounter.compareAndSet(current, next))
                return next;
        }
    }
	
	@Override
	public IServer choose(Object key) {
        return choose(getLoadBalancer(), key);
    }
	
	@Override
	public void setLoadBalancer(ILoadBalancer lb) {
		this.loadBalancer = lb;
	}

	@Override
	public ILoadBalancer getLoadBalancer() {
		return loadBalancer;
	}
	
	
}
