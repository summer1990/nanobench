package com.alisoft.nb.listener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.alisoft.nb.MeasureInfo;

public class SimpleConsole implements MeasureListener {
	private List<MeasureInfo> timesList = Collections.synchronizedList(new ArrayList<MeasureInfo>());

	public void onMeasure(MeasureInfo measureInfo) {
		timesList.add(measureInfo);
		outputMeasureInfo(measureInfo);
	}

	private void outputMeasureInfo(MeasureInfo measureInfo) {
		if (measureInfo.getIndex() % 10 == 0) {
			System.out.println();
		}
		System.out.print(measureInfo.getIndex() + ".");
		if (isEnd(measureInfo)) {
			long total = 0;
			for (MeasureInfo t : timesList) {
				total += t.getMeasureTime();
			}
			timesList.clear();
			StringBuffer sb = new StringBuffer("\n");
			sb.append(measureInfo.getLabel() + "\t").append("avg: ").append(
					total / measureInfo.getNumberOfMeasurement() / 1000000)
					.append(" ms\t").append("total: ").append(total / 1000000)
					.append(" ms\t").append("calls: ").append(
							measureInfo.getNumberOfMeasurement()).append(
							" times\n");
			System.out.println(sb.toString());
		}
	}

	private synchronized boolean isEnd(MeasureInfo times) {
		return times.getNumberOfMeasurement() == timesList.size();
	}

}
