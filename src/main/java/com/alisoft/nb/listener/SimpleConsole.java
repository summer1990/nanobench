package com.alisoft.nb.listener;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import com.alisoft.nb.MeasureState;

public class SimpleConsole implements MeasureListener {
	private static final DecimalFormat integerFormat = new DecimalFormat(
			"#,##0.0");
	private List<MeasureState> timesList = new ArrayList<MeasureState>();

	public void onMeasure(MeasureState state) {
		synchronized (timesList) {
			timesList.add(state);
		}
		outputMeasureInfo(state);
	}

	private void outputMeasureInfo(MeasureState state) {
		synchronized (timesList) {
			if (timesList.size() % 50 == 0) {
				System.out.println();
			}
			System.out.print(".");
		}
		if (isEnd(state)) {
			long total = 0;
			for (MeasureState t : timesList) {
				total += t.getMeasureTime();
			}
			timesList.clear();
			StringBuffer sb = new StringBuffer("\n");
			sb.append(state.getLabel() + "\t").append("avg: ").append(
					format(total / state.getNumberOfMeasurement()
							/ 1000000)).append(" ms\t").append("total: ")
					.append(format(total / 1000000)).append(" ms\t").append(
							"calls: ").append(
							state.getNumberOfMeasurement()).append(
							" times\n");
			System.out.println(sb.toString());
		}
	}

	private String format(double value) {
		return integerFormat.format(value);
	}

	private boolean isEnd(MeasureState times) {
		synchronized (timesList) {
			return times.getNumberOfMeasurement() == timesList.size();
		}
	}

}
