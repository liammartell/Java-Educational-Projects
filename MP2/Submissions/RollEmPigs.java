import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

/**
 * RollEmPigs class is the GUI class for the game.
 * NOTE: inside the GUI class is extensive code for the timer, which cannot be placed in another file.
 */
public class RollEmPigs {
    private JPanel gameplayPanel;
    private JButton rollButton;
    private JLabel turnResultLabel;
    private JLabel die1Label;
    private JLabel die2Label;
    private JLabel player1Label;
    private JLabel player2Label;
    private JLabel player1ScoreLabel;
    private JLabel player2ScoreLabel;
    private JButton stopButton;
    private JLabel diceResultLabel;

    private Random rand = new Random();
    private int dieValue = 6;
    private final int NUMBER_OF_SIDES = 6;
    private final int NUMBER_OF_DICE = 2;
    private ImageIcon[] dieImage = new ImageIcon[NUMBER_OF_SIDES + 1];
    private int[] myDiceSet = new int[NUMBER_OF_DICE + 1];
    private boolean isDieAnimated = true; //assumes user wants animated die
    private final static int DELAY = 200; //time between successive images in ms
    private final static int FRAME_COUNT_MAX = 9; //max number of frames to show
    private final static int FRAME_COUNT_MIN = 5; //min number of frames to show
    private int frameCount = 0; //current frame count
    private int frameCountLimit = 0; //total frames to show for an animation
    private Timer animationTimer; //animation actionEvent generator
    private boolean isClickable = true; //blocks mouse events during animation
    private boolean[] isDieBeingHeld = new boolean[NUMBER_OF_DICE +1]; //tracks whether die is being held
    private Player player1 = new Player();
    private Player player2 = new Player();
    private GameplayHandler myGame = new GameplayHandler(
            player1, player2, die1Label, die2Label, turnResultLabel, player1ScoreLabel, player2ScoreLabel
    );

    /**
     * constructor for GUI Class
     */
    public RollEmPigs() {
        //start of game
        player1.reset(); player2.reset();
        myGame.setNames(player1Label, player2Label);

        //Prepping dice
        for(int i = 1; i <=NUMBER_OF_SIDES; i++) {
            String filename = "/images/die" + i + ".gif";
            dieImage[i] = new ImageIcon(this.getClass().getResource(filename));
        }

        // set up our animation timer
        animationTimer = new Timer(DELAY, new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                animationTimerActionPerformed(evt);
            }
        });

        //counters are used to control the number of times the timer repeats
        animationTimer.setRepeats(false);

        /**
         * adds functionality to the roll button
         */
        rollButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                diceResultLabel.setText("Roll is...");

                //initialize frame counting vars
                frameCount = 0;
                int range = FRAME_COUNT_MAX - FRAME_COUNT_MIN +1;
                frameCountLimit = FRAME_COUNT_MIN + rand.nextInt(range);

                //"Roll" the die and start the animation if we're to do so
                dieValue = rand.nextInt(NUMBER_OF_SIDES) + 1;
                if (isDieAnimated) {animationTimer.start();}
                else {
                    die1Label.setIcon(dieImage[myDiceSet[1]]);
                    die2Label.setIcon(dieImage[myDiceSet[2]]);
                }

                myGame.rollDice();
                myGame.takeTurn(player1Label, player2Label);

            }
        });
        /**
         * adds functionality to the stop button
         */
        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                myGame.switchTurn(player1Label, player2Label);
                myGame.gameOver();

            }
        });


    }

    /**
     * code to be executed when the timer is called
     * @param evt
     */
    private void animationTimerActionPerformed(java.awt.event.ActionEvent evt) {
        isClickable = false; //prevents mouse action during animation
        frameCount++; ///increment our animation frame counter

        //if we reached our limit, display the true value and restore mouse events
        //else show some random value
        if(frameCount < frameCountLimit) {

            die1Label.setIcon(dieImage[rand.nextInt(NUMBER_OF_SIDES) + 1]);
            die2Label.setIcon(dieImage[rand.nextInt(NUMBER_OF_SIDES) + 1]);

            myGame.getDice(myDiceSet);


            animationTimer.start();
        }
        else {
            int diceTotal = 0;
            for(int i = 1; i <= NUMBER_OF_DICE; i++) {
                System.out.println(myDiceSet[i]);
                diceTotal += myDiceSet[i];
            }

            die1Label.setIcon(dieImage[myDiceSet[1]]);
            die1Label.setText(String.valueOf(myDiceSet[1]));
            die2Label.setIcon(dieImage[myDiceSet[2]]);
            die2Label.setText(String.valueOf(myDiceSet[2]));

            myGame.setRollResult(diceResultLabel);
            isClickable = true;
        }
    }

    public static void main(String[] args) {
        JFrame myFrame = new JFrame("Dice Game test"); //Makes the frame
        myFrame.setContentPane(new RollEmPigs().gameplayPanel); //attaches panel to frame

        //sets up what happens with the frame when closed
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        myFrame.setPreferredSize(new Dimension(500, 300));
        myFrame.setResizable(false);

        myFrame.pack(); //put everything in the frame
        myFrame.setVisible(true);
    }
}