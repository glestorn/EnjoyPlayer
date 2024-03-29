import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class AudioPlayerEntity {
    private Mediator mediator;
    private String filePath;
    private Clip wavClip;
    private String currentTrackFormat;
    private MediaPlayer mp3Player;

    private float MINIMUM_VOLUME = -40;

    public AudioPlayerEntity(Mediator mediator) {
        this.mediator = mediator;
    }

    public void stopPlayTrack() {
        if (currentTrackFormat == null) {
            return;
        }
        if (currentTrackFormat.equals("mp3")) {
            if (mp3Player.getStatus().equals(MediaPlayer.Status.PAUSED)) {
                mp3Player.play();
                mediator.setStopImage();
            }
            else {
                mp3Player.pause();
                mediator.setPlayImage();
            }
        }
        else if (currentTrackFormat.equals("wav")) {
            if (wavClip.isRunning()) {
                wavClip.stop();
                mediator.setPlayImage();
            }
            else {
                wavClip.start();
                mediator.setStopImage();
            }
        }
    }

    public void setVolume(double newVolume) {
        if (currentTrackFormat == null) {
            return;
        }
        if (currentTrackFormat.equals("mp3")) {
            mp3Player.setVolume(newVolume); // from 0 to 1
        }
        else if (currentTrackFormat.equals("wav")) {
            FloatControl volume = (FloatControl)wavClip.getControl(FloatControl.Type.MASTER_GAIN);
            float newVolumeValue;
            if (newVolume >= 0.5) {
                newVolumeValue = (float)((newVolume - 0.5) * 2);
                volume.setValue(newVolumeValue * volume.getMaximum());
            }
            else {
                newVolumeValue = (float)(newVolume * 2);
                volume.setValue((1 - newVolumeValue) * MINIMUM_VOLUME);
            }
        }
    }

    public void setTrackTime(double newTime) {
        if (currentTrackFormat == null) {
            return;
        }
        if (currentTrackFormat.equals("mp3")) {
            Duration someDuration = new Duration(mp3Player.getStopTime().toMillis() * newTime);
            mp3Player.stop();
            mp3Player = new MediaPlayer(new Media(new File(filePath).toURI().toString()));
            mp3Player.setStartTime(someDuration);
            mp3Player.play();
        } else if (currentTrackFormat.equals("wav")) {
            wavClip.setFramePosition((int)(wavClip.getFrameLength() * newTime));
        }
    }

    public String getCurrentTime() {
        StringBuilder currentTime = new StringBuilder();
        int minutes = 0;
        int seconds = 0;
        if (currentTrackFormat == null) {
            return "  0:00";
        }
        else if (currentTrackFormat.equals("mp3")) {
            if (mp3Player == null) {
                return "  0:00";
            }
            double allSeconds = mp3Player.getCurrentTime().toMillis() / 1000;
            minutes = (int)(allSeconds / 60);
            seconds = (int)(allSeconds % 60);
        }
        else if (currentTrackFormat.equals("wav")) {
            if (wavClip == null) {
                return "  0:00";
            }
            double allSeconds = (double)wavClip.getMicrosecondPosition() / 1000000;
            minutes = (int)(allSeconds / 60);
            seconds = (int)(allSeconds % 60);
        }

        if (minutes < 100) {
            currentTime.append(" ");
        }
        if (minutes < 10) {
            currentTime.append(" ");
        }
        currentTime.append(minutes);
        currentTime.append(":");
        if (seconds < 10) {
            currentTime.append(0);
        }
        currentTime.append(seconds);
        return currentTime.toString();
    }

    public double getCurrentSliderPosition() {
        double newValue = 0;
        if (currentTrackFormat == null) {
            return 0;
        }
        if (currentTrackFormat.equals("mp3")) {
            if (mp3Player == null) {
                return 0;
            }
            newValue = mp3Player.getCurrentTime().toMillis()
                    / mp3Player.getStopTime().toMillis();
        } else if (currentTrackFormat.equals("wav")) {
            if (wavClip == null) {
                return 0;
            }
            newValue = (double)wavClip.getFramePosition()
                    / (double)wavClip.getFrameLength();
            if (newValue * 100 == 100) {
                mediator.startNextTrack();
            }
        }
        return newValue;
    }

    public void startNewTrack(String filePath) {
        if (currentTrackFormat != null) {
            if (currentTrackFormat.equals("mp3")) {
                mp3Player.stop();
            } else if (currentTrackFormat.equals("wav")) {
                wavClip.stop();
                wavClip.close();
            }
        }

        this.filePath = filePath;
        mediator.setStopImage();
        String trackName = filePath;
        while (trackName.contains("\\")) {
            trackName = trackName.substring(trackName.indexOf("\\") + 1);
        }
        mediator.setTrackName(trackName.substring(0, trackName.indexOf(".")));

        if (filePath.endsWith(".wav")) {
            try {
                currentTrackFormat = "wav";
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(
                        new File(filePath).getAbsoluteFile());
                wavClip = AudioSystem.getClip();
                wavClip.open(audioInputStream);
                wavClip.loop(0);

                FloatControl volume = (FloatControl)wavClip.getControl(FloatControl.Type.MASTER_GAIN);
                volume.setValue(volume.getMaximum());
            }
            catch (UnsupportedAudioFileException | IOException |
                    LineUnavailableException ex) {
                ex.printStackTrace();
            }
        }
        else if (filePath.endsWith(".mp3")) {
            currentTrackFormat = "mp3";
            Media mp3Track = new Media(new File(filePath).toURI().toString());
            mp3Player = new MediaPlayer(mp3Track);
            mp3Player.setOnEndOfMedia(new Runnable() {
                @Override
                public void run() {
                    mediator.startNextTrack();
                }
            });
            mp3Player.setVolume(1);
            mp3Player.play();
        }
    }

    public void resetTrack() {
        if (currentTrackFormat != null) {
            if (currentTrackFormat.equals("mp3")) {
                mp3Player.stop();
            }
            else if (currentTrackFormat.equals("wav")) {
                wavClip.stop();
                wavClip.close();
            }
            currentTrackFormat = null;
            mediator.setTrackName("");
        }
    }

    public String getCurrentTrack() {
        return filePath;
    }
    //TODO try to make Strategy pattern
}
