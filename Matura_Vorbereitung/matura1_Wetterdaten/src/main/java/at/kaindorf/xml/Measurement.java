package at.kaindorf.xml;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

@XmlAccessorType(XmlAccessType.FIELD)
public class Measurement {

    private Country country;

    private String state;
    private String city;

    private Integer month;
    private Integer day;
    private Integer year;
    private Double avgtemperature;
}
