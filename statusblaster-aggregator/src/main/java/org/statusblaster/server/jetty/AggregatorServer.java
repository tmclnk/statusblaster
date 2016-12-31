package org.statusblaster.server.jetty;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.statusblaster.StatusBlasterConstants;
import org.statusblaster.aggregator.StatusService;
import org.statusblaster.server.LRUStatusService;

public class AggregatorServer {
	
	public static void main(String[] args) throws Exception {
		System.setProperty("org.slf4j.simpleLogger.log.org.statusblaster", "debug");
	
		StatusService statusService = new LRUStatusService();
		
		Server server = new Server(StatusBlasterConstants.DEFAULT_HTTP_PING_PORT);
		
		ContextHandlerCollection contextHandlers = new ContextHandlerCollection();
		
		// one context to listen to incoming status stuff
		ContextHandler pingHandler = new ContextHandler("/ping");
		pingHandler.setHandler(new PingHandler(statusService));
		contextHandlers.addHandler(pingHandler);

		// one context to show statuses (json?)
		ContextHandler jsonHandler = new ContextHandler("/json");
		jsonHandler.setHandler(new JsonHandler(statusService));
		contextHandlers.addHandler(jsonHandler);
		
		// one context to show statuses (hex?)
		ContextHandler hexHandler = new ContextHandler("/hex");
		hexHandler.setHandler(new HexHandler(statusService));
		contextHandlers.addHandler(hexHandler);
	
		// context to just list available apps
		ContextHandler listHandler = new ContextHandler("/list");
		listHandler.setHandler(new ListHandler(statusService));
		contextHandlers.addHandler(listHandler);
		
		server.setHandler(contextHandlers);
		server.start();
		server.join();
	}
}
