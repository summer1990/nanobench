package com.alisoft.nb.listener;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.alisoft.nb.MeasureInfo;

public class SimpleConsole implements MeasureListener {
	private static final DecimalFormat integerFormat = new DecimalFormat(
			"#,##0.0");
	private List<MeasureInfo> timesList = new ArrayList<MeasureInfo>();

	public void onMeasure(MeasureInfo measureInfo) {
		synchronized (timesList) {
			timesList.add(measureInfo);
		}
		outputMeasureInfo(measureInfo);
	}

	private void outputMeasureInfo(MeasureInfo measureInfo) {
		synchronized (timesList) {
			if (timesList.size() % 50 == 0) {
				System.out.println();
			}
			System.out.print(".");
		}
		if (isEnd(measureInfo)) {
			long total = 0;
			for (MeasureInfo t : timesList) {
				total += t.getMeasureTime();
			}
			timesList.clear();
			StringBuffer sb = new StringBuffer("\n");
			sb.append(measureInfo.getLabel() + "\t").append("avg: ").append(
					format(total / measureInfo.getNumberOfMeasurement()
							/ 1000000)).append(" ms\t").append("total: ")
					.append(format(total / 1000000)).append(" ms\t").append(
							"calls: ").append(
							measureInfo.getNumberOfMeasurement()).append(
							" times\n");
			System.out.println(sb.toString());
		}
	}

	private String format(double value) {
		return integerFormat.format(value);
	}

	private boolean isEnd(MeasureInfo times) {
		synchronized (timesList) {
			return times.getNumberOfMeasurement() == timesList.size();
		}
	}

}
