package model;

import main.MyLogger;

import javax.sound.sampled.*;
import java.io.File;
import java.net.URL;

public class SoundManager {
    private static final String getHitSoundFileName = "sounds/get_hit_blya.wav";
    private static final String hitSoundFileName = "sounds/hit.wav";
    private static final String hitShieldSoundFileName = "sounds/hit_shield.wav";
    private static final String takeItemSoundFileName = "sounds/take_item.wav";
    private static final URL resourceToGetHitSound = SoundManager.class.getClassLoader().getResource(getHitSoundFileName);
    private static final URL resourceToHitSound = SoundManager.class.getClassLoader().getResource(hitSoundFileName);
    private static final URL resourceToHitShieldSound = SoundManager.class.getClassLoader().getResource(hitShieldSoundFileName);
    private static final URL resourceToTakeItemSound = SoundManager.class.getClassLoader().getResource(takeItemSoundFileName);

    /**
     * Play the sound from the file
     * @param sourceFile file with the sound
     */
    public static void play(URL sourceFile) {
        new Thread(() -> {
            try {
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(sourceFile.toURI()));
                Clip clip = AudioSystem.getClip();
                clip.open(audioInputStream);

                clip.start();

                clip.addLineListener(event -> {
                    if (event.getType() == LineEvent.Type.STOP) {
                        clip.close();
                    }
                });
            } catch (Exception e) {
                MyLogger.getMyLogger().severe("Exception with playing sound");
                e.printStackTrace();
            }
        }).start();
    }

    public static void playGetHitSound() {
        play(resourceToGetHitSound);
    }

    public static void playHitSound() {
        play(resourceToHitSound);
    }

    public static void playHitShieldSound() {
        play(resourceToHitShieldSound);
    }

    public static void playTakeItemSound() {
        play(resourceToTakeItemSound);
    }

}
