package config;

import java.io.Serializable;

public class GameOptions implements Serializable {

    private static GameOptions instance;
    private boolean isMuted;
    private boolean showDebug;
    private int volume;
    private int speedBoost;

    private GameOptions() {
        instance = this;
        isMuted = false;
        showDebug = false;
        volume = 5;
        speedBoost = 1;
    }

    //Getters

    public boolean getIsMuted() {
        return isMuted;
    }

    public boolean getShowDebug() {
        return showDebug;
    }

    public int getVolume() {
        return volume;
    }

    public int getSpeedBoost() {
        return speedBoost;
    }

    //Setters

    public void setIsMuted(boolean isMuted) {
        this.isMuted = isMuted;
    }

    public void setShowDebug(boolean showDebug) {
        this.showDebug = showDebug;
    }

    public void setVolume(int volume) { this.volume = volume; }

    public void setSpeedBoost(int speedBoost) {
        this.speedBoost = speedBoost;
    }

    //Instance Management

    public static synchronized GameOptions getInstance() {
        if (instance == null) {
            instance = new GameOptions();
        } return instance;
    }

    public static void setInstance(GameOptions instance) {
        GameOptions.instance = instance;
    }

}
