package com.silbytech.loyali.entities;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

/************************************
 * Created by Yosef Silberhaft
 ************************************/
public class CardInUse implements Serializable {

    @SerializedName("id")
    private int id;

    @SerializedName("current")
    private int current;

    @SerializedName("card")
    private Card card;

    public CardInUse(int id, int current, Card card) {
        this.id = id;
        this.current = current;
        this.card = card;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }
}
