package com.example.memory.model;

import java.util.Objects;

public class Card {
    private CardsTypes type;
    private final char fliepedSide = ' ';
    private boolean fliped = false;
    private boolean paired = false;

    public Card(CardsTypes type) {
        this.type = type;
    }

    public CardsTypes getType() {
        return type;
    }

    public char getFliepedSide() {
        return fliepedSide;
    }

    public void setPaired() {
        this.paired = true;
    }
    public boolean isPaired() {
        return paired;
    }

    public boolean isFliped() {
        return fliped;
    }

    public void flipCard() {
        this.fliped = !this.fliped;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return type == card.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(type);
    }

    @Override
    public String toString() {
        if (fliped) {
            return String.valueOf(type.getCharacter());
        } else {
            return String.valueOf(fliepedSide);
        }
    }
}
