package org.statusblaster.aggregator;

import java.io.IOException;
import java.util.BitSet;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class TimeBuckets {
	private final BlockingQueue<Long> queue;
	private final int size;
	private final long interval;
	private long prev = 0;

	public TimeBuckets(int size, long interval){
		this.size = size;
		this.interval = interval;
		queue =  new LinkedBlockingQueue<>(size);
	}
	
	public boolean ping(){
		Long now = System.currentTimeMillis();
		// limit how frequently we can take in new data elements
		if((now - prev) > interval){
			// add to end of queue; if queue is full, 
			// pull from the head to make space
			if(!queue.offer(now)) {
				queue.poll();
				queue.add(now);
			}
			prev = now;
			return true;
		}
		return false;
	}
	
	public void toJSON(Appendable out) throws IOException{
		BitUtils.toJSON(toBitSet(), size, out);
	}
	
	public void toHex(Appendable out) throws IOException{
		BitSet bits = toBitSet();
		for(byte b: bits.toByteArray()){
			String str = String.format("%02x",b);
			out.append(str);
		}
	}
	
	public BitSet toBitSet(){
		final long now = System.currentTimeMillis();
		BitSet bits = new BitSet(size);
		for(int i = 0; i < size; i++){
			// bounds for the current bucket
			long end = now - i * interval; // eg now
			long start = now - (i + 1) * interval; // eg 1 second ago
		
			for(long ts : queue){
				if(ts <= end && ts > start){
//					System.out.println("(" + start + "," + end + "]");
					bits.set(i);
				}
			}
		}
		return bits;
	}
}
