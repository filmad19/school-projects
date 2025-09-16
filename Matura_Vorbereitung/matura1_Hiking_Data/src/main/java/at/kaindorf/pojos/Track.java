package at.kaindorf.pojos;

import jakarta.xml.bind.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

@XmlRootElement(name = "trk")
@XmlAccessorType(XmlAccessType.FIELD)
public class Track {

    @XmlElement
    private String name;

    @XmlElementWrapper(name = "trkseg")
    @XmlElement(name = "trkpt")
    private List<TrackingPoint> trackingPoints = new ArrayList<>();
}
