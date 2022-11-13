package com.example.lernen_trains.pojos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Train {
    private long id;
    private List<String> stations = new ArrayList<>();
    private String type;

    public Train(String line) {
        String[] tokens = line.split(";");

        this.id = Long.parseLong(tokens[0]);
        String stationsString = tokens[1];
        this.type = tokens[2];

        String[] stationsArray = stationsString.split("#");

        for(String s : stationsArray){
            stations.add(s);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Train train = (Train) o;

        if (!Objects.equals(stations, train.stations)) return false;
        return Objects.equals(type, train.type);
    }

    @Override
    public int hashCode() {
        int result = stations != null ? stations.hashCode() : 0;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }
}
