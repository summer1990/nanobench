package com.alisoft.nb.listener;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alisoft.nb.MeasureState;

public class SimpleConsole implements MeasureListener {
	private static final Log log = LogFactory.getLog(SimpleConsole.class);
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
			if (log.isDebugEnabled() && timesList.size() % 50 == 0) {
				System.out.println();

			}
			if (log.isDebugEnabled()) {
				System.out.print(state.getIndex() + ".");
			}
		}
		if (isEnd(state)) {
			long total = 0;
			for (MeasureState t : timesList) {
				total += t.getMeasureTime();
			}
			timesList.clear();
			StringBuffer sb = new StringBuffer("\n");
			sb.append(state.getLabel() + "\t").append("avg:").append(
					format(total / state.getMeasurements() / 1000000)).append(
					" ms\t").append("total:").append(format(total / 1000000))
					.append(" ms\t").append("running ").append(
							state.getMeasurements()).append(" times\t").append(
							"in ").append(state.getThreadCount()).append(
							" Threads");
			log.info(sb.toString());
		}
	}

	private String format(double value) {
		return integerFormat.format(value);
	}

	private boolean isEnd(MeasureState times) {
		synchronized (timesList) {
			return times.getMeasurements() == timesList.size();
		}
	}

}
