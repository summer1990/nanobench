package com.alisoft.nb.listener;

public final class Listeners {
	private Listeners(){};
	
	public static MeasureListener[] simple() {
		return new MeasureListener[] {new SimpleConsole(), new MemoryUsage()};
	}
	
	public static SimpleConsole simpleConsole() {
		return new SimpleConsole();
	}
	
	public static MemoryUsage memoryUsage() {
		return new MemoryUsage();
	}
}
