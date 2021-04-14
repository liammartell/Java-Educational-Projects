import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

/**
 * Player class contains fields and methods for a player playing Roll em Pigs..
 * This includes scores, names, getters and setters as well as other methods.
 */
public class Player {
    private String name = "Player";
    private int gameScore = 0;
    private int turnScore = 0;

    private int die1Value = 0;
    private int die2Value = 0;


    /**
     * player constructor sets default name to player and both scores to 0
     */
    public Player() {
        this("Player", 0, 0);

    }

    /**
     * sets the name, gameScore, and turnScore to passed in values
     * primarily for debug
     * @param name - name of the player
     * @param gameScore - total score for the player
     * @param turnScore - score rolled on the current turn
     */
    public Player(String name, int gameScore, int turnScore) {
        this.name = name;
        this.gameScore = gameScore;
        this.turnScore = turnScore;
    }

    /**
     * rollDice performs the rolling of the individual die being displayed on each of the two DieLabel objects;
     * the returned values are to be used to update the game and turn scores, as well as the status vairables for the
     * boolean returning methods below
     * NOTE: an extra parameter was added in order to get the dice outside of the timer thread.
     * @param die1Label the label for the first die icon to be set
     * @param die2Label the label for the second die icon to be set
     * @param game the gameplayHandler functionality
     * @return the sum of the dice roll
     */
    public int rollDice (JLabel die1Label, JLabel die2Label, GameplayHandler game) {
        die1Value = game.getDice(1);
        die2Value = game.getDice(2);

        return die1Value + die2Value;
    }


    /**
     * checks if the player's turn is lost
     * @return true if the result of the roll causes the player to lose their turn; false otherwise
     */
    public boolean isTurnScoreLost() {
        return die1Value == 1 || die2Value == 1;
    }

    /**
     * checks if player's points are lost
     * @return true only if the result of the roll causes all the player's points to be lost; false otherwise
     */
    public boolean isGameScoreLost() {
        return die1Value == 1 && die2Value == 1;
    }

    /**
     * checks for the win condition for the current player
     * @return true only if the player has accumulated over 100 total points; false otherwise
     */
    public boolean hasWon() {
        return gameScore >= 100;
    }

    /**
     * ONLY called after a player finishes their turn. Updates the gamescore
     * @return true if the addition of the gamescore has changed its value; false otherwise
     */
    public boolean addTurnScoreToGameScore() {
        System.out.println("current gamescore: " + gameScore);

        if (gameScore + turnScore != gameScore) {
            gameScore += turnScore;
            System.out.println("current gamescore: " + gameScore);

            return true;
        }
        return false;
    }

    /**
     * gets the turnScore for the player
     * @return the score of the current turn
     */
    public int getTurnScore() {
        return turnScore;
    }

    /**
     * gets the total score for the player
     * @return the player's score for the entire game
     */
    public int getGameScore() {
        return gameScore;
    }

    /**
     * adds the sum of the dice to the turn score
     * ONLY TO BE CALLED IF NEITHER DICE IS A ONE
     */
    public void updateTurnScore() {
        this.turnScore += die1Value + die2Value;
    }

    /**
     * sets the turnScore to the passed in argument
     * @param turnScore - the current player's score for the turn
     */
    public void setTurnScore(int turnScore) {
        this.turnScore = turnScore;
    }

    /**
     * sets the gameScore to the passed in argument
     * @param gameScore - the current player's score for the entire game
     */
    public void setGameScore(int gameScore) {
        this.gameScore = gameScore;
        //debug line
       // System.out.println("current gamescore: " + gameScore);
    }

    /**
     * accesses the name of the player
     * @return - the name of the player
     */
    public String getName() {
        return name;
    }

    /**
     * sets the name of the player
     * @param name - the name you want this player to have
     */
    public void setName(String name) {
        this.name = name;
    }


    /**
     * resets the game and turn scores. For the purposes of restarting a game
     */
    void reset() {
        gameScore = 0;
        turnScore = 0;
    }
}