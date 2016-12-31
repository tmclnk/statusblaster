package org.statusblaster.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.statusblaster.aggregator.StatusService;
import org.statusblaster.aggregator.TimeBuckets;

public class LRUStatusService implements StatusService {
	private Map<String, TimeBuckets> pings = new Hashtable<>();
	private int size = 64;
	private long interval = 1000;
	
	@Override
	public List<String> keys() {
		List<String> keys = new ArrayList<>(pings.keySet());
		Collections.sort(keys);
		return keys;	
	}

	@Override
	public void notify(String key) {
		key = key.toLowerCase();
		TimeBuckets buckets = pings.get(key);
		if(buckets == null){
			buckets = new TimeBuckets(size, interval);
			pings.put(key, buckets);
		}
		buckets.ping();
	}

	@Override
	public void toJSON(String key, Appendable out) throws IOException {
		TimeBuckets bucket = pings.get(key);
		if(bucket != null){
			bucket.toJSON(out);
		}
	}
	@Override
	public void toHex(String key, Appendable out) throws IOException {
		TimeBuckets bucket = pings.get(key);
		if(bucket != null){
			bucket.toHex(out);
		}
	}	
}
