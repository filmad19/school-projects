package at.kaindorf.pojos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StrategyResult {
    private String country;
    private String city;
    private StatisticStrategy statisticStrategy;
    private List<StatEntry> statEntryList;
}
