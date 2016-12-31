package org.statusblaster.server.jetty;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.statusblaster.aggregator.StatusService;

public class HexHandler extends AbstractHandler  {
	@SuppressWarnings("unused")
	private Logger logger = LoggerFactory.getLogger(getClass());
	private final StatusService statusService;
	
	public HexHandler(StatusService statusService) {
		super();
		this.statusService = statusService;
	}

	@Override
	public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		// render responses to any page, even if it's
		// just static html off the user's disk
		response.setStatus(HttpServletResponse.SC_OK);
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/plain;charset=utf-8");
		statusService.toHex(target, response.getWriter());
		baseRequest.setHandled(true);
	}

}
