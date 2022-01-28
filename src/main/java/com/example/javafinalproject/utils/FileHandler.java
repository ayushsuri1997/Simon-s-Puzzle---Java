package com.example.javafinalproject.utils;

import com.example.javafinalproject.model.UserDetails;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.WritableImage;
import javafx.scene.robot.Robot;
import javafx.stage.Stage;
import javafx.stage.Window;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;

/** Handler for the Files.
 * @author Ayush
 * @version 1.0
 */

public class FileHandler {

    private ArrayList<UserDetails> topPlayersList;

    /**
     * Initialises the topPlayersList Array List of type UserDetails.
     */
    public FileHandler() {
        topPlayersList = new ArrayList<>();
    }

    public ArrayList<UserDetails> getTopPlayersList() {
        return topPlayersList;
    }

    /**
     * Set the top players array list.
     * @param topPlayersList ArrayList of UserDetails.
     */
    public void setTopPlayersList(ArrayList<UserDetails> topPlayersList) {
        this.topPlayersList = topPlayersList;
    }

    /**
     * Adds a player (User) in the arrayList topPlayersList.
     * @param userDetails User details of type UserDetails.
     */
    public void addPlayer(UserDetails userDetails) {
        topPlayersList.add(userDetails);
    }

    /**
     * This method is to load the ListView which shows the results of the top 10 players. We get this data by creating a file called topPlayers.txt. If the file doesn't exists, it's created otherwise we add players in the file.
     * @param topPlayers File in which we have to store the top player details.
     * @param observableList List which is used to set the listview and contains the top 10 scores
     * @return List which is used to set the listview and contains the top 10 scores
     * @throws IOException If the topPlayers.txt is not found.
     * @throws ClassNotFoundException This is thrown if the reading object can't be converted to the required type.
     */
    public ObservableList<String> loadListView(File topPlayers, ObservableList<String> observableList) throws IOException, ClassNotFoundException {
        if (topPlayers.createNewFile()) {
            System.out.println("New File Created:" +topPlayers.getName());
        } else if (topPlayers.length() != 0){
            //Read from File
            FileInputStream fis = new FileInputStream("topPlayers.txt");
            ObjectInputStream ois = new ObjectInputStream(fis);
            setTopPlayersList((ArrayList<UserDetails>) ois.readObject());
            ois.close();
            Collections.sort(getTopPlayersList());
            if (getTopPlayersList().size() > 10) {
                for (int i = 0; i < 10; i++) {
                    observableList.add((i+1)+". "+ getTopPlayersList().get(i).toString());
                }
            } else {
                int indexCount = 1;
                for (UserDetails player : getTopPlayersList()) {
                    observableList.add(indexCount+". "+player.toString());
                    indexCount++;
                }
            }
            return observableList;
        }
        return observableList;
    }

    /**
     * Loads the list view of images.
     * @param observableListImage Gives the list of image path
     * @return List of image paths which are presnt in the prevState Directory
     */
    public ObservableList<String> loadListViewImage(ObservableList<String> observableListImage) {
        File f = new File("prevState");
        if (f.exists()) {
            //Check files
            observableListImage.addAll(Arrays.asList(Objects.requireNonNull(f.list())));
            return observableListImage;
        }
        return observableListImage;
    }

    /**
     * Method that writes the contents of the top 10 players to the file.
     * @param topPlayers File where we have to store the top 10 players.
     * @throws IOException Checks if the file where we have to store the details is present or not.
     */
    public void writeArrayList(File topPlayers) throws IOException {
        FileOutputStream fos = new FileOutputStream(topPlayers);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(getTopPlayersList());
        oos.close();
    }

    /**
     * Creates an Image Directory to store the list of screenshots of the previous states.
     */
    public void createImageDirectory() {
        File dir = new File("prevState");
        if (dir.exists()) {
            System.out.println("Directory already exists");
        } else {
            dir.mkdir();
        }
    }

    /**
     * Takes the screenshot of the board on a win or a lose. It makes of a Swing Package which is Robot.
     * @param userDetails userDetails of type UserDetails to get name of the user and make it as the filename for the image which is stored.
     * @throws IOException Checks if the directory in which we want to store the image is present or not.
     */
    public void doTakeScreenshot(UserDetails userDetails) throws IOException {
        Robot robot = new Robot();
        ObservableList<Window> open = Stage.getWindows();
        if (open.size() == 1) {
            for (Window window : open) {
                Rectangle2D rectangle2D = new Rectangle2D(window.getX(), window.getY() + 150, window.getWidth() - 250, window.getHeight() - 100);
                WritableImage image = robot.getScreenCapture(null ,rectangle2D);
                BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, null);
                BufferedImage imageRGB = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(), BufferedImage.OPAQUE);
                Graphics2D graphics = imageRGB.createGraphics();
                graphics.drawImage(bufferedImage, 0, 0, null);
                File file = new File("prevState/"+userDetails.getUserName()+".jpg");
                ImageIO.write(imageRGB, "jpg", file);
            }
        }
    }
}
