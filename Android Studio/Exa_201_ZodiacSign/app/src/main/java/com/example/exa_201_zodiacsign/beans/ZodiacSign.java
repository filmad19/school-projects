package com.example.exa_201_zodiacsign.beans;

import java.time.MonthDay;
import java.time.format.DateTimeFormatter;

public class ZodiacSign {
    private String name;
    private MonthDay startDate;
    private int pictureID;
    private static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("dd.MMMM");

    public ZodiacSign(String name, MonthDay startDate, int pictureID) {
        this.name = name;
        this.startDate = startDate;
        this.pictureID = pictureID;
    }


    public String getName() {
        return name;
    }

    public MonthDay getStartDate() {
        return startDate;
    }

    public String formateDate(MonthDay date) {
        return date.format(DTF);
    }

    public int getPictureID(){
        return pictureID;
    }
}
