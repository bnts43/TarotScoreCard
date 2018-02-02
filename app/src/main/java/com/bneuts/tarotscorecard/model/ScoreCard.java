package com.bneuts.tarotscorecard.model;

import java.time.LocalDate;

/**
 * Created by bneut on 02/02/2018.
 */

public class ScoreCard {
    private String name;
    private LocalDate date;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String toString() {
        return "{ ScoreCard name = " + name + " on day: " + date.toString() + " }";
    }
}
