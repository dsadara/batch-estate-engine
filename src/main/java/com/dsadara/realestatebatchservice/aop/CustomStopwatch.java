package com.dsadara.realestatebatchservice.aop;

import lombok.Getter;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;

@Component
@Getter
public class CustomStopwatch {
    private final Map<String, StopWatch> stopWatches = new HashMap<>();
    private StopWatch totalStopWatch = new StopWatch();

    public void start(String taskName) {
        if (!stopWatches.containsKey(taskName)) {
            stopWatches.put(taskName, new StopWatch(taskName));
        }
        StopWatch stopWatch = stopWatches.get(taskName);
        stopWatch.start();
        totalStopWatch.start();
    }

    public void stop(String taskName) {
        if (!stopWatches.containsKey(taskName)) {
            return;
        }
        StopWatch stopWatch = stopWatches.get(taskName);
        stopWatch.stop();
        totalStopWatch.stop();
    }

    public String prettyPrint() {
        StringBuilder sb = new StringBuilder();
        sb.append('\n');
        sb.append("---------------------------------------------\n");
        sb.append("ns         %     Task name\n");
        sb.append("---------------------------------------------\n");
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMinimumIntegerDigits(9);
        nf.setGroupingUsed(false);
        NumberFormat pf = NumberFormat.getPercentInstance();
        pf.setMinimumIntegerDigits(3);
        pf.setGroupingUsed(false);
        stopWatches.forEach((s, stopWatch) -> {
            sb.append(nf.format(stopWatch.getTotalTimeNanos())).append("  ");
            sb.append(pf.format((double) stopWatch.getTotalTimeNanos() / totalStopWatch.getTotalTimeNanos())).append("  ");
            sb.append(stopWatch.getId()).append('\n');
        });
        sb.append("---------------------------------------------\n");
        sb.append(totalStopWatch.getTotalTimeNanos()).append(" ns").append("  <= Total Execution Time \n");
        sb.append("---------------------------------------------\n");
        return sb.toString();
    }

    public void reset() {
        stopWatches.clear();
        totalStopWatch = new StopWatch();
    }

}
