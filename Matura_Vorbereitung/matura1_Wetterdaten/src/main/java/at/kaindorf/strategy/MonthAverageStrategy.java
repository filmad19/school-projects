package at.kaindorf.strategy;

import at.kaindorf.pojos.*;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MonthAverageStrategy implements IStrategy {

    @Override
    public StrategyResult analyze(QueueEntry queueEntry) {

        List<StatEntry> statEntryList = queueEntry.getMeasures()
                .stream()
                .collect(Collectors.groupingBy(measureData -> measureData.getYear() + "/" + String.format("%02d", measureData.getMonth())))
                .entrySet()
                .stream()
                .map(entry -> {
                    String x = entry.getKey();
                    double y = entry.getValue()
                            .stream()
                            .mapToDouble(MeasureData::getValue)
                            .average()
                            .getAsDouble();

                    return new StatEntry(x, y);
                })
                .sorted(Comparator.comparing(StatEntry::getX))
                .collect(Collectors.toList());


        return new StrategyResult(queueEntry.getCountry(), queueEntry.getCity(), StatisticStrategy.MONTH_AVERAGE, statEntryList);
    }
}
