package org.statusblaster.aggregator;

import java.io.IOException;
import java.util.BitSet;

public class BitUtils {
	public static void toJSON(BitSet bits, int size, Appendable out) throws IOException {
		out.append('[');
		for(int i = 0; i < size; i++){
			out.append(bits.get(i) ? '1' : '0');
			if(i < size - 1) out.append(',');
		}
		out.append(']');
	}
}
