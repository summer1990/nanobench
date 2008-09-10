package com.alisoft.benchmark;

import java.util.Hashtable;
import java.util.Random;

import org.junit.Test;

import com.alisoft.nb.Nano;


public class MapBenchTest {
	@Test
	public void testHashTable() {
		final Hashtable<Integer, Integer> hashtable = new Hashtable<Integer, Integer>();
		Nano.bench().measurements(10000).threads(10).measure("hashtable", new Runnable() {
			public void run() {
				Random random = new Random();
				for (int i = 0; i < 1000; i++) {
					hashtable.put(i, i);
					if (i % 2 == 0) {
						for (int j = 0; j < 3; j++) {							
							hashtable.get(random.nextInt(10000));
						}
					}
				}
				
			}
		});
	}
}
