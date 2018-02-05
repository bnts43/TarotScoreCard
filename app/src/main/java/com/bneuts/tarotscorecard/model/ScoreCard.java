package com.bneuts.tarotscorecard.model;

import android.support.annotation.NonNull;

import java.util.Date;
import java.util.List;

/**
 * Created by Benoît Neuts on 02/02/2018.
 * ScoreCard POJO
 */

public class ScoreCard {

    @NonNull
    private String Id;
        @NonNull
        public String getScoreId() { return Id; }
        public void setScoreId(@NonNull String scoreId) { this.Id = scoreId; }

    private String name;
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

    private Date date;
        public Date getDate() { return date; }
        public void setDate(Date date) { this.date = date; }

    private List<String> players;
        public List<String> getPlayers() { return players; }
        public void setPlayers(List<String> players) { this.players = players; }

    public String toString() {
        return "{ ScoreCard name = " + name + " on day: " + date + " }";
    }
}
