package music;

public class MusicTest {

    public static void main(String[] args) {
        MusicPlayer mp = new MusicPlayer();
        mp.load("src/music/data/Killing_in_the_Name.wav");
        mp.play();
        mp.setVolume(0.2f);
        mp.loop();
        while (true) {

        }
    }
}
