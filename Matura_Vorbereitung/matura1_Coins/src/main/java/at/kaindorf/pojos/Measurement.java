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
public class Measurement {

    @XmlElement(name = "Radius")
    private Double radius;

    @XmlElement(name = "Hoehe")
    private Double height;
}
