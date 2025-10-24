package org.example.memorycards;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.animation.PauseTransition;
import javafx.util.Duration;
import java.util.ArrayList;
import java.util.Collections;

public class HelloController {

    @FXML
    private Label statusLabel;

    @FXML
    private Button card1, card2, card3, card4, card5, card6, card7, card8, card11, card21, card51, card61;

    private Button firstCard = null;
    private Button secondCard = null;
    private boolean canClick = true; // Добавим блокировку кликов

    // Список всех букв для карточек (6 пар)
    private ArrayList<String> allLetters = new ArrayList<>();

    // Список всех кнопок-карточек
    private ArrayList<Button> allCards = new ArrayList<>();

    @FXML
    public void initialize() {
        // Заполняем список всех карточек
        setupCardList();
        setupGame();
    }

    // Собираем все карточки в один список
    private void setupCardList() {
        allCards.clear();
        allCards.add(card1);
        allCards.add(card2);
        allCards.add(card3);
        allCards.add(card4);
        allCards.add(card5);
        allCards.add(card6);
        allCards.add(card7);
        allCards.add(card8);
        allCards.add(card11);
        allCards.add(card21);
        allCards.add(card51);
        allCards.add(card61);
    }

    private void setupGame() {
        createShuffledLetters();

        assignLettersToCards();

        canClick = true;
        statusLabel.setText("Click on cards to find pairs!");
    }
    private void createShuffledLetters() {
        allLetters.clear();

        String[] letters = {"A", "B", "C", "D", "E", "F"};
        for (String letter : letters) {
            allLetters.add(letter); // Первая карта пары
            allLetters.add(letter); // Вторая карта пары
        }

        Collections.shuffle(allLetters);
    }

    private void assignLettersToCards() {
        for (int i = 0; i < allCards.size(); i++) {
            Button card = allCards.get(i);
            card.setText("?");
            card.setDisable(false);
            card.setStyle("-fx-background-color: #F9BF58;");

            card.setUserData(allLetters.get(i));
        }
    }

    @FXML
    private void handleNewGame() {
        setupGame();
        statusLabel.setText("New game started! Find pairs!");
    }

    @FXML
    private void handleCardClick(javafx.event.ActionEvent event) {
        if (!canClick) return;

        Button clickedCard = (Button) event.getSource();

        if (!clickedCard.getText().equals("?")) {
            return;
        }

        showCard(clickedCard);

        if (firstCard == null) {
            firstCard = clickedCard;
            statusLabel.setText("Find matching card!");
        } else {
            // Вторая карта
            secondCard = clickedCard;
            canClick = false;
            checkMatch();
        }
    }

    private void showCard(Button card) {
        String realLetter = (String) card.getUserData();
        card.setText(realLetter);
    }

    // Проверяем совпадение
    private void checkMatch() {
        // Используем PauseTransition
        PauseTransition pause = new PauseTransition(Duration.seconds(1));
        pause.setOnFinished(event -> {
            // Берем буквы из userData
            String firstLetter = (String) firstCard.getUserData();
            String secondLetter = (String) secondCard.getUserData();

            if (firstLetter.equals(secondLetter)) {
                statusLabel.setText("Good! Pair found!");
                firstCard.setStyle("-fx-background-color: green;");
                secondCard.setStyle("-fx-background-color: green;");
            } else {
                firstCard.setText("?");
                secondCard.setText("?");
                statusLabel.setText("Try again!");
            }

            firstCard = null;
            secondCard = null;
            canClick = true;
        });

        pause.play();
    }
}