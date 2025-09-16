package at.kaindorf.pojos;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

@XmlAccessorType(XmlAccessType.FIELD)
public class Statistics {

    @XmlElement(name = "Mittelwert")
    private double mean;

    @XmlElement(name = "STD")
    private double stddev;
}
