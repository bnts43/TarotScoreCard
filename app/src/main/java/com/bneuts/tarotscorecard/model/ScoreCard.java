package com.bneuts.tarotscorecard.model;

import java.util.Date;
import java.util.List;

/**
 * Created by Benoît Neuts on 02/02/2018.
 * ScoreCard POJO
 */

public class ScoreCard {

    private String id;
        public String getId() { return id; }
        public void setId(String id) { this.id = id; }

    private String cardId;
        public String getCardId() { return cardId; }
        public void setCardId(String cardId) { this.cardId = cardId; }

    private String name;
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

    private Date date;
        public Date getDate() { return date; }
        public void setDate(Date date) { this.date = date; }

    private List<String> players;
        public List<String> getPlayers() { return players; }
        public void setPlayers(List<String>    players) { this.players = players; }

    public ScoreCard() {

    }

    public ScoreCard(String name, Date date) {
        this.cardId = "to_be_changed";
        this.name = name;
        this.date = date;
    }

    public String toString() {
        return "{ ScoreCard name = " + name + " on day: " + date + " }";
    }
}
