import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

/**
 * Class for loading the Music / Sounds
 */
public class Music {
    /**
     * loads and starts the Music with {@link Clip}.
     * @param MusicLocation String with the location of the music.
     */
    void playMusic(String MusicLocation) {
        try {
            File musicPath = new File(MusicLocation);
            if (musicPath.exists()) {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
                Clip clip = AudioSystem.getClip();
                clip.open(audioInput);
                if (clip.isRunning()){
                    clip.stop();
                } else clip.start();

            } else {
                System.out.println("Can't find file");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

