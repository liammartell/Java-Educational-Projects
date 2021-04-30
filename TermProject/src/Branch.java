/*
NOTE: String returns have html and css tags to allow proper txt wrapping in JLabels
 */
import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Represents the object for each section of the simulations progression
 */
public class Branch {
   private String scenario;
   private String question1File;
   private String question2File;
   private String answerName1;
   private String answerName2;
   private boolean input;
   private File myFile;
   private Scanner myScanner;
   private ImageIcon[] branchImages = new ImageIcon[3];


    /**
     * Branch constructor, resets scenario to blank text
     */
    public Branch() {
        scenario = " ";
    }

    /**
     * Branch constructor that sets the scenario to be accessed
     * @param scenario
     * @param option1
     * @param option2
     */
    public Branch(String scenario, String option1, String option2) {
        this.scenario = scenario;
        this.question1File = option1;
        this.question2File = option2;
    }

    /**
     * Branch constructor for scenarios
     * @param answerName1
     * @param answerName2
     * @param question1File
     * @param question2File
     * @param scenario
     */
    public Branch (String answerName1, String answerName2, String question1File, String question2File, String scenario) {
        this.answerName1 = answerName1;
        this.answerName2 = answerName2;
        this.question1File = question1File;
        this.question2File = question2File;
        this.scenario = scenario;
    }

    /**
     * Branch constructor sets scenario to be accessed
     * @param scenario
     */
    public Branch (String scenario) {
        this.scenario = scenario;
    }

    /**
     *returns players input
     * @return input
     */
    public boolean getInput() {
        return input;
    }

    /**
     * sets input of the user's choice
     * @param input
     */
    public void setInput(boolean input) {
        this.input = input;
    }

    /**
     * displays the response to the users choice
     * @return user's message
     */
    public String displayCorrectMessage() {
        if (input) {
            myFile = new File(answerName1);
        }
        else {
            myFile = new File(answerName2);
        }
        try {
            myScanner = new Scanner(myFile, "UTF-8");
        } catch (FileNotFoundException e) {
            System.out.println("File: " + myFile.toString() + " not found.");
        }
        String out = myScanner.nextLine();
        while(myScanner.hasNextLine()) {
            out = out + "<br>" + myScanner.nextLine();
        }
        return "<html><body><p style= 'width: 400px;'>" + out + "</p></body></html>";
    }

    /**
     * gets the branches first option
     * @return question1
     */
    public String getQuestion1() {
        myFile = new File (question1File);
        try {
            myScanner = new Scanner(myFile, "UTF-8");
        } catch (FileNotFoundException e) {
            System.out.println("File: " + myFile.toString() + " not found.");
        }
        return "<html>" + myScanner.nextLine() + "</html>";
    }

    /**
     * gets the branches second option
     * @return question2
     */
    public String getQuestion2() {
        myFile = new File (question2File);
        try {
            myScanner = new Scanner(myFile, "UTF-8");
        } catch (FileNotFoundException e) {
            System.out.println("File: " + myFile.toString() + " not found.");
        }
        return "<html>" + myScanner.nextLine() + "</html>";
    }

    /**
     * gets the branches scenario
     * @return scenario
     */
    public String getScenario() {
        myFile = new File (scenario);
        try {
            myScanner = new Scanner(myFile, "UTF-8");
        } catch (FileNotFoundException e) {
            System.out.println("File: " + myFile.toString() + " not found.");
        }
        return "<html>" + myScanner.nextLine() + "</html>";
    }

    /**
     * Takes in the 3 different pictures for each branch and assigns them to the image icon array
     * @param scenarioImagePath
     * @param goodChoiceImage
     * @param badChoiceImage
     */
    public void setBranchImages(String scenarioImagePath, String goodChoiceImage, String badChoiceImage) {
        this.branchImages[0] = new ImageIcon(scenarioImagePath);
        this.branchImages[1] = new ImageIcon(goodChoiceImage);
        this.branchImages[2] = new ImageIcon(badChoiceImage);
    }

    /**
     * gets scenario image for the branch
     * @return branchImage for scenario
     */
    public ImageIcon getScenarioImage() {
        return branchImages[0];
    }

    /**
     * gets good choice image for the branch
     * @return branchImage for good choice
     */
    public ImageIcon getGoodChoiceImage() {
        return branchImages[1];
    }

    /**
     * gets bad choice image for the branch
     * @return branchImage for bad choice
     */
    public ImageIcon getBadChoiceImage() {
        return branchImages[2];
    }
}
