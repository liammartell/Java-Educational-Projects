import javax.swing.*;
import java.util.ArrayList;

/**
 * Runs through the contents of the simulation
 */
public class Gameplay {
    private Branch[] branches = new Branch[8];
    private boolean enable;
    private ArrayList<Branch> previousBranchList;
    private Branch currentBranch;

    /**
     * Passes in text files and initializes remaining fields
     */
    public Gameplay() {
        branches[0] = new Branch (
                "src/Main/MainScenario.txt",
                "src/Main/MainOption1.txt",
                "src/Main/MainOption2.txt"
        );
        branches[1] = new Branch(
                "src/Branch 1/GoodAnswerB1.txt",
                "src/Branch 1/BadAnswerB1.txt",
                "src/Branch 1/GoodOptionB1.txt",
                "src/Branch 1/BadOptionB1.txt",
                "src/Branch 1/B1Senario.txt"
        );
        branches[1].setBranchImages(
                "src/Photos/Branch 1pic/scenarioB1.jpeg",
                "src/Photos/Branch 1pic/goodAnswerB1.jpeg",
                "src/Photos/Branch 1pic/badAnswerB1.jpeg"
        );

        branches[2] = new Branch(
                "src/Branch 1/Branch 2-good/GoodAnswerB2.txt",
                "src/Branch 1/Branch 2-good/BadAnswerB2.txt",
                "src/Branch 1/Branch 2-good/GoodOptionB2.txt",
                "src/Branch 1/Branch 2-good/BadOptionB2.txt",
                "src/Branch 1/Branch 2-good/B2Senario.txt"
        );
        branches[2].setBranchImages(
                "src/Photos/Branch 2/scenarioB2.jpg",
                "src/Photos/Branch 2/goodAnswerB2.jpeg",
                "src/Photos/Branch 2/badAnswerB2.jpeg"
        );

        branches[3] = new Branch(
                "src/Branch 1/Branch 3-bad/GoodAnswerB3.txt",
                "src/Branch 1/Branch 3-bad/BadAnswerB3.txt",
                "src/Branch 1/Branch 3-bad/GoodOptionB3.txt",
                "src/Branch 1/Branch 3-bad/BadOptionB3.txt",
                "src/Branch 1/Branch 3-bad/B3Senario.txt"
        );
        branches[3].setBranchImages(
                "src/Photos/Branch 3/scenarioB3.jpg",
                "src/Photos/Branch 3/goodAnswerB3.jpg",
                "src/Photos/Branch 3/badAnswerB3.jpg"
        );

        branches[4] = new Branch(
                "src/Branch 1/Branch 2-good/Branch 4-good/GoodAnswerB4.txt",
                "src/Branch 1/Branch 2-good/Branch 4-good/BadAnswerB4.txt",
                "src/Branch 1/Branch 2-good/Branch 4-good/GoodOptionB4.txt",
                "src/Branch 1/Branch 2-good/Branch 4-good/BadOptionB4.txt",
                "src/Branch 1/Branch 2-good/Branch 4-good/B4Senario.txt"
        );
        branches[4].setBranchImages(
                "src/Photos/Branch 4/scenarioB4.jpg",
                "src/Photos/Branch 4/goodAnswerB4.jpg",
                "src/Photos/Branch 4/badAnswerB4.jpg"
        );

        branches[5] = new Branch(
                "src/Branch 1/Branch 2-good/Branch 5-bad/GoodAnswerB5.txt",
                "src/Branch 1/Branch 2-good/Branch 5-bad/BadAnswerB5.txt",
                "src/Branch 1/Branch 2-good/Branch 5-bad/GoodOptionB5.txt",
                "src/Branch 1/Branch 2-good/Branch 5-bad/BadOptionB5.txt",
                "src/Branch 1/Branch 2-good/Branch 5-bad/B5Senario.txt"
        );
        branches[5].setBranchImages(
                "src/Photos/Branch 5/scenarioB5.jpg",
                "src/Photos/Branch 5/goodAnswerB5.jpg",
                "src/Photos/Branch 5/badAnswerB5.jpg"
        );

        branches[6] = new Branch(
                "src/Branch 1/Branch 3-bad/Branch6-Good/GoodAnswerB6.txt",
                "src/Branch 1/Branch 3-bad/Branch6-Good/BadAnswerB6.txt",
                "src/Branch 1/Branch 3-bad/Branch6-Good/GoodOptionB6.txt",
                "src/Branch 1/Branch 3-bad/Branch6-Good/BadOptionB6.txt",
                "src/Branch 1/Branch 3-bad/Branch6-Good/B6Senario.txt"
        );
        branches[6].setBranchImages(
                "src/Photos/Branch 6/scenarioB6.jpeg",
                "src/Photos/Branch 6/goodAnswerB6.jpg",
                "src/Photos/Branch 6/badAnswerB6.jpg"
        );
        branches[7] = new Branch("src/Branch 1/EndScreen.txt");

        enable = true;
        previousBranchList = new ArrayList<Branch>();
    }

    /**
     * Sets up Main title screen
     * @param ScenarioText - Label for the current branch's scenario
     * @param Choice1 - Label for the current branch's choice 1
     * @param Choice2 - Label for the current branch's choice 2
     * @param b1 - JButton for choosing choice 1
     * @param b2 - JButton for choosing choice 2
     */
    public void setUpMainScreen(JLabel ScenarioText, JLabel Choice1, JLabel Choice2, JButton b1, JButton b2) {
        currentBranch = branches[0];
        ScenarioText.setText("Anxiety Simulation");
        Choice1.setText("The following simulation deals with anxiety and anxious situations.");
        Choice2.setText("Select either button to begin");
        b1.setText("Go!");
        b2.setText("Go!");
    }

    /**
     * Runs through game play of what text will display based on what choice the user makes
     * @param ScenarioText - Label for the current branch's scenario
     * @param Choice1 - Label for the current branch's choice 1
     * @param Choice2 - Label for the current branch's choice 2
     * @param image - Label for the image of the current branch
     * @param b1 - JButton for choosing choice 1
     * @param b2 - JButton for choosing choice 2
     * @param undo - JButton to reset or go back a branch
     */
    public void button1Handling(JLabel ScenarioText, JLabel Choice1, JLabel Choice2, JLabel image, JButton b1, JButton b2, JButton undo) {
        //display education point for all branch transitions except the starting screen
        if(!currentBranch.equals(branches[0])) {
            currentBranch.setInput(true);
            JOptionPane.showMessageDialog(
                    null,
                    currentBranch.displayCorrectMessage(),
                    "Good Job!",
                    JOptionPane.INFORMATION_MESSAGE,
                    currentBranch.getGoodChoiceImage()
            );
        }
        if(currentBranch.equals(branches[0])) {
            previousBranchList.add(branches[0]);
            //0->1
            currentBranch = branches[1];
        } else if(currentBranch.equals(branches[1])) {
            previousBranchList.add(branches[1]);
            //1->2
            currentBranch = branches[2];
        } else if(currentBranch.equals(branches[2])) {
            previousBranchList.add(branches[2]);
            //2->4
            currentBranch = branches[4];
        } else if(currentBranch.equals(branches[3])) {
            previousBranchList.add(branches[3]);
            //3->6
            currentBranch = branches[6];
        } else if (currentBranch.equals(branches[4]) || currentBranch.equals(branches[6]) || currentBranch.equals(branches[5])) {
            //4, 6, 5->end screen
            setEndScreen(ScenarioText, Choice1, Choice2, undo);
            hideElements(Choice1, Choice2, b1, b2);
        }
        //only false for end screen
        if (enable) {
            setNewBranch(ScenarioText, Choice1, Choice2, image);
        }
        if(currentBranch.equals(branches[0]) || currentBranch.equals(branches[1])) {
            undo.setVisible(false);
        }
        else {undo.setVisible(true);}
    }

    /**
     * Runs through game play of what text will display based on what choice the user makes
     * @param ScenarioText - Label for the branches scenario text
     * @param Choice1 - Label for the branches choice 1 text
     * @param Choice2 - Label for the branches choice 2 text
     * @param image - Label for the branches image
     * @param b1 - JButton for choosing choice 1
     * @param b2 - JButton for choosing choice 2
     * @param undo - JButton to restart or go back one branch
     */
    public void button2Handling(JLabel ScenarioText, JLabel Choice1, JLabel Choice2, JLabel image, JButton b1, JButton b2, JButton undo) {
        //display education point for all branch transitions except the starting screen
        if(!currentBranch.equals(branches[0])) {
            currentBranch.setInput(false);
            JOptionPane.showMessageDialog(
                    null,
                    currentBranch.displayCorrectMessage(),
                    "Oh No!",
                    JOptionPane.INFORMATION_MESSAGE,
                    currentBranch.getBadChoiceImage()
            );
        }
        if(currentBranch.equals(branches[0])) {
            previousBranchList.add(branches[0]);
            //0->1
            currentBranch = branches[1];
        }
        else if(currentBranch.equals(branches[1])) {
            previousBranchList.add(branches[1]);
            //1->3
            currentBranch = branches[3];

        }
        else if(currentBranch.equals(branches[2])) {
            previousBranchList.add(branches[2]);
            //2->5
            currentBranch = branches[5];
        }else if(currentBranch.equals(branches[5]) || currentBranch.equals(branches[4]) || currentBranch.equals(branches[6]) || currentBranch.equals(branches[3])) {
            //6, 5, 4, 3 -> end screen
            setEndScreen(ScenarioText, Choice1, Choice2, undo);
            hideElements(Choice1, Choice2, b1, b2);
        }
        //only false for end screen
        if(enable) {
            setNewBranch(ScenarioText, Choice1, Choice2, image);
        }

        if(currentBranch.equals(branches[0]) || currentBranch.equals(branches[1])) {
            undo.setVisible(false);
        }
        else {undo.setVisible(true);}
    }

    /**
     * reverts the panel to the previous branch
     * @param Scenario - Label for the current branch's scenario
     * @param Choice1 - Label for the current branch's choice 1
     * @param Choice2 - Label for the current branch's choice 2
     * @param image - Label for the image of the current branch
     * @param b1 - JButton for choosing choice 1
     * @param b2 - JButton for choosing choice 2
     * @param undo - JButton to reset or go back a branch
     */
    public void undoHandling(JLabel Scenario, JLabel Choice1, JLabel Choice2, JLabel image, JButton b1, JButton b2, JButton undo) {
        showElements(Choice1, Choice2, b1, b2);
        undo.setText("Undo");
        if(!currentBranch.equals(branches[7])) {
            currentBranch = previousBranchList.get(previousBranchList.size() - 1);
            previousBranchList.remove(previousBranchList.get(previousBranchList.size() - 1));
            if(currentBranch.equals(branches[1])) {undo.setVisible(false);}

            }
        else {
            currentBranch = branches[1];
            previousBranchList.remove(previousBranchList.get(previousBranchList.size()-1));
            undo.setVisible(false);
        }
        setNewBranch(Scenario, Choice1, Choice2, image);
        enable = true;

        System.out.println(previousBranchList.toString());
    }

    /**
     * Displays the end screen text once the simulation is over
     * @param ScenarioText - JLabel for branches scenario text
     * @param Choice1 - JLabel for text in place of Choice 1
     * @param Choice2 - JLabel for text in place of Choice 2
     * @param undo - JButton to reset the game
     */
    public void setEndScreen(JLabel ScenarioText, JLabel Choice1, JLabel Choice2, JButton undo) {
        currentBranch = branches[7];
        ScenarioText.setText(currentBranch.getScenario());
        undo.setText("Restart");
        enable = false;
    }

    /**
     * Sets the GUI to the current branch
     * @param ScenarioText - JLabel for branches scenario text
     * @param Choice1 - JLabel for text in place of Choice 1
     * @param Choice2 - JLabel for text in place of Choice 2
     * @param image - JLabel for the branch's image
     */
    public void setNewBranch(JLabel ScenarioText, JLabel Choice1, JLabel Choice2, JLabel image) {
        ScenarioText.setText(currentBranch.getScenario());
        image.setIcon(currentBranch.getScenarioImage());
        Choice1.setText(currentBranch.getQuestion1());
        Choice2.setText(currentBranch.getQuestion2());
    }

    /**
     * Hides the buttons and choices on GUI
     * @param Choice1 - JLabel for text in place of Choice 1
     * @param Choice2 - JLabel for text in place of Choice 2
     * @param b1 - JButton for choosing choice 1
     * @param b2 - JButton for choosing choice 2
     */
    public void hideElements(JLabel Choice1, JLabel Choice2, JButton b1, JButton b2) {
        b1.setVisible(false);
        b2.setVisible(false);
        Choice1.setVisible(false);
        Choice2.setVisible(false);
    }

    /**
     * Shows the buttons and choices on GUI
     * @param Choice1 - JLabel for text in place of Choice 1
     * @param Choice2 - JLabel for text in place of Choice 2
     * @param b1 - JButton for choosing choice 1
     * @param b2 - JButton for choosing choice 2
     */
    public void showElements(JLabel Choice1, JLabel Choice2, JButton b1, JButton b2) {
        b1.setVisible(true);
        Choice1.setVisible(true);
        b2.setVisible(true);
        Choice2.setVisible(true);
    }





}
