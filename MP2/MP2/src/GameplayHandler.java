import javax.swing.*;
import javax.swing.JOptionPane;
import java.awt.*;
import java.util.Random;

/**
 * Gameplay handler class will perform the gameplay functions to erase cookie cutter code in the GUI
 * It communicated between both the Player classes and the Gui Class
 */
public class GameplayHandler {
    private Random rand = new Random();
    private boolean player1Turn = true;
    private int dialogResult = -1;
    private boolean shouldSwitch = false;
    private JLabel die1Label;
    private JLabel die2Label;
    private Player p1;
    private Player p2;
    private JLabel turnScoreLabel;
    private JLabel p1ScoreLabel;
    private JLabel p2ScoreLabel;
    private int[] dice = new int[3];


    /**
     * Constructor for handling gameplay
     * There is no default constructor, because gameplay cannot happen without the players and labels
     * @param p1 - player 1
     * @param p2 - player 2
     * @param die1Label - the label of the first die
     * @param die2Label - the label of the second die
     * @param turnScoreLabel - the label for the turn score to be displayed
     * @param p1ScoreLabel - p1's game score
     * @param p2ScoreLabel - p2's game score
     */
    public GameplayHandler(
            Player p1,
            Player p2,
            JLabel die1Label,
            JLabel die2Label,
            JLabel turnScoreLabel,
            JLabel p1ScoreLabel,
            JLabel p2ScoreLabel
    ) {
        this.die1Label = die1Label;
        this.die2Label = die2Label;
        this.p1 = p1;
        this.p2 = p2;
        this.turnScoreLabel = turnScoreLabel;
        this.p1ScoreLabel = p1ScoreLabel;
        this.p2ScoreLabel = p2ScoreLabel;
    }

    /**
     * getDice method sets the dice rolled in the gameplay class to an array in the timer handler method
     * for the purposes of running the animation
     * @param diceSet - the set of dice ints that are rolled in the timer
     * @return an integer array for containing two integer values 1-6
     */
    public int[] getDice(int[] diceSet) {
        for(int i = 1; i<dice.length; i++) {
            diceSet[i] = dice[i];
        }
        return diceSet;
    }

    /**
     * overriden getDice is used to get the dice at a certain index, (mainly for debug)
     * @param index - which dice value you'd like to access
     * @return - the int of the die being accessed
     */
    public int getDice(int index) {
        return dice[index];
    }

    /**
     * rollDice function rolls the numbers of the dice
     */
    public void rollDice() {
        for(int i = 1; i < 3; i++) {
            dice[i] = rand.nextInt(6)+1;
        }
    }

    /**
     * switchTurn function performs the necessary actions needed when a player's turn ends. This includes:
     * adding turn score to game score
     * setting the score label text
     * indicating which players turn it is
     * flipping a boolean so the code knows which player's fields to use
     * @param player1Label - the name label for player 1
     * @param player2Label - the name label for player 2
     */
    public void switchTurn(JLabel player1Label, JLabel player2Label) {
        if (player1Turn) {
            //boolean switch
            player1Turn = false;
            //update score if turn score is not zero
            p1.addTurnScoreToGameScore();

            //prep for next turn
            p1ScoreLabel.setText("Score: " + p1.getGameScore());
            p1.setTurnScore(0);
            player1Label.setForeground(Color.black);
            player2Label.setForeground(Color.red);
        } else {
            //switch turn
            player1Turn = true;

            //update gamescore
            p2.addTurnScoreToGameScore();

            //set labels and prep for next turn
            p2ScoreLabel.setText("Score: " + p2.getGameScore());
            p2.setTurnScore(0);
            player1Label.setForeground(Color.red);
            player2Label.setForeground(Color.black);
        }
    }

    /**
     * prompts and sets the names for p1 and p2 at the beginning of the game
     * @param name1Label - label for p1
     * @param name2Label - label for p2
     */
    public void setNames(JLabel name1Label, JLabel name2Label) {
        p1.setName(JOptionPane.showInputDialog("Player 1 Name: "));
        p2.setName(JOptionPane.showInputDialog("Player 2 Name: "));
        name1Label.setText(p1.getName());
        name2Label.setText(p2.getName());
        name1Label.setForeground(Color.red);
    }

    /**
     * performs one turn of a player. Called whenever a button is pressed
     * @param player1Label - name label for p1
     * @param player2Label - name label for p2
     */
    public void takeTurn(JLabel player1Label, JLabel player2Label) {
        shouldSwitch = true;

        //for player 1's turn
        if (player1Turn) {
            //"roll" dice an update the turn score
            p1.rollDice(die1Label, die2Label, this);
            p1.updateTurnScore();

            //checks if game or turn score is lost and sets accordingly
            if (p1.isGameScoreLost()) {
                p1.setGameScore(0);
                p1.setTurnScore(0);
                JOptionPane.showMessageDialog(null, "Oh no! Your entire score is lost!");
                switchTurn(player1Label, player2Label);
            } else if (p1.isTurnScoreLost()) {
                p1.setTurnScore(0);
                JOptionPane.showMessageDialog(null, "Oh no! You lost your turn!");
                switchTurn(player1Label, player2Label);
            }
            //if neither score lost add the scores
            //else if (p1.addTurnScoreToGameScore()) {}
            turnScoreLabel.setText(p1.getName() + " has rolled " + p1.getTurnScore() + " points this round!");

        }
        //for player 2's turn
        else {
            //repeat steps as above
            p2.rollDice(die1Label, die2Label, this);
            p2.updateTurnScore();

            if (p2.isGameScoreLost()) {
                p2.setGameScore(0);
                p2.setTurnScore(0);
                JOptionPane.showMessageDialog(null, "Oh no! Your entire score is lost!");
                switchTurn(player1Label, player2Label);
            } else if (p2.isTurnScoreLost()) {
                p2.setTurnScore(0);
                JOptionPane.showMessageDialog(null, "Oh no! You lost your turn!");
                switchTurn(player1Label, player2Label);
            }// else if (p2.addTurnScoreToGameScore()) {}

            turnScoreLabel.setText(p2.getName() + " has rolled " + p2.getTurnScore() + " points this round!");
        }
    }

    /**
     * setRollResult label updates the rollResult label to display the value of the rolled dice
     * @param rollResultLabel - the label for the sum of the dice
     */
    public void setRollResult(JLabel rollResultLabel) {
        if(player1Turn) {
            rollResultLabel.setText(p1.getName() + " rolled: " + p1.rollDice(die1Label, die2Label, this));
        }
        else {
            rollResultLabel.setText(p2.getName() + " rolled: " + p2.rollDice(die1Label, die2Label, this));
        }
    }

    /**
     * executes what happens if the game has been won, or if it is still to be continued
     */
    public void gameOver() {
        //check win conditions and prompt if won
        if(p1.hasWon()) {
            dialogResult = JOptionPane.showConfirmDialog(null, p1.getName() + " Wins! \n Would you like to play again?");
        }
        else if(p2.hasWon()) {
            dialogResult = JOptionPane.showConfirmDialog(null, p2.getName() + " Wins! \n Would you like to play again?");
        }

        //result of win conditions
        if(dialogResult == JOptionPane.YES_OPTION) {
            p1.reset();
            p2.reset();
            p1ScoreLabel.setText("Score:");
            p2ScoreLabel.setText("Score:");
            turnScoreLabel.setText("");
        }
        else if(dialogResult == JOptionPane.NO_OPTION) {
            System.exit(0);
        }
    }
}
