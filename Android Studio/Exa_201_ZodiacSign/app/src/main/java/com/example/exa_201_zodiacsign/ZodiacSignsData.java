package com.example.exa_201_zodiacsign;

import java.time.MonthDay;

public enum ZodiacSignsData {
    AQUARIUS("Wassermann",MonthDay.of(1, 20), R.drawable.aquarius),
    PISCES("Fische", MonthDay.of(2, 19), R.drawable.pisces),
    ARIES("Widder", MonthDay.of(3, 21), R.drawable.aries),
    TAURUS("Stier", MonthDay.of(4, 21), R.drawable.taurus),
    GEMINI("Zwillinge", MonthDay.of(5, 22), R.drawable.gemini),
    CANCER("Krebs", MonthDay.of(6, 22), R.drawable.cancer),
    LEO("Löwe", MonthDay.of(7, 23), R.drawable.leo),
    VIRGO("Jungfrau", MonthDay.of(8, 23), R.drawable.virgo),
    LIBRA("Waage", MonthDay.of(9, 23), R.drawable.libra),
    SCORPIUS("Skorpion", MonthDay.of(10, 23), R.drawable.scorpius),
    SAGITTARIUS("Schütze", MonthDay.of(11, 23), R.drawable.sagittarius),
    CAPRICORNUS("Steinbock", MonthDay.of(12, 21), R.drawable.capricornus);

    String name;
    MonthDay startDate;
    int pictureID;

    ZodiacSignsData(String name, MonthDay startDate, int pictureID) {
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

    public int getPictureID() {
        return pictureID;
    }
}
