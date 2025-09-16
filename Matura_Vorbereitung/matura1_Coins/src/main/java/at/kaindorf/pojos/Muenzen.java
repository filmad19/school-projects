package at.kaindorf.pojos;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

@XmlRootElement(name = "Muenzen")
@XmlAccessorType(XmlAccessType.FIELD)
public class Muenzen {

    @XmlElement(name = "Durchmesser")
    private Statistics diameter;

    @XmlElement(name = "Dicke")
    private Statistics thickness;

    @XmlElement(name = "Masse")
    private Statistics mass;
}
