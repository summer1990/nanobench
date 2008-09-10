package com.alisoft.nb.listener;

import java.text.DecimalFormat;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alisoft.nb.MeasureState;
import com.alisoft.nb.util.MemoryUtil;

public class MemoryUsage implements MeasureListener {
	private static final Log log = LogFactory.getLog(MemoryUsage.class);
	private static final DecimalFormat integerFormat = new DecimalFormat(
			"#,##0");
	private AtomicInteger count = new AtomicInteger();

	public void onMeasure(MeasureState state) {
		count.getAndIncrement();
		outputMeasureInfo(state);
	}

	private void outputMeasureInfo(MeasureState state) {
		if (isEnd(state)) {

			StringBuffer sb = new StringBuffer("\n");
			sb.append("memory-usage: ").append(state.getLabel()).append("\t")
					.append(format(MemoryUtil.memoryUsed())).append(" ");
			log.info(sb.toString());
		}
	}

	private String format(double value) {
		return integerFormat.format(value);
	}

	private boolean isEnd(MeasureState state) {
		return count.get() == state.getMeasurements();
	}

}
