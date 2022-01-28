package com.example.javafinalproject.model;

import com.example.javafinalproject.view.WelcomeScreen;

import java.io.Serializable;

/** Stores details of the User who is playing the game.
 * @author Ayush
 * @version 1.0
 */
public class UserDetails implements Comparable<UserDetails>, Serializable {

    private String userName;
    private int score;

    //Calling WelcomeScreen to get Username

    /** Initialises the UserDetails class sets the userName by calling the display() method from WelcomeScreen.
     */

    public UserDetails() {
        userName = WelcomeScreen.display();
    }

    //Acessors and Mutators
    /** Acessors method to get the UserName.
     * @return Name of the User in the form a string.
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Sets the user name of the user who is playing.
     * @param userName Pass the name of the user which should be of type String.
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Gives the final score of the current user.
     * @return Final score of the current user in int format.
     */
    public int getScore() {
        return score;
    }

    /**
     * Sets the final score of the user once the game is finished.
     * @param score Provide the final score of the user which should be of type int.
     */
    public void setScore(int score) {
        this.score = score;
    }

    //To String

    /**
     * This method is used to diplay the contents of the user.
     * @return A string in the format username, Score: score.
     */
    @Override
    public String toString() {
        return getUserName()+", Score: "+getScore();
    }

    //CompareTo to sort top 10 results

    /**
     * Method to compare the scores of all the users and get the UserDetails in descending order of scores. This is used by Collections.sort() method.
     * @param o An object of type UserDetails
     * @return An int depending on the condition. If the passed reference is greater than the current reference then returns 1 and so on.
     */
    @Override
    public int compareTo(UserDetails o) {
        if (this.score < o.score) {
            return 1;
        } else if (this.score > o.score){
            return -1;
        } else {
            return 0;
        }
    }
}
