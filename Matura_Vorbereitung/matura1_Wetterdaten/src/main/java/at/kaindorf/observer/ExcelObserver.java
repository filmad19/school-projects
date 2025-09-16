package at.kaindorf.observer;

import at.kaindorf.pojos.StatEntry;
import at.kaindorf.pojos.StrategyResult;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

public class ExcelObserver implements StatisticsObserver {

    public ExcelObserver(StatisticsSubject statisticsSubject) {
        statisticsSubject.attach(this);
    }

    @Override
    public void update(StrategyResult strategyResult) {

        String filename = strategyResult.getCity() + "_" + strategyResult.getStatisticStrategy().name() + ".csv";

        Path path = Paths.get(System.getProperty("user.dir"), "src", "main", "resources", "out", filename);

        String header = strategyResult.getCity() + ";" + strategyResult.getCountry() + ";" + strategyResult.getStatisticStrategy();

        String time = "Time;" + strategyResult.getStatEntryList()
                .stream()
                .map(StatEntry::getX)
                .collect(Collectors.joining(";"));

        String average = "Average;" + strategyResult.getStatEntryList()
                .stream()
                .map(statEntry -> String.format("%.2f", statEntry.getY()))
                .collect(Collectors.joining(";"));

        String output = header + "\n" + time + "\n" + average;

        try {
            Files.writeString(path, output);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
