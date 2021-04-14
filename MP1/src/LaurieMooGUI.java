import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.JOptionPane;


public class LaurieMooGUI extends JFrame {

    private JLabel titleLabel;
    private JLabel guessLabel;
    private JTextField guessTextField;
    private JLabel guessNumberLabel;
    private JPanel laurieMOOPanel;
    private JLabel guessNumberLabelModify;
    private JTextArea mooField;
    private JLabel textFieldTitleLabel;
    private JLabel mooLabel;
    private JLabel guessesLabel;
    private JTextArea mooArea;
    private JTextArea previousArea;
    RandomMooValue myMoo = new RandomMooValue();


    private int bigMoos;
    private int littleMoos;
    private int guesses = 10;

    public LaurieMooGUI() {
        //For debugging purposes only
        //JOptionPane.showMessageDialog(null, "" + myMoo.getSecretValue());
        guessNumberLabelModify.setText(String.valueOf(guesses));

        guessTextField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //game over condition
                if(guesses == 0) {
                    JOptionPane.showMessageDialog(null, "Boo Hoo! No Laurie Moo!\nThe secret value was: " + myMoo.getSecretValue());
                    System.exit(0);
                }

                guessNumberLabelModify.setText(String.valueOf(guesses));

                //Check for non numeric guesses
                try{
                    Integer.parseInt(guessTextField.getText());
                } catch (Exception NumberFormatException) {
                    JOptionPane.showMessageDialog(null,"Error: Numeric guesses only");
                    return;
                }

                mooArea.setText("The Cows Go: \n");

                //for a valid guess
                if(myMoo.setGuess(guessTextField.getText())) {
                    guesses--;
                    bigMoos = myMoo.getBigMooCount(guessTextField.getText());
                    littleMoos = myMoo.getLittleMooCount(guessTextField.getText());

                    //displays little and big moos for the number returned by their respective functions
                    for(int i = 0; i < bigMoos; i++) {
                        mooArea.append("\n MOO!!! \n");
                    }
                    for(int i = 0; i < littleMoos; i++) {
                       mooArea.append("\n moo... \n");
                    }
                    //history of guesses
                    previousArea.append(myMoo.getGuess() + "\n");

                    myMoo.clearMoos(myMoo.getGuess());
                    myMoo.clearShadowArray();

                    //for a completely wrong guess
                    if(bigMoos == 0 && littleMoos == 0) { JOptionPane.showMessageDialog(null, "All you hear are cowbells..."); }

                    //win condition
                    if(myMoo.isCorrectGuess(myMoo.getGuess())) {
                        JOptionPane.showMessageDialog(null, "Ding Ding! You won Laurie MOO!!");
                        System.exit(0);
                    }
                }
                else {
                    //for guesses too short or too long
                    JOptionPane.showMessageDialog(null, "Error: guess range 0000-9999");
                }

            }
        });
    }

    public static void main(String[] args) {
        JFrame myFrame = new JFrame();
        myFrame.setContentPane(new LaurieMooGUI().laurieMOOPanel);

        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.setPreferredSize(new Dimension(1000, 1000));
        myFrame.setTitle("LaurieMoo!");

        myFrame.pack();
        myFrame.setVisible(true);
    }
}

class RandomMooValue {
    private String secretValue;
    private int bigMooCount;
    private int littleMooCount;
    private String guess;
    private int bound = 4;
    Random rand;
    private char[] littleMooShadowArr;

    /**
     * Creates a new RandomMooValue object containing a secret value to be guessed
     */
    public RandomMooValue() {
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
    public boolean isCorrectGuess(String n) {
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
        if (n.length() == bound) {
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
            if (n.length() == bound) {
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
        if(n.length() == bound) {
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

    public String getGuess() {
        return guess;
    }
}

