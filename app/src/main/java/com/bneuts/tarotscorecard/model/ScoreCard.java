package com.bneuts.tarotscorecard.model;

import java.util.Date;

/**
 * Created by Benoît Neuts on 02/02/2018.
 * ScoreCard POJO
 */

public class ScoreCard {
    private String name;
    private Date date;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String toString() {
        return "{ ScoreCard name = " + name + " on day: " + date + " }";
    }
}
