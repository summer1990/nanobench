package com.alisoft.nb;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import com.alisoft.nb.listener.Listeners;
import com.alisoft.nb.listener.MeasureListener;

public class Nano implements Benchmark {
	private int numberOfMeasurement = 20;
	private int numberOfWarmUp = 10;
	private int numberOfThread = 1;
	private CountDownLatch measureLatch;
	private CountDownLatch warmUpLatch;
	private List<MeasureListener> listeners;
	
	public static Nano bench() {
		return new Nano();
	}
	
	public Nano measurements(int numberOfMeasurement) {
		this.numberOfMeasurement = numberOfMeasurement;
		return this;
	}
	
	public Nano threads(int numberOfThread) {
		this.numberOfThread = numberOfThread;
		return this;
	}

	protected Nano() {
		this.listeners = new CopyOnWriteArrayList<MeasureListener>();
		this.listeners.add(Listeners.simpleConsole());
	}

	public void measure(Runnable task) {
		measure("", task);
	}

	public void measure(String label, Runnable task) {
		this.warmUpLatch = new CountDownLatch(this.numberOfWarmUp);
		this.measureLatch = new CountDownLatch(this.numberOfMeasurement);
		doWarmup(task);
		doMeasure(label, task);
	}

	private void doMeasure(String label, Runnable task) {
		Executor executor = Executors.newFixedThreadPool(this.numberOfThread);
		for (int i = 0; i < this.numberOfMeasurement; i++) {
			executor.execute(new TimeMeasureProxy(new MeasureInfo(label,
					numberOfMeasurement, i), task, this.listeners,
					this.measureLatch));
		}

		try {

			this.measureLatch.await();
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	private void doWarmup(Runnable task) {
		Executor executor = Executors.newSingleThreadExecutor();
		for (int i = 0; i < this.numberOfWarmUp; i++) {
			executor.execute(new TimeMeasureProxy(new MeasureInfo("_warmup_",
					this.numberOfWarmUp, i), task, this.listeners,
					this.warmUpLatch));
		}
		try {
			this.warmUpLatch.await();
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	void addListener(MeasureListener listener) {
		this.listeners.add(listener);
	}

	private static class TimeMeasureProxy implements Runnable {
		private MeasureInfo times;
		private Runnable runnable;
		private List<MeasureListener> listeners;
		private CountDownLatch measureLatch;

		public TimeMeasureProxy(MeasureInfo times, Runnable runnable,
				List<MeasureListener> listeners, CountDownLatch measureLatch) {
			super();
			this.times = times;
			this.runnable = runnable;
			this.listeners = listeners;
			this.measureLatch = measureLatch;
		}

		public void run() {
			this.times.setStartTime(System.nanoTime());
			this.runnable.run();
			this.times.setEndTime(System.nanoTime());
			notifyMeasurement(times);
			this.measureLatch.countDown();
		}

		private void notifyMeasurement(MeasureInfo times) {
			for (MeasureListener listener : this.listeners) {
				listener.onMeasure(times);
			}
		}
	}

}
