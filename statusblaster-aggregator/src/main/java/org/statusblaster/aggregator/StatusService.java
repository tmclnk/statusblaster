package org.statusblaster.aggregator;

import java.util.List;

import org.statusblaster.StatusMessage;

public interface StatusService {
	public void notify(StatusMessage message);
	public List<Long> statusOf(String key);
	public List<String> keys();
}
