package org.statusblaster.aggregator;

import static org.junit.Assert.assertEquals;

import java.io.StringWriter;

import org.junit.Test;

public class TimeBucketsTest {

	/** 
	 * A horribly slow and nondeterministic test which is indicative
	 * of a way to not write unit tests.
	 * @throws Exception
	 */
	@Test
	public void testJSON() throws Exception {
		long interval = 100;
		TimeBuckets buckets = new TimeBuckets(8, interval);
	
		buckets.ping();
		Thread.sleep(interval + 1);
		Thread.sleep(interval + 1);
		buckets.ping();
		Thread.sleep(interval + 1);
		Thread.sleep(interval + 1);
		buckets.ping();
		Thread.sleep(interval + 1);
		buckets.ping();
		Thread.sleep(interval + 1);

		StringWriter writer = new StringWriter();
		buckets.toJSON(writer);
		String expected= "[0,1,1,0,1,0,1,0]"; // hex 6A
		System.out.println(writer.toString());
		assertEquals(expected, writer.toString());
	}

}
