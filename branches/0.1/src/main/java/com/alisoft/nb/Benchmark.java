package com.alisoft.nb;


public interface Benchmark {
	void measure(Runnable task);
	void measure(String label, Runnable task);
}
