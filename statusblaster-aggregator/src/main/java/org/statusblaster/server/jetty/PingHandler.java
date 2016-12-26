package org.statusblaster.server.jetty;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.statusblaster.Status;
import org.statusblaster.StatusMessage;
import org.statusblaster.aggregator.StatusService;

public class PingHandler extends AbstractHandler {
	private final StatusService statusService;
	
	public PingHandler(StatusService statusService){
		this.statusService = statusService;
	}

	public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/plain;charset=utf-8");
		response.setStatus(HttpServletResponse.SC_OK);
		baseRequest.setHandled(true);
		
		statusService.notify(new StatusMessage(target, Status.NOT_DEAD_YET));
	}
}
