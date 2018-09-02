package org.king.example.loadbalancer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class StartUpELB {

	public static void main(String[] args) {
		
		Scanner scan = new Scanner(System.in);
		//final ILoadBalancer loadBalancer = new BaseLoadBalancer();
		ILoadBalancer loadBalancer = null;
		showTips();
		while(true) {
			String read = scan.nextLine();
		    if("1".equals(read)) {
		    	if(loadBalancer == null) {
		    		loadBalancer = new BaseLoadBalancer();
		    		System.out.println("ElB started...");
		    	}else {
		    		System.out.println("ElB already started!");
		    	}
		    }else if("2".equals(read)) {
		    	if(loadBalancer == null) {
		    		System.out.println("please start ELB first");
		    		continue;
		    	} 
		    	for(int i=1;i<=3;i++) {
		    		Server server = new Server("Server"+i,i);
		    		AppEngine appEngine = new AppEngine(server, i);
		    		List<AppEngine> appEngineList = new ArrayList<>();
		    		appEngineList.add(appEngine);
		    		server.setAppEngines(appEngineList);
		    		loadBalancer.addServers(server);
		    		System.out.println("The "+server.getServerName()+" has been started and completed.");
		    	}
		    }else if("3".equals(read)) {
		    	if(loadBalancer == null) {
		    		System.out.println("please start ELB first");
		    		continue;
		    	} 
		    	List<IServer> upServerList = loadBalancer.getReachableServers();
		    	List<IServer> allServerList = loadBalancer.getAllServers();
		    	
		    	int upCount = upServerList.size();
		    	if(upCount == 0) {
		    		System.out.println("No available services");
		    		continue;
		    	}
		        
		    	Random random = new Random();
		        int invalidIndex = random.nextInt(upCount);
		    	IServer invalidServer = upServerList.get(invalidIndex);
		    	upServerList.remove(invalidIndex);
		    	
		    	for(IServer server :allServerList) {
		    		if(server.getServerId() == invalidServer.getServerId()) {
		    			server.setServerStatus(false);
		    		}
		    	}
		    	System.out.println(invalidServer.getServerName()+" was killed successfully");
		    }else if("4".equals(read)) {
		    	if(loadBalancer == null) {
		    		System.out.println("please start ELB first");
		    		continue;
		    	} 
		    	Server newServer = new Server("server"+System.currentTimeMillis(),(int)System.currentTimeMillis());
		    	AppEngine appEngine = new AppEngine(newServer, newServer.getServerId());
	    		List<AppEngine> appEngineList = new ArrayList<>();
	    		appEngineList.add(appEngine);
	    		newServer.setAppEngines(appEngineList);
	    		loadBalancer.addServers(newServer);
	    		System.out.println(newServer.getServerName()+" has been added successfully");
		    }else if("5".equals(read)) {
		    	if(loadBalancer == null) {
		    		System.out.println("please start ELB first");
		    		continue;
		    	} 
		    	IRule rule = loadBalancer.getRule();
		    	if(rule instanceof RandomRule) {
		    		IRule newRule = new RoundRobinRule(loadBalancer);
		    		loadBalancer.setRule(newRule);
		    		System.out.println("the load balance rule has changed to RoundRobin Rule");
		    	}else if(rule instanceof RandomRule) {
		    		IRule newRule = new RandomRule(loadBalancer);
		    		loadBalancer.setRule(newRule);
		    		System.out.println("the load balance rule has changed to Random Rule");
		    	}
		    }else if("6".equals(read)) {
		    	if(loadBalancer == null) {
		    		System.out.println("please start ELB first");
		    		continue;
		    	} 
		    	List<IServer> upServerList = loadBalancer.getReachableServers();
		    	System.out.println("There are "+upServerList.size()+" more available services.");
		    }else if("7".equals(read)) {
//		    	for(int i = 0 ; i<15 ;i++) {
//		    		new Thread(new Runnable() {
//						@Override
//						public void run() {
//							IServer server = loadBalancer.chooseServer(null);
//					    	List<AppEngine> appEngineList = server.getAppEngines();
//					    	Random random = new Random();
//					        int invalidIndex = random.nextInt(appEngineList.size());
//					        appEngineList.get(invalidIndex).service();
//						}
//					},"thread"+i).start();
//		    	}
		    	if(loadBalancer == null) {
		    		System.out.println("please start ELB first");
		    		continue;
		    	} 
		    	IServer server = loadBalancer.chooseServer(null);
		    	List<AppEngine> appEngineList = server.getAppEngines();
		    	if(appEngineList == null || appEngineList.size() ==0) {
		    		System.out.println("There is no AppEngine instance on "+server.getServerName());
		    		continue;
		    	}
		    	Random random = new Random();
		        int invalidIndex = random.nextInt(appEngineList.size());
		        appEngineList.get(invalidIndex).service();
		    }else if("8".equals(read)) {
		    	System.out.println("Successfully quit the system!");
		    	break;
		    }else {
		    	System.out.println("Please re-enter the correct number");
		    }
		}
	}
	
	public static void showTips() {
		System.out.println("welcome to Distributed System,here are some commends you can execute:");
		System.out.println("1)  start ElB with default Random Loadbalance Rule");
		System.out.println("2)  start 3 instances with AppEngine configured");
		System.out.println("3)  kill one EAS instance randomly( this is called Monkey in chaos Engineering)");
		System.out.println("4)  statr one new EAS instance ");
		System.out.println("5)  change load balance rule of ELB");
		System.out.println("6)  show status of ELB and available EAS Servers");
		System.out.println("7)  send a /customer request to ELB,and display the output from AppEngine");
		System.out.println("8)  Exit the system");
	}
}
