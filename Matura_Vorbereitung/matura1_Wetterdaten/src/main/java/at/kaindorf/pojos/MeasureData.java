package at.kaindorf.pojos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MeasureData {
    private int day;
    private int month;
    private int year;
    private double value;
}
