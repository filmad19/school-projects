package at.kaindorf.xml;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class XmlLocalDateTimeAdapter extends XmlAdapter<String, LocalDateTime> {

    @Override
    public LocalDateTime unmarshal(String v) throws Exception {
        return LocalDateTime.parse(v, DateTimeFormatter.ISO_DATE_TIME);
    }

    @Override
    public String marshal(LocalDateTime v) throws Exception {
        return v.format(DateTimeFormatter.ISO_DATE_TIME);
    }
}
