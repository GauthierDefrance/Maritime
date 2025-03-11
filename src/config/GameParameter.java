package config;

import java.io.Serializable;

public class GameParameter implements Serializable {

    private static GameParameter instance;
    private boolean isMuted;
    private boolean showDebug;

    private GameParameter() {
        instance = this;
        isMuted = false;
        showDebug = false;
    }

    //Getters

    public boolean getIsMuted() {
        return isMuted;
    }

    public boolean getShowDebug() {
        return showDebug;
    }

    //Setters

    public void setIsMuted(boolean isMuted) {
        this.isMuted = isMuted;
    }

    public void setShowDebug(boolean showDebug) {
        this.showDebug = showDebug;
    }

    public static GameParameter getInstance() {
        if (instance == null) {
            instance = new GameParameter();
        } return instance;
    }

}
