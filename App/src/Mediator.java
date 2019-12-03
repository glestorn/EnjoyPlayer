import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

public class Mediator {
    private AudioPlayerEntity audioPlayer;
    private ActionStatePanel actStatePanel;
    private PlaylistManager playlistManager;

    public Mediator(BorderPane root, Scene scene) {
        audioPlayer = new AudioPlayerEntity(this);
        actStatePanel = new ActionStatePanel(this);
        playlistManager = new PlaylistManager(this, root, scene);
    }

    public AudioPlayerEntity getAudioPlayer() {
        return audioPlayer;
    }

    public ActionStatePanel getActStatePanel() {
        return actStatePanel;
    }

    public PlaylistManager getPlaylistManager() {
        return playlistManager;
    }

    public void stopPlayTrack() {
        audioPlayer.stopPlayTrack();
    }

    public void setNewVolume(double newVolume) {
        audioPlayer.setVolume(newVolume);
    }

    public void setNewTrackTime(double newTrackTime) {
        audioPlayer.setTrackTime(newTrackTime);
    }

    public String getTrackTime() {
        return audioPlayer.getCurrentTime();
    }

    public double getCurrentSliderPosition() {
        return audioPlayer.getCurrentSliderPosition();
    }
}
