package com.example.memory.model;

import java.util.Random;

public enum CardsTypes {
        SUN ('☀'),
        CLOUD('☁'),
        UNBRELA('☂'),
        SNOWMAN('☃'),
        STAR('★'),
        PHONE('☎'),
        FINGER('☚'),
        TREBOR('☘'),
        SHKUL('☠'),
        RAD('☢'),
        RAD_2('☣'),
        RED_MENACE('☭'),
        PEACE('☮'),
        JYNG_YANG('☯'),
        SMYLE('☻'),
        KING('♔'),
        QUEEN('♕'),
        TOWER('♖'),
        ALFIL('♗'),
        HORSE('♘'),
        POWN('♙'),
        KING_W('♚'),
        QUEEN_W('♛'),
        TOWER_W('♜'),
        ALFIL_W('♝'),
        HORSE_W('♞'),
        POWN_W('♟'),
        CARDS_A('♧'),
        CARDS_B('♡'),
        CARDS_C('♢'),
        CARDS_D('♤'),

        ;

        private final char c;

        CardsTypes(char c) {

                this.c = c;
        }

        public char getCharacter() {
                return c;
        }

        private static final Random RANDOM = new Random();

        public static CardsTypes randomCardType() {
                int pick = RANDOM.nextInt(CardsTypes.values().length);
                return CardsTypes.values()[pick];
        }
}
