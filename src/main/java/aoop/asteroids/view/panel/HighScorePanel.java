package aoop.asteroids.view.panel;

import aoop.asteroids.control.button.MenuButton;
import aoop.asteroids.util.HighScore;
import aoop.asteroids.util.HighScoreEvents;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;

import javax.swing.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Collections;

public class HighScorePanel extends JPanel {

    private final PropertyChangeSupport changeSupport;
    private ArrayList<HighScore> highScores;
    private JTable scores;

    public HighScorePanel() {
        this.scores = new JTable();
        this.changeSupport = new PropertyChangeSupport(this);
        MenuButton menuButton = new MenuButton(changeSupport);
        this.add(menuButton);
        menuButton.setLocation(0,0);
    }

    public void newHighScores() {
        this.highScores = new ArrayList<>();
        JSONArray jsonArray = HighScoreEvents.getHighScores();
        for(int i = 0; i < jsonArray.length(); i++) {
            JSONObject score = jsonArray.getJSONObject(i);
            highScores.add(new HighScore(score.getString("name"), score.getInt("score")));
        }
        setUpScores();
    }

    private void setUpScores() {
        this.remove(scores);
        Collections.sort(highScores);
        System.out.println(highScores.toString());
        String[] colNames = {"Position", "UserName", "Score"};
        Object[][] data = new Object[10][10];
        for(int i = 0; i < 10; i++) {
            data[i][0] = i+1 + ".";
            if(i < highScores.size()) {
                HighScore score = highScores.get(i);
                System.out.println(score.toString());
                data[i][1] = score.getUserName();
                data[i][2] = score.getScore();
            }
        }
        this.scores = new JTable(data,colNames);
        scores.setEnabled(false);
        this.add(scores);
    }


    /**
     * Method used to add PropertyChangeListeners to changeSupport
     *
     * @param listener listener to be added to changeSupport
     */
    public void addListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }
}
