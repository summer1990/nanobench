package com.alisoft.nb;

public class MeasureState implements Comparable<MeasureState> {
	private String label;
	private long startTime;
	private long endTime;
	private long index;
	private long numberOfMeasurement;

	public MeasureState(String label, long numberOfMeasurement, long index) {
		super();
		this.label = label;
		this.numberOfMeasurement = numberOfMeasurement;
		this.index = index;
	}
	
	public long getIndex() {
		return index;
	}

	public String getLabel() {
		return label;
	}

	public long getStartTime() {
		return startTime;
	}

	public long getEndTime() {
		return endTime;
	}
	
	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}
	
	public long getNumberOfMeasurement() {
		return numberOfMeasurement;
	}

	public long getMeasureTime() {
		return endTime - startTime;
	}

	public int compareTo(MeasureState another) {
		if (this.startTime > another.startTime) {
			return -1;
		} else if (this.startTime < another.startTime) {
			return 1;
		}
		else {
			return 0;
		}
	}
}
