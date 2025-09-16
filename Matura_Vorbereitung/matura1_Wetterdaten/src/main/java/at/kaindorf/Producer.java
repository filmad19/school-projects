package at.kaindorf;

import at.kaindorf.pojos.MeasureData;
import at.kaindorf.pojos.Queue;
import at.kaindorf.pojos.QueueEntry;
import at.kaindorf.xml.FileAccess;
import at.kaindorf.xml.Measurement;

import java.util.List;
import java.util.stream.Collectors;

public class Producer implements Runnable{

    private Queue queue;
    private String currentCountry;
    private static FileAccess fileAccess = FileAccess.getInstance();

    public Producer(Queue queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        currentCountry = fileAccess.getNextCountry();

        while (currentCountry != null) {

            List<QueueEntry> queueEntries = fileAccess.readXMLTempData().getMeasurementList()
                    .stream()
                    .filter(m -> m.getCountry().getCountryName().equals(currentCountry))
                    .collect(Collectors.groupingBy(Measurement::getCity))
                    .entrySet()
                    .stream()
                    .map(entry -> {

                        String city = entry.getKey();
                        String country = entry.getValue().get(0).getCountry().getCountryName();
                        String region = entry.getValue().get(0).getCountry().getRegion();

                        List<MeasureData> measureDataList = entry.getValue().stream()
                                .map(m -> new MeasureData(m.getDay(), m.getMonth(), m.getYear(), m.getAvgtemperature()))
                                .collect(Collectors.toList());

                        return new QueueEntry(city, country, region, measureDataList);
                    })
                    .collect(Collectors.toList());

            long countOfEntries = queueEntries.stream()
                    .flatMap(entry -> entry.getMeasures().stream())
                    .count();

            System.out.println("Country: " + currentCountry + " has " + countOfEntries + " entries");

            for(QueueEntry queueEntry : queueEntries) {

                synchronized (queue) {
                    while (queue.isFull()) {
                        try {
                            queue.wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }

                    queue.pushEntry(queueEntry);
                    queue.notifyAll();
                }
            }

            currentCountry = fileAccess.getNextCountry();
        }
    }
}
