package com.alisoft.benchmark;

import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Random;

import org.junit.Test;

import com.alisoft.nb.Nano;

public class MapBenchTest {
	@Test
	public void testHashTable() {
		final Hashtable<Integer, Integer> hash = new Hashtable<Integer, Integer>();
		Nano.bench().measurements(1000).threads(10).measure("hashtable",
				new Runnable() {
					public void run() {
						Random random = new Random(10000);
						for (int i = 0; i < 1000; i++) {
							hash.put(i, i);
							if (i % 2 == 0) {
								for (int j = 0; j < 3; j++) {
									hash.get(random.nextInt());
								}
							}
						}
					}
				});
	}

	@Test
	public void testSyncHashMap() {
		final Map<Integer, Integer> hash = Collections
				.synchronizedMap(new HashMap<Integer, Integer>());
		Nano.bench().measurements(1000).threads(10).measure("sync-hashmap",
				new Runnable() {
					public void run() {
						Random random = new Random(10000);
						for (int i = 0; i < 1000; i++) {
							hash.put(i, i);
							if (i % 2 == 0) {
								for (int j = 0; j < 3; j++) {
									hash.get(random.nextInt());
								}
							}
						}
					}
				});
	}
}
