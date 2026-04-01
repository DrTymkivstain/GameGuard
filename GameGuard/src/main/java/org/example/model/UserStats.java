package org.example.model;

import java.time.LocalDate;

public class UserStats {
    private final String date;
    private int minutesUsed;

    public UserStats(String date, int minutesUsed) {
        this.date = date;
        this.minutesUsed = minutesUsed;
    }

    public String getDate() { return date; }
    public int getMinutesUsed() { return minutesUsed; }
    public void addMinutes(int mins) { this.minutesUsed += mins; }
    public void reset() { this.minutesUsed = 0; }
}
