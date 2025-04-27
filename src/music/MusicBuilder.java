package music;


import log.LoggerUtility;
import org.apache.log4j.Logger;

import java.io.File;

import static config.GameConfiguration.MUSIC_FILE_PATH;


/**
 * This class serve the purpose of building MusicPlayer that will be then given to the MusicManager.
 * @author Gauthier Defrance
 * Class MusicBuilder
 * @version 0.1
 */
public class MusicBuilder {
    private static Logger logger = LoggerUtility.getLogger(MusicBuilder.class);

    /**
     * Method that create an object MusicPlayer with a specific name like : my_music.wav
     * @param filename the full name with extension of the file
     * @return {@link MusicPlayer} object that can play music and must be stopped
     */
    public static MusicPlayer createMusic(String filename) {
        return createMusic(filename, false);
    }

    /**
     * Method that create an object MusicPlayer with a specific name like : my_music.wav
     * @param filename the full name with extension of the file
     * @param loop {@link Boolean} that indicate if the music player should be initialized in looping mode.
     * @return {@link MusicPlayer} object that can play music and must be stopped
     */
    public static MusicPlayer createMusic(String filename, Boolean loop) {
        File fichier = new File(MUSIC_FILE_PATH+filename);
        if(fichier.exists()) {
            MusicPlayer mp = new MusicPlayer();
            mp.load(MUSIC_FILE_PATH+filename);
            if(loop) mp.loop();
            return mp;
        } else {
            logger.warn("The file couldn't be created : " + MUSIC_FILE_PATH+filename);
            return null;
        }
    }



}
