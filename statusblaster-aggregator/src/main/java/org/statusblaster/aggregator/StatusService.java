package org.statusblaster.aggregator;

import java.io.IOException;
import java.util.List;

public interface StatusService {
	public void notify(String key);
	public List<String> keys();
	public void toJSON(String key, Appendable out) throws IOException;
	public void toHex(String key, Appendable out) throws IOException;
}
