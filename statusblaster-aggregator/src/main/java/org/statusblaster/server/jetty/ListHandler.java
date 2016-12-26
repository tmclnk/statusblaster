package org.statusblaster.server.jetty;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.statusblaster.aggregator.StatusService;

import com.google.gson.Gson;

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
			Gson gson = new Gson();
			gson.toJson(statusService.keys(), response.getWriter());
			baseRequest.setHandled(true);
		}
}
