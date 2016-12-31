package org.statusblaster.server.jetty;

import java.io.IOException;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.statusblaster.aggregator.StatusService;

public class ListHandler extends AbstractHandler  {
		private final StatusService statusService;
		
		public ListHandler(StatusService statusService) {
			super();
			this.statusService = statusService;
		}

		@Override
		public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response)
				throws IOException, ServletException {
			response.setHeader("Access-Control-Allow-Origin", "*");
		
			response.setContentType("text/json;charset=utf-8");
			response.setStatus(HttpServletResponse.SC_OK);
			String json = statusService.keys().stream().map(s-> "\"" + s + "\"").collect(Collectors.joining(",", "[", "]"));
			response.getWriter().append(json);
			baseRequest.setHandled(true);
		}
}
