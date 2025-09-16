package at.kaindorf.strategy;

import at.kaindorf.pojos.*;
import at.kaindorf.xml.Measurement;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class YearAverageStrategy implements IStrategy {

    @Override
    public StrategyResult analyze(QueueEntry queueEntry) {

        List<StatEntry> statEntryList = queueEntry.getMeasures()
                .stream()
                .collect(Collectors.groupingBy(MeasureData::getYear))
                .entrySet()
                .stream()
                .map(entry -> {
                    String x = String.valueOf(entry.getKey());
                    double y = entry.getValue()
                            .stream()
                            .mapToDouble(MeasureData::getValue)
                            .average()
                            .getAsDouble();

                    return new StatEntry(x, y);
                })
                .sorted(Comparator.comparing(StatEntry::getX))
                .collect(Collectors.toList());


        return new StrategyResult(queueEntry.getCountry(), queueEntry.getCity(), StatisticStrategy.YEAR_AVERAGE, statEntryList);
    }
}
