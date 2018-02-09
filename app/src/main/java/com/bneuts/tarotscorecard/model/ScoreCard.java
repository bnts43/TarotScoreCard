package com.bneuts.tarotscorecard.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Benoît Neuts on 02/02/2018.
 * ScoreCard POJO
 */

public class ScoreCard {

    private String cardId;
        public String getCardId() { return cardId; }
        public void setCardId(String cardId) { this.cardId = cardId; }

    private String name;
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

    private Date date;
        public Date getDate() { return date; }
        public void setDate(Date date) { this.date = date; }

    private List<User> players = new ArrayList<>();
        public List<User> getPlayers() { return players; }
        public void setPlayers(List<User> players) { this.players = players; }

    public ScoreCard() {

    }

    public ScoreCard(String name, Date date) {
        this.cardId = "to_be_changed";
        this.name = name;
        this.date = date;
    }

    public ScoreCard(String name, Date date, List<User> players) {
        this.cardId = "to_be_changed";
        this.name = name;
        this.date = date;
        this.players = players;
    }

    public String toString() {
        return "{ ScoreCard name = " + name + " on day: " + date + " }";
    }
}
