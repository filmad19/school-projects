package at.kaindorf.pojos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueueEntry {
    private String city;
    private String country;
    private String region;
    private List<MeasureData> measures;
}
