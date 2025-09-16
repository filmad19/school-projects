package at.kaindorf.pojos;

import jakarta.xml.bind.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

@XmlRootElement(name = "GoldMuenzen")
@XmlAccessorType(XmlAccessType.FIELD)
public class GoldMuenze {

    @XmlElementWrapper(name = "Messungen")
    @XmlElement(name = "Messung")
    private List<Measurement> measurementList;
}
