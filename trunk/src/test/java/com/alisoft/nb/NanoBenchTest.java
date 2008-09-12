package com.alisoft.nb;

import org.junit.Test;

import com.alisoft.nb.Nano;

public class NanoBenchTest {
	@Test
	public void testMeasureWithSingleThread() {
		Nano.bench().measure("single-thread", new Runnable() {
			@SuppressWarnings("static-access")
			public void run() {
				try {
					Thread.currentThread().sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
	}
	@Test
	public void testMeasureWithMultiThreads() {
		Nano.bench().threads(3).measure("multi-threads", new Runnable() {
			@SuppressWarnings("static-access")
			public void run() {
				try {
					Thread.currentThread().sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
	}
}
