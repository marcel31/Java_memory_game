package com.example.memory.model;

import java.util.*;

/**
 * Classe Table contiene las cartas en la massa i las monedas del jugador
 * Se necessita un numero integro par para inicializar esta classe
 */
public class Table {
    public ArrayList<Card> cards;
    private int coins = 0;
    private int firstIndexSelected = -1;
    private int secondIndexSelected = -1;

    public Table(int number_Slots) throws Exception {
        SetUpTable(number_Slots);
    }

    public void SetUpTable(int number_Slots) throws Exception {
        cards = new ArrayList<Card>(); // Vuidem la taula per si estava omplida
        if (number_Slots % 2 != 0) {
            throw new Exception("Num de Slots es Inpar");
        } else {
            //Crear mitad inicial
            for (int i = 0; i < (number_Slots/2); i++) {
                Card newCard;
                do{
                     newCard = new Card(CardsTypes.randomCardType());
                }while (cards.contains(newCard));
                    cards.add(newCard);

            }
            //duplicar
            for (int i = 0; i < (number_Slots/2); i++) {
                Card newCard = new Card(cards.get(i).getType());

                cards.add(newCard);

            }
            Collections.shuffle(cards); // I mezclem les cartes
        }
    }

    public void SelectCard (int index) {


        if (this.firstIndexSelected != -1 && this.firstIndexSelected != index) {
            Card selectedCard = cards.get(index);
            System.out.println(selectedCard.toString());
            // IF the card is NOT fliped and NOT pair then is enabled to be selected
            if (!selectedCard.isFliped() && !selectedCard.isPaired()){
                this.secondIndexSelected = index;
                cards.get(this.secondIndexSelected).flipCard();

            }


        }else {
            Card selectedCard = cards.get(index);

            // IF the card is NOT fliped and NOT pair then is enabled to be selected
            if (!selectedCard.isFliped() && !selectedCard.isPaired()){
                this.firstIndexSelected = index;
                cards.get(this.firstIndexSelected).flipCard();
                System.out.printf("First Index Flip:", this.firstIndexSelected);
            }
        }
        System.out.println(firstIndexSelected);
        System.out.println(secondIndexSelected);


    }

    public void TheyMatch(){
        if (firstIndexSelected != -1 && secondIndexSelected != -1) {

            Card cardA = cards.get(firstIndexSelected);
            Card cardB = cards.get(secondIndexSelected);

            if (cardA.equals(cardB)) {
                // Si son iguals llavors eliminem les dos cartas i 1+ moneda
                System.out.println("PAIR FOUND");


                cards.get(firstIndexSelected).setPaired();
                cards.get(secondIndexSelected).setPaired();

                deselectCards();


                coins += 1;


            } else {
                // Si no son iguals llavors les tornem a girar i reiniciem els selected index
                System.out.println("PAIR NOT FOUND");


                cards.get(firstIndexSelected).flipCard();
                cards.get(secondIndexSelected).flipCard();


                deselectCards();
            }
        }
    }

    public boolean didYouWin(){
        boolean winCondition = true;

        Iterator<Card> iter = cards.iterator();

        while (iter.hasNext()){
            if (!iter.next().isFliped()) {
                // If card isFliped is false "X" then no win for you
                winCondition = false;
            }
        }
        return winCondition;
    }

    public void deselectCards(){
        firstIndexSelected = -1;
        secondIndexSelected = -1;
    }
    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public int getFirstIndexSelected() {
        return firstIndexSelected;
    }

    public int getSecondIndexSelected() {
        return secondIndexSelected;
    }

}
