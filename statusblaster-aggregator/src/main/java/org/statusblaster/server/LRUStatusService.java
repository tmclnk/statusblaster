package org.statusblaster.server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.statusblaster.StatusMessage;
import org.statusblaster.aggregator.StatusService;

public class LRUStatusService implements StatusService {
	private Logger logger = LoggerFactory.getLogger(getClass());
	/** minimum time between puts */
	private static final long interval = 1000;
	private Map<String, List<Long>> pings;
	{
		pings = new HashMap<>();
	}
	
	@Override
	public List<String> keys() {
		List<String> keys = new ArrayList<>(pings.keySet());
		Collections.sort(keys);
		return keys;	
	}

	@Override
	public void notify(StatusMessage message) {
		String key = message.getKey();
		List<Long> times = pings.get(key);
		final long now = System.currentTimeMillis();
		if(times == null){
			// meh, good enough
			times = Collections.synchronizedList( new ArrayList<>() );
			pings.put(key, times);
			times.add(now);
			logger.trace("{}:{}", key, message.getStatus());
		} else {
			final long lastPing = times.get(times.size() - 1);
			if(now - lastPing > interval){
				times.add(now);
				logger.trace("{}:{}", key, message.getStatus());
			}
		}
	}

	@Override
	public List<Long> statusOf(String key) {
		return this.pings.get(key.toLowerCase());
	}
}
