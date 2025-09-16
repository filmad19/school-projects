package at.kaindorf.pojos;

import lombok.Data;


@Data
public class Result {
    private String name;
    private double distance;
    private double elePos;
    private double eleNeg;
    private long analyzeTime;

    public Result(String name) {
        this.name = name;
    }
}
