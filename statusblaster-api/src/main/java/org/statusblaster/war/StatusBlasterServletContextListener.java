package org.statusblaster.war;

import java.net.MalformedURLException;
import java.util.Arrays;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.statusblaster.HttpStatusBlaster;
import org.statusblaster.TimedStatusBlaster;
/**
 * A simple ServletContextListener to launch a thread and periodically
 * ping a set of keys defined in the ServletContext.
 * 
 * Register this ServletContextListener and set the key
 * {@value CONTEXT_ATTRIBUTE_URL} to the base url you wish to ping,
 * and {@value CONTEXT_ATTRIBUTE_KEYS} to a comma-separated list of 
 * keys you wish to ping.
 */
public class StatusBlasterServletContextListener implements ServletContextListener{
	public static final String CONTEXT_ATTRIBUTE_KEYS="StatusBlastKeys";
	public static final String CONTEXT_ATTRIBUTE_URL="StatusBlastUrl";
	private final Logger logger = LoggerFactory.getLogger(getClass());
	private TimedStatusBlaster blaster;
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		String keyString = (String) sce.getServletContext().getAttribute(CONTEXT_ATTRIBUTE_KEYS);
		String urlString = (String) sce.getServletContext().getAttribute(CONTEXT_ATTRIBUTE_URL);
		if(keyString != null && urlString != null){
			String[] keys = keyString.split("[\\w,]+");
			HttpStatusBlaster httpBlaster;
			try {
				httpBlaster = new HttpStatusBlaster(urlString);
			} catch (MalformedURLException e) {
				throw new RuntimeException(e);
			}
			blaster = new TimedStatusBlaster(httpBlaster, keys);
			blaster.start();
			logger.info("Blasting Status periodically to {}", Arrays.asList(keys));
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		blaster.stop();
	}
}
