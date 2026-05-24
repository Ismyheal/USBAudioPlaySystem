import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class AudioPlaybackSystemTest {

    private Playlist playlist;
    private AudioPlayerService player;
    private PlaybackControls controls;
    private PlayerDisplay display;

    @BeforeEach
    void setUp() {
        playlist = new Playlist();
        player = new AudioPlayerService();
        controls = new PlaybackControls(player, playlist);
        display = new PlayerDisplay();
    }

    // ─────────────────────────────────────────────
    // User Story 1: As a driver, I need to play audio from a connected USB device
    // ─────────────────────────────────────────────

    // Verifies that the USB service successfully connects when called.
    @Test
    void testUSBConnectsSuccessfully() {
        // Arrange
        USBService usb = new USBService();

        // Act
        boolean result = usb.connectUSB();

        // Assert
        assertTrue(result, "USB should connect successfully and return true.");
    }

    // Verifies that only supported audio formats are added to the playlist.
    @Test
    void testOnlySupportedAudioFormatsAreAdded() {
        // Arrange
        AudioFile supported = new AudioFile("song.mp3");
        AudioFile unsupported = new AudioFile("song.flac");

        // Act
        playlist.add(supported);
        playlist.add(unsupported);

        // Assert
        assertEquals(1, playlist.tracks.size(),
                "Only supported formats (.mp3, .wav) should be added to the playlist.");
    }

    // ─────────────────────────────────────────────
    // User Story 2: As a driver, I need to pause and resume audio playback
    // ─────────────────────────────────────────────

    // Verifies that pause() outputs a paused message to the console.
    @Test
    void testPauseStopsPlayback() {
        // Arrange
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        // Act
        controls.pause();
        System.setOut(System.out);

        // Assert
        assertTrue(out.toString().contains("Paused"),
                "pause() should output a paused message indicating playback has stopped.");
    }

    // Verifies that resume() outputs a resumed message to the console.
    @Test
    void testResumeRestoresPlayback() {
        // Arrange
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        // Act
        controls.resume();
        System.setOut(System.out);

        // Assert
        assertTrue(out.toString().contains("Resumed"),
                "resume() should output a resumed message indicating playback has continued.");
    }

    // ─────────────────────────────────────────────
    // User Story 3: As a driver, I need to skip to the next or previous track
    // ─────────────────────────────────────────────

    // Verifies that next() advances the playlist and plays the next track.
    @Test
    void testNextSkipsToNextTrack() {
        // Arrange
        playlist.add(new AudioFile("track1.mp3"));
        playlist.add(new AudioFile("track2.wav"));
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        // Act
        controls.next();
        System.setOut(System.out);

        // Assert
        assertTrue(out.toString().contains("track2.wav"),
                "next() should skip to and play the next track in the playlist.");
    }

    // Verifies that previous() goes back and plays the previous track.
    @Test
    void testPreviousSkipsToPreviousTrack() {
        // Arrange
        playlist.add(new AudioFile("track1.mp3"));
        playlist.add(new AudioFile("track2.wav"));
        controls.next(); // move to track2
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        // Act
        controls.previous();
        System.setOut(System.out);

        // Assert
        assertTrue(out.toString().contains("track1.mp3"),
                "previous() should go back and play the previous track in the playlist.");
    }

}