package music;


import config.GameOptions;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class manage the music played.
 * @author Gauthier Defrance
 * @author Kenan Ammad
 * Class MusicManager
 * @version 0.1
 */
public class MusicManager {
    private static MusicManager instance;
    private MusicPlayer[] musicPlayers;

    private MusicManager() {
        musicPlayers = new MusicPlayer[8];
        musicPlayers[0] = new MusicPlayer("startMenu.wav",true);
        musicPlayers[1] = new MusicPlayer("optionsMenu.wav",true);
        musicPlayers[2] = new MusicPlayer("mainGameMenu.wav",true);
        musicPlayers[3] = new MusicPlayer("inGameMenu.wav",true);
        musicPlayers[4] = new MusicPlayer("battle.wav",true);
        musicPlayers[5] = new MusicPlayer("declareWar.wav", false);
        musicPlayers[6] = new MusicPlayer("success.wav", false);
        musicPlayers[7] = new MusicPlayer("fail.wav", false);
    }

    /**
     * @return {@link MusicManager} the only instance that should exist
     */
    public static MusicManager getInstance() {
        if (instance == null) {
            instance = new MusicManager();
        }
        return instance;
    }

    /**
     * Method that kill all the musics that has ended.
     * and actualize the volume of the remaining one.
     */
    public void actualizeMusicPlayers() {
        for (MusicPlayer mp : musicPlayers) {
            if(mp != null) {
                if (!GameOptions.getInstance().getIsMuted()) {
                    mp.setVolume(0);
                    mp.stop();
                }
                else mp.setVolume(((float) GameOptions.getInstance().getVolume()) / 10);
            }
        }
    }

    /**
     * Methode that pause all the existing music that Loop
     */
    public void pauseAllMusicPlayersLoop() {
        for (MusicPlayer mp : musicPlayers) {
            if(mp != null && mp.isLooping()) {
                mp.pause();
            }
        }
    }

    /**
     * Methode that pause all the existing music that Loop
     * @param i except musicPlayers[i]
     */
    public void pauseAllMusicPlayersLoop(int i) {
        for (MusicPlayer mp : musicPlayers) {
            if(mp != null && !mp.equals(musicPlayers[i]) && mp.isLooping()) {
                mp.pause();
            }
        }
    }

    /**
     * Methode that pause all the existing music
     */
    public void pauseAllMusicPlayers() {
        for (MusicPlayer mp : musicPlayers) {
            if(mp != null) {
                mp.pause();
            }
        }
    }

    /**
     * Methode that resume all the existing music
     */
    public void resumeAllMusicPlayers() {
        for (MusicPlayer mp : musicPlayers) {
            if(mp != null) {
                mp.resume();
            }
        }
    }


    public void playerMusic(int i) {
        if(musicPlayers[i] != null)musicPlayers[i].play();
    }

    /**
     * Method that pause a specific music
     */
    public void pauseMusicPlayer(int i) {
        if(musicPlayers[i] != null)musicPlayers[i].pause();
    }


    /**
     * Methode that unpause a specific music
     */
    public void resumeMusicPlayer(int i) {
        if(musicPlayers[i] != null && !musicPlayers[i].isPlaying())musicPlayers[i].resume();
    }

    public MusicPlayer getMusicPlayer(int i) {
        return musicPlayers[i];
    }

}
