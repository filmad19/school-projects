package at.kaindorf.pojos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatEntry {
    private String x;
    private double y;

    @Override
    public String toString() {
        return "(" + x + "/" + String.format("%.2f", y) + ")";
    }
}
