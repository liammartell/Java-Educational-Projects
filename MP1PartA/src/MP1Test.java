/** Created by Liam Martell for Programming 2 @ ONU
    Hi Grader! The RandomMooValue Class is created in this file, below the MP1Test Class
 */

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.JOptionPane;

public class MP1Test {
    private JLabel secretValueLabel;
    private JLabel guessLabel;
    private JLabel bigMooLabel;
    private JLabel littleMooLabel;
    private JLabel isCorrectLabel;
    private JTextField secretValueTextField;
    private JButton randomizeButton;
    private JTextField guessTextField;
    private JLabel numberOfBigMoosLabel;
    private JLabel numberOfLittleMoosLabel;
    private JLabel correctGuessAnswerLabel;
    private JPanel mp1TestPanel;
    RandomMooValue myMooValue = new RandomMooValue();


    public MP1Test() {
        //starts the game with a random value
        secretValueTextField.setText(myMooValue.getSecretValue());
        /**
         * On randomize button hit, generates a random Secret value
         */
        randomizeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                myMooValue = new RandomMooValue();
                secretValueTextField.setText(String.valueOf(myMooValue.getSecretValue()));
            }
        });
        /**
         * on a new secret value entered, checks if valid and sets the string as a new secret value
         */
        secretValueTextField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //creates a new instance of RandomMooValue every time a new secretValue is entered
                myMooValue = new RandomMooValue();
                //check for valid strings
                if(myMooValue.setSecretValue(secretValueTextField.getText().trim())) {
                    myMooValue.setSecretValue(secretValueTextField.getText().trim());
                }
                else {
                    JOptionPane.showMessageDialog(null, "Error: secretValue must be 0000-9999");
                }
            }
        });
        /**
         * on a guess being entered, executes the code needed to calculate the number of moos and if the player won.
         */
        guessTextField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //checks for validity
                if (myMooValue.setGuess(guessTextField.getText().trim())) {
                    //sets number of moos
                    numberOfBigMoosLabel.setText(String.valueOf(myMooValue.getBigMooCount(guessTextField.getText())));
                    numberOfLittleMoosLabel.setText(String.valueOf(myMooValue.getLittleMooCount(guessTextField.getText())));

                    if (myMooValue.isCorrectGuess(Integer.parseInt(guessTextField.getText()))) {
                        correctGuessAnswerLabel.setText("yes");
                    }
                    else {
                        correctGuessAnswerLabel.setText("no");
                    }

                    myMooValue.clearMoos(guessTextField.getText());
                    myMooValue.clearShadowArray();
                }
                else {
                    JOptionPane.showMessageDialog(null, "Error: guess must be between 0000-9999");
                }
            }
        });
    }

    public static void main(String[] args) {

        JFrame myFrame = new JFrame("MP1 Test");
        myFrame.setContentPane(new MP1Test().mp1TestPanel);

        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        myFrame.pack();
        myFrame.setVisible(true);
    }
}


class RandomMooValue {
    private String secretValue;
    private int bigMooCount;
    private int littleMooCount;
    private String guess;
    private int upperBound;
    private int lowerBound;
    Random rand;
    private char[] littleMooShadowArr;

    /**
     * Creates a new RandomMooValue object containing a secret value to be guessed
     */
    public RandomMooValue() {
        upperBound = 4;
        lowerBound = 0;
        bigMooCount = 0;
        littleMooCount = 0;
        rand = new Random();
        rand.setSeed(System.currentTimeMillis());
        setSecretValue();
        littleMooShadowArr = new char[] {'A', 'B', 'C', 'D'};

    }

    /**
     * The number of digits in the user's guess that exactly (same position) matches the digits in the secret value
     * @param guess - the number that the user guessed
     * @return - a number 0-4 representing how many digits the user guessed correctly by position
     */
    public int getBigMooCount(String guess) {

        for(int i = 0; i < 4; i++) {
            if(secretValue.charAt(i) == guess.charAt(i)) {
                bigMooCount++;
            }
        }


        return bigMooCount;
    }

    /**
     * The number of digits in the user's guess that match digits in the secret value, but are not the same position
     * @param guess - the number that the user guessed
     * @return - a number 0-4 representing how many of the guessed digits match, but are different positions.
     * Note that a guessed number cannot have any one digit generate both a MOO and a moo as a result
     */
    public int getLittleMooCount(String guess) {

        for(int i = 0; i < 4; i++) {
            if(secretValue.charAt(i) != guess.charAt(i)) {
                for(int j = 0; j < 4; j++) {
                    if(secretValue.charAt(i) == guess.charAt(j) && guess.charAt(j) != littleMooShadowArr[j] && i!=j) {
                        littleMooCount++;
                        littleMooShadowArr[j] = guess.charAt(j);
                        break;
                    }
                }
            }
        }


        return littleMooCount;
    }

    /**
     * Access the secret value that the user is trying to guess, primarily to show the user after running out of guesses
     * @return - the secret value that the user is/was attempting to guess
     */
    public String getSecretValue() {
        return secretValue;
    }

    /**
     * Determines if the user correctly guessed the secret value
     * @param n - the number that the user guessed
     * @return - true if the guess is correct, false otherwise.
     */
    public boolean isCorrectGuess(int n) {
        if (secretValue.equals(guess)) {
            return true;
        }
        return false;
    }

    /**
     * Generates a new secret value to be guessed in a game of LaurieMOO
     * @return - true in all cases
     */
    public boolean setSecretValue() {
        int x = rand.nextInt(10000);
        if(x < 1000) {
            x*=10;
        }
        secretValue = String.valueOf(x);

        return true;
    }

    /**
     * Sets the "secret" value to be guessed in a game of LaurieMOO to a known 4-digit quantity.This method is for testing
     * purposes only
     * @param n - the number (string) that will be set as the secret value, if it is within the inclusive range of 0000-9999
     * @return - true if the secret value was reset; false if the passed value was outside of the allowed range
     */
    public boolean setSecretValue(String n) {
        if (n.length() >= lowerBound || n.length() < upperBound) {
            secretValue = n;
            return true;
        }
        return false;
    }

    /**
     * Sets the guess that the player enters into LaurieMOO
     * @param n - A string of the numeric guess the player enters
     * @return - true if the string is an accurate size. False otherwise
     */
    public boolean setGuess(String n) {
        try {
            Integer.parseInt(n);
            if (n.length() >= lowerBound || n.length() < upperBound) {
                guess = n;
                return true;
            }
        } catch (Exception NumberFormatException) {JOptionPane.showMessageDialog(null, "Error: numeric guesses only");}
        return false;
    }

    /**
     * Clears existing uppercase and lowercase Moo's when a new guess is entered
     * @param n - A string of the numeric guess the player enters
     * @return - true if the new guess is an appropriate size
     */
    public boolean clearMoos(String n) {
        if(n.length() >= lowerBound || n.length() < upperBound) {
            bigMooCount = 0; littleMooCount = 0;
            return true;
        }
        return false;
    }

    /**
     * resets the array containing previous successful moo.'s used for little moo count
     */
    public void clearShadowArray() {
        littleMooShadowArr = new char[] {'A', 'B', 'C', 'D'};
    }
}


