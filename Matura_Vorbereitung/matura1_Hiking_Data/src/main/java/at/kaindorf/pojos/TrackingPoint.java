package at.kaindorf.pojos;

import at.kaindorf.xml.XmlLocalDateTimeAdapter;
import jakarta.xml.bind.annotation.*;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor

@XmlAccessorType(XmlAccessType.FIELD)
public class TrackingPoint {

    @XmlAttribute(name = "lat")
    private double lat;

    @XmlAttribute(name = "lon")
    private double lon;

    @XmlElement(name = "ele")
    private double elevation;

    @XmlElement(name = "time")
    @XmlJavaTypeAdapter(XmlLocalDateTimeAdapter.class)
    private LocalDateTime timestamp;
}
