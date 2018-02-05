package com.bneuts.tarotscorecard.model;

import android.support.annotation.NonNull;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Benoît Neuts on 02/02/2018.
 * ScoreCard POJO
 */

public class ScoreCard {

    public ScoreCard(String name, Date date, @NonNull Map<String, Long> players) {
        this.name = name;
        this.date = date;
        this.players = players;
    }

    private String Id;
        @NonNull
        public String getId() { return Id; }
        private void setId(@NonNull String scoreId) { this.Id = scoreId; }

    private String name;
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

    private Date date;
        public Date getDate() { return date; }
        public void setDate(Date date) { this.date = date; }

    @NonNull
    private Map<String,Long> players = new HashMap<>();
        public Map<String,Long> getPlayers() { return players; }
        public void setPlayers(Map<String,Long> players) { this.players = players; }


    public String toString() {
        return "{ ScoreCard name = " + name + " on day: " + date + " }";
    }
}
