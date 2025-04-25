package music;


import config.GameOptions;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class manage the music played.
 * @author Gauthier Defrance
 * Class MusicManager
 * @version 0.1
 */
public class MusicManager {
    private static MusicManager instance;
    private HashMap<String, MusicPlayer> musicPlayersMap;

    private MusicManager() {
        musicPlayersMap = new HashMap<String, MusicPlayer>();
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
     * Method that add a music and play it.
     * @param mp
     */
    public void addMusicPlayer(MusicPlayer mp) {
        if(musicPlayersMap.containsKey(mp.getFilename()) ){
            MusicPlayer old = musicPlayersMap.get(mp.getFilename());
            old.stop();
            old.close();
        }
        musicPlayersMap.put(mp.getFilename(),mp);
        mp.setVolume(((float) GameOptions.getInstance().getVolume() )/10);
        mp.play();
    }

    /**
     * Method that kill a specific music given a filename.
     * @param filename
     */
    public void killMusicPlayer(String filename) {
        MusicPlayer mp = musicPlayersMap.get(filename);
        mp.stop();
        mp.close();
        musicPlayersMap.remove(filename);
    }

    /**
     * Method that kill all the musics that has ended.
     * and actualize the volume of the remaining one.
     */
    public void actualizeMusicPlayers() {
        ArrayList<MusicPlayer> copyLst = new ArrayList<MusicPlayer>();
        copyLst.addAll(musicPlayersMap.values());
        for (MusicPlayer mp : copyLst) {
            if(mp.isFinished() && !mp.isLooping()) {
                killMusicPlayer(mp.getFilename());
            } else {
                mp.setVolume(((float) GameOptions.getInstance().getVolume() )/10);
            }
        }
    }


    /**
     * Methode that kill all the existing music
     */
    public void killAllMusicPlayers() {
        ArrayList<MusicPlayer> copyLst = new ArrayList<MusicPlayer>();
        copyLst.addAll(musicPlayersMap.values());
        for (MusicPlayer mp : copyLst) {
            killMusicPlayer(mp.getFilename());
        }
    }

    /**
     * Methode that pause all the existing music
     */
    public void pauseAllMusicPlayers() {
        ArrayList<MusicPlayer> copyLst = new ArrayList<MusicPlayer>();
        copyLst.addAll(musicPlayersMap.values());
        for (MusicPlayer mp : copyLst) {
            pauseMusicPlayer(mp.getFilename());
        }
    }

    /**
     * Methode that resume all the existing music
     */
    public void resumeAllMusicPlayers() {
        ArrayList<MusicPlayer> copyLst = new ArrayList<MusicPlayer>();
        copyLst.addAll(musicPlayersMap.values());
        for (MusicPlayer mp : copyLst) {
            resumeMusicPlayer(mp.getFilename());
        }
    }



    /**
     * Method that pause a specific music
     * @param filename
     */
    public void pauseMusicPlayer(String filename) {
        musicPlayersMap.get(filename).pause();
    }


    /**
     * Methode that unpause a specific music
     * @param filename
     */
    public void resumeMusicPlayer(String filename) {
        musicPlayersMap.get(filename).resume();
    }

}
