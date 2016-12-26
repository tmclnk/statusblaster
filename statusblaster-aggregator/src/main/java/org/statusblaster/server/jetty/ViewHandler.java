package org.statusblaster.server.jetty;

import java.io.IOException;
import java.util.BitSet;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.statusblaster.aggregator.StatusService;

/**
 * Render a JSON list for a given key. 
 *
 */
public class ViewHandler extends AbstractHandler  {
	@SuppressWarnings("unused")
	private Logger logger = LoggerFactory.getLogger(getClass());
	private final StatusService statusService;
	
	public ViewHandler(StatusService statusService) {
		super();
		this.statusService = statusService;
	}

	@Override
	public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		response.setHeader("Access-Control-Allow-Origin", "*");
		List<Long> pings = statusService.statusOf(target);
		if(pings == null){
			return;
		}
		
		// make buckets
		Long now = System.currentTimeMillis();

		BitSet bits = new BitSet();
		long[] prev = {now};
		int i = 0;
		for(long l = now; l > now - 60000; l-=1000){
			final long n = l;
			boolean match = pings.stream().anyMatch(ts ->{
				return ts > n && ts < prev[0];
			});
			bits.set(i++, match);
			prev[0] = l;
		}
		
		response.setContentType("text/plain;charset=utf-8");
		response.setStatus(HttpServletResponse.SC_OK);

		response.setStatus(HttpServletResponse.SC_OK);
		String str = DatatypeConverter.printBase64Binary(bits.toByteArray());
		response.getWriter().write(str);
		
		baseRequest.setHandled(true);
	}
}
