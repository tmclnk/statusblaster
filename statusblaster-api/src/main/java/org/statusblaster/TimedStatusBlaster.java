package org.statusblaster;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * Usage:
 * StatusBlaster blaster = new HttpStatusBlaster();
 * TimedStatusBlaster timedBlaster = new TimedStatusBlaster(blaster, "hello");
 * timedBlaster.start();
 * //... 
 * timedBlaster.stop(); // presumably do this on shutdown
 */
public class TimedStatusBlaster {
	private Logger logger = LoggerFactory.getLogger(getClass());
	private static final long INTERVAL = 1000;
	private final StatusBlaster blaster;
	// TODO allow caller to add and remove keys in a threadsafe way
	private final String[] keys;
	private final Thread thread;
	private boolean running = false;
	
	public TimedStatusBlaster(StatusBlaster blaster, String... keys){
		this.blaster = blaster;
		this.keys = keys;
		thread = new Thread(this::run, "StatusBlaster");
	}
	
	public void start(){
		running = true;
		thread.start();
	}
	
	public void stop() {
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			logger.warn("Blaster Thread interrupted", e);
		}
	}
	
	private void run(){
		while(this.running){
			for(String key : keys){
				blaster.blast(new StatusMessage(key, Status.NOT_DEAD_YET));
			}
			try {
				Thread.sleep(INTERVAL);
			} catch (InterruptedException e) {
				logger.warn("Blaster Thread", e);
			}
		}
	}
}
