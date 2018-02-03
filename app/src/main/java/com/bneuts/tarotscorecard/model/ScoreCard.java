package com.bneuts.tarotscorecard.model;

import java.util.Date;

/**
 * Created by Benoît Neuts on 02/02/2018.
 * ScoreCard POJO
 */

public class ScoreCard {
    private String name;
    private long date;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public Date getDateAsDate() {
        return new Date(date);
    }

    public String toString() {
        return "{ ScoreCard name = " + name + " on day: " + date + " }";
    }
}
