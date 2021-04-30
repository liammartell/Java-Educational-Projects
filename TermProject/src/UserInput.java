import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Gets the choice of the user
 */
public class UserInput {
    private JPanel ScenarioScreen;
    private JButton Choice1Button;
    private JButton Choice2Button;
    private JLabel Choice2Text;
    private JLabel Choice1Text;
    private JLabel ScenarioText;
    private JLabel imageLabel;
    private JButton undoButton;
    private Gameplay simulation;

    /**
     * Constructor for UserInput()
     */
    public UserInput() {
        //initializing branches with txt files
        simulation = new Gameplay();

        //to allow text wrapping
        ScenarioText.setMaximumSize(new Dimension(1000,0));
        ScenarioText.setPreferredSize(new Dimension(1000,0));
        Choice1Text.setMaximumSize(new Dimension(500, 0));
        Choice1Text.setPreferredSize(new Dimension(500, 0));
        Choice2Text.setMaximumSize(new Dimension(500, 0));
        Choice2Text.setPreferredSize(new Dimension(500, 0));
        undoButton.setVisible(false);

        simulation.setUpMainScreen(ScenarioText, Choice1Text, Choice2Text, Choice1Button, Choice2Button);

        /**
         * Passes in text when button 1 is clicked
         */
        Choice1Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                simulation.button1Handling(
                        ScenarioText,
                        Choice1Text,
                        Choice2Text,
                        imageLabel,
                        Choice1Button,
                        Choice2Button,
                        undoButton
                );
            }
        });

        /**
         * Passes in text when button 2 is clicked
         */
        Choice2Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                simulation.button2Handling(
                        ScenarioText,
                        Choice1Text,
                        Choice2Text,
                        imageLabel,
                        Choice1Button,
                        Choice2Button,
                        undoButton
                );

            }
        });
        /**
         * Passes in text when undo button is clicked
         */
        undoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                simulation.undoHandling(
                        ScenarioText,
                        Choice1Text,
                        Choice2Text,
                        imageLabel,
                        Choice1Button,
                        Choice2Button,
                        undoButton
                );

            }
        });
    }

    /**
     * Runs DisplayFrame()
     * Sets size for JOptionPanes
     * @param args
     */
    public static void main(String[] args) {
        DisplayFrame();
        UIManager.put("OptionPane.minimumSize", new Dimension(800,640));
        UIManager.put("OptionPane.messageFont", new FontUIResource(new Font("Arial", Font.BOLD, 18)));
    }


    /**
     * Creates the Frame for the simulation to run on
     */
    static void DisplayFrame(){
        JFrame myFrame = new JFrame("Mental Health"); //Makes the frame
        myFrame.setContentPane(new UserInput().ScenarioScreen); //attaches panel to frame

        //sets up what happens with the frame when closed
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //we should find a way to allow it to start in fullscreen
        myFrame.setPreferredSize(new Dimension(1920, 1080));
        myFrame.setResizable(true);

        myFrame.pack(); //put everything in the frame
        myFrame.setVisible(true);
    }
}

