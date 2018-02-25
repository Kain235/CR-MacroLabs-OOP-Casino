package io.zipcoder.casino;

import java.util.*;

public class War extends Game implements GameInterface, CardGameInterface {

    private Dealer dealer = new Dealer();
    private Person player = new Person("Joe");
    private ArrayList<Card> playerPlayedCards = new ArrayList<Card>();
    private ArrayList<Card> dealerPlayedCards = new ArrayList<Card>();
    Scanner input = new Scanner(System.in);

    public War(Person player) {
        // this.player = player;
        // un-note that ^ when the main menu is done and everything should work properly
    }

    public void start() {
        System.out.println("Welcome to WAR! Enter anything into the console to play a card");
        System.out.println("Enter 'exit' at any time to end the game");
        Deck dealerDeck = new Deck();
        for (int i = 0; i < dealerDeck.getDeckOfCards().size(); i++) {
            dealer.getHand().receiveCards(dealerDeck.getDeckOfCards().get(i));
        }
        dealer.getHand().shuffleHand();
        dealCards();
        engine();
    }

    public void engine() {
        // String playerInput = input.nextLine();
        if (nextLineIsNotExit()) {
            while ((dealer.getHand().getHandArrayList().size() != 0) && (player.getHand().getHandArrayList().size() != 0)) {
                playerPlayedCards.add(player.getHand().drawCardfromHand());
                dealerPlayedCards.add(dealer.getHand().drawCardfromHand());
                System.out.println("You played " + playerPlayedCards + " and the dealer played " + dealerPlayedCards);
                int winner =
                        compareCards(
                                getLastCardPlayedOnTable(playerPlayedCards),
                                getLastCardPlayedOnTable(dealerPlayedCards));
                announceWinner(winner);
                if (!nextLineIsNotExit()) {
                    end();
                }
                checkIfGameIsOver();
            }
        } else {end();}
    }

    private void checkIfGameIsOver() {
        if ((player.getHand().getHandArrayList().size() == 0) || (dealer.getHand().getHandArrayList().size() == 0)) {
            end();
        }
    }

    private void announceWinner(int winnerNumber) {
        if (winnerNumber == 1) {
            playerWins();
        } else if (winnerNumber == 2) {
            dealerWins();
        }
    }

    private Card getLastCardPlayedOnTable(ArrayList<Card> cardsOnTable) {
        return cardsOnTable.get(cardsOnTable.size() - 1);
    }

    private boolean nextLineIsNotExit() {
        return !("exit").equals(input.nextLine());
    }

    public int compareCards(Card p1card, Card p2card) {
        if (p1card.getRank().toInt() == p2card.getRank().toInt()) {
            iDeclareWar();
        } else if (p1card.getRank().toInt() > p2card.getRank().toInt()) {
            return 1;
        } else {return 2;}
        return 0;
    }

    public void playerWins() {
        System.out.println("You won this round!");
        while (playerPlayedCards.size()!=0) {
            player.getHand().receiveCards(playerPlayedCards.remove(0));
        }
        while (dealerPlayedCards.size()!=0) {
            player.getHand().receiveCards(dealerPlayedCards.remove(0));
        }
        System.out.println("You have " + player.getHand().getHandArrayList().size() + " cards and the dealer has " + dealer.getHand().getHandArrayList().size() + " cards");
    }

    public void dealerWins() {
        System.out.println("You lost this round!");
        while (playerPlayedCards.size()!=0) {
            dealer.getHand().receiveCards(playerPlayedCards.remove(0));
        }
        while (dealerPlayedCards.size()!=0) {
            dealer.getHand().receiveCards(dealerPlayedCards.remove(0));
        }
        System.out.println("You have " + player.getHand().getHandArrayList().size() + " cards and the dealer has " + dealer.getHand().getHandArrayList().size() + " cards");
    }

    public void iDeclareWar() {
        System.out.println("I   D E C L A R E   W A R!");
        int amountOfPlayerAvailibleCards = checkNumberOfCards(player.getHand());
        int amountOfDealerAvailibleCards = checkNumberOfCards(dealer.getHand());
        iDeclareWarLogic(playerPlayedCards, player, amountOfPlayerAvailibleCards);
        iDeclareWarLogic(dealerPlayedCards, dealer, amountOfDealerAvailibleCards);
    }

    public void iDeclareWarLogic(ArrayList<Card> playedCards, Person person, int amountOfCardsAvailable) {
        if (amountOfCardsAvailable < 4) {
            int cardsAvailableInteger = amountOfCardsAvailable;
            for (int i = 0; i < cardsAvailableInteger-1; i++) {
                playedCards.add(person.getHand().getHandArrayList().remove(i));
            }
        } else {
            for (int i = 0; i < 4; i++) {
                playedCards.add(person.getHand().getHandArrayList().remove(i));
            }
        }
    }

    public void dealCards() {
        for (int i = dealer.getHand().getHandArrayList().size()-1; i >= 26; i--) {
            player.getHand()
                    .getHandArrayList()
                    .add(dealer
                            .getHand()
                            .getHandArrayList()
                            .remove(i));
        }
    }

    public void end() {
        String winner = "";
        if (player.getHand().getHandArrayList().size() > 50) {
            winner += "you!";
        } else {
            winner += "the dealer!";
        }
        System.out.println("And the winner is " + winner);
        System.out.println("If you want to play again, enter 'yes', or enter anything else to return to the casino");
        if (input.nextLine().equals("yes")) {
            start();
        }
    }

    public int checkNumberOfCards(Hand handToCheck) {
        return handToCheck.getHandArrayList().size();
    }

}