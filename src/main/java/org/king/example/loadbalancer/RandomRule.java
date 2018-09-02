package org.king.example.loadbalancer;

import java.util.List;
import java.util.Random;

public class RandomRule implements IRule {

	private ILoadBalancer loadBalancer;
	
	public RandomRule() {
    }
	
	public RandomRule(ILoadBalancer lb) {
		this.loadBalancer = lb;
    }
	
	@Override
	public IServer choose(Object key) {
		
		if (loadBalancer == null) {
            return null;
        }
		
		List<IServer> reachableServers = loadBalancer.getReachableServers();
        int upCount = reachableServers.size();
        
        if(upCount == 0) {
        	return null;
        }
        
        Random random = new Random();
        int index = random.nextInt(upCount);
        
		return reachableServers.get(index);
	}

	@Override
	public void setLoadBalancer(ILoadBalancer lb) {
		this.loadBalancer = lb;
	}

	@Override
	public ILoadBalancer getLoadBalancer() {
		return this.loadBalancer;
	}

}
