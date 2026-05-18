import java.util.ArrayList;

/* ================= AUDIO FILE ================= */
class AudioFile {
    String fileName;

    AudioFile(String fileName) {
        this.fileName = fileName;
    }

    boolean isSupported() {
        return fileName.endsWith(".mp3") || fileName.endsWith(".wav");
    }
}

/* ================= PLAYLIST ================= */
class Playlist {
    ArrayList<AudioFile> tracks = new ArrayList<>();
    int index = 0;

    void add(AudioFile f) {
        if (f.isSupported()) {
            tracks.add(f);
        } else {
            System.out.println("Rejected (unsupported format): " + f.fileName);
        }
    }

    AudioFile current() {
        return tracks.get(index);
    }

    void next() {
        index = (index + 1) % tracks.size();
    }

    void previous() {
        index = (index - 1 + tracks.size()) % tracks.size();
    }

    boolean isEmpty() {
        return tracks.isEmpty();
    }
}

/* ================= USB SERVICE ================= */
class USBService {
    boolean connectUSB() {
        System.out.println("USB Connected to Infotainment System");
        return true;
    }
}

/* ================= AUDIO PLAYER ================= */
class AudioPlayerService {

    void play(String song) {
        System.out.println("▶ Playing: " + song);
    }

    void pause() {
        System.out.println("⏸ Paused");
    }

    void resume() {
        System.out.println("▶ Resumed");
    }
}

/* ================= CONTROLS ================= */
class PlaybackControls {

    AudioPlayerService player;
    Playlist playlist;

    PlaybackControls(AudioPlayerService p, Playlist pl) {
        this.player = p;
        this.playlist = pl;
    }

    void play() {
        if (!playlist.isEmpty()) {
            player.play(playlist.current().fileName);
        }
    }

    void next() {
        playlist.next();
        play();
    }

    void previous() {
        playlist.previous();
        play();
    }

    void pause() {
        player.pause();
    }

    void resume() {
        player.resume();
    }
}

/* ================= DISPLAY ================= */
class PlayerDisplay {

    void show(Playlist p) {
        System.out.println("\n=== Car Infotainment Playlist ===");

        if (p.isEmpty()) {
            System.out.println("No media files found");
            return;
        }

        for (AudioFile f : p.tracks) {
            System.out.println("- " + f.fileName);
        }
    }
}

/* ================= MAIN SYSTEM ================= */
public class USBAudioPlaySystem {

    public static void main(String[] args) {

        // 1. Connect USB
        USBService usb = new USBService();
        usb.connectUSB();

        // 2. Load playlist
        Playlist playlist = new Playlist();
        playlist.add(new AudioFile("song1.mp3"));
        playlist.add(new AudioFile("song2.wav"));
        playlist.add(new AudioFile("video.avi")); // rejected

        // 3. Initialize system
        AudioPlayerService player = new AudioPlayerService();
        PlaybackControls controls = new PlaybackControls(player, playlist);
        PlayerDisplay display = new PlayerDisplay();

        // 4. Show playlist
        display.show(playlist);

        // 5. Simulate user controls
        System.out.println("\n--- User Controls ---");
        controls.play();
        controls.next();
        controls.pause();
        controls.resume();
        controls.previous();
    }
}