package music;

import config.GameConfiguration;
import log.LoggerUtility;
import org.apache.log4j.Logger;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

/**
 * This class manage one music played at a time.
 * This class is pretty generic and relay a lot on the javax.sound.sampled library.
 * @author Gauthier Defrance
 * Class MusicPlayer
 * @version 0.1
 */
public class MusicPlayer {
    private static Logger logger = LoggerUtility.getLogger(MusicPlayer.class);
    private Clip clip;
    private Boolean loop=false;
    private String filename;


    /**
     * Method that create an object MusicPlayer with a specific name like : my_music.wav
     * @param filename the full name with extension of the file
     * @param loop {@link Boolean} that indicate if the music player should be in looping mode.
     */
    public MusicPlayer(String filename, Boolean loop) {
        File fichier = new File(GameConfiguration.MUSIC_FILE_PATH+filename);
        if(fichier.exists()) {
            this.load(GameConfiguration.MUSIC_FILE_PATH+filename);
            this.loop = loop;
        }
        else {
            logger.warn("The file couldn't be created : " + GameConfiguration.MUSIC_FILE_PATH+filename);
        }
    }

    /**
     * Method that load in memory a music .waw from a given path
     */
    public void load(String filePath) {
        this.filename = null;
        try {
            File audioFile = new File(filePath);
            if (!audioFile.exists()) {
                logger.warn("The file doesn't exist : " + filePath);
            }
            else {
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
                clip = AudioSystem.getClip();
                clip.open(audioStream);
                this.filename = filePath;
            }
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            logger.warn("Error while loading the file :"+e.getMessage());
        }
    }

    /**
     * Method that need to be called in order to start the music.
     */
    public void play() {
        if (clip != null) {
            clip.setFramePosition(0);
            clip.start();
            if (loop) doLoop();
        }
    }

    /**
     * Method that pause the music at a specific position.
     */
    public void pause() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }

    /**
     * Method that resume the music at a specific position.
     */
    public void resume() {
        if (clip != null && !clip.isRunning()) {
            clip.start();
            if (loop) doLoop();
        }
    }

    /**
     * Method that stop the current music.
     * If resumed it restart the music at it's beginning
     */
    public void stop() {
        if (clip != null) {
            clip.stop();
            clip.setFramePosition(0);
        }
    }

    /**
     * Method that close a Clip, making the player unable to read it anymore.
     */
    public void close() {
        if (clip != null) {
            clip.close();
        }
    }

    /**
     * Method that set the volume of a clip using the logarithmic curve making the sound
     * increase / decrease smoothly.
     * @param volume {@link Float} beetween 0 and 1 representing how much is the sound loud
     */
    public void setVolume(float volume) {
        if (clip != null) {
            volume = Math.max(0.0f, Math.min(1.0f, volume));

            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            float min = gainControl.getMinimum();
            float max = gainControl.getMaximum();

            float dB = (float) (Math.log10(volume == 0.0f ? 0.0001f : volume) * 20.0);
            dB = Math.max(min, Math.min(max, dB));

            gainControl.setValue(dB);
        }
    }

    /**
     * Set the player in the loop mode.
     */
    public void doLoop() {
        loop=true;
        if (clip != null) {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }


    /**
     * Method that show if the Player is currently playing or not.
     * @return Bool {@link Boolean}
     */
    public boolean isPlaying() {
        return clip != null && clip.isRunning();
    }

    /**
     * Method that show if the Player has reached the end of it's music
     * @return Bool {@link Boolean}
     */
    public boolean isFinished() {
        return clip != null && !clip.isRunning() && clip.getFramePosition() >= clip.getFrameLength();
    }

    /**
     * Check if the Player is currently in a loop or not
     * @return Bool {@link Boolean}
     */
    public boolean isLooping() {
        return loop;
    }

    /**
     * @return {@link Clip} the clip object of the player.
     */
    public Clip getClip() {
        return clip;
    }

    public String getFilename() {
        return filename;
    }

}
